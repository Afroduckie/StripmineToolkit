package com.andrielgaming.bulkminer.handlers.functionality;

import java.util.LinkedList;
import java.util.Random;
import java.util.function.Supplier;

import com.andrielgaming.bulkminer.items.tools.BulkTool;
import com.andrielgaming.bulkminer.items.tools.Scythe;
import com.andrielgaming.bulkminer.network.BulkminerPacketHandler;
import com.andrielgaming.bulkminer.network.packets.BlockBreakPack;
import com.andrielgaming.bulkminer.utils.BlockfaceRayTracer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class BlockBreakHandler
{
	public static ItemStack held;
	public static Block broken;
	public static PlayerEntity player;
	public static BlockPos pos;
	public static BlockRayTraceResult context;
	public static String dir;
	private static Random rand = new Random();

	// Modifies the block breaking speed of all BulkTool children
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void modifyBreakspeed(BreakSpeed event)
	{
		if (event.getPlayer().getMainHandItem().getItem() instanceof BulkTool)
			event.setNewSpeed(event.getOriginalSpeed() * 0.7f);
	}


	// BulkTool break handling
	//@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onBlockBroken(BreakEvent event)
	{
		// Save a bunch of shit for vector calculations and other methods further down
		player = event.getPlayer();
		pos = event.getPos();
		held = player.getMainHandItem();
		broken = event.getState().getBlock();
		BlockState stat = event.getState();

		// Grab string name of the block face from static raytracer class
		dir = BlockfaceRayTracer.getBlockFace(player, pos, held, broken).getName();

		// Check if tool used is one of the strip-mining tools thru BulkTool inheritance
		if (held.getItem() instanceof BulkTool && dir != null && pos != null)
		{
			LinkedList<BlockPos> positions = getBreakables(event.getPos(), event.getState(), (World) event.getWorld(), held, dir);
			LinkedList<BlockPos> seedspots = new LinkedList<BlockPos>();
			if (positions != null && !positions.isEmpty())
			{
				for (BlockPos p : positions)
				{
					// For SOME mother-shitting reason, the packet called only works if done IN REVERSE so fuck it, if it works
					if(!player.level.isClientSide)
						player.level.destroyBlock(p, !player.isCreative());
					else
						BulkminerPacketHandler.INSTANCE.sendTo(new BlockBreakPack(p), Minecraft.getInstance().getConnection().getConnection(), NetworkDirection.PLAY_TO_CLIENT);
				}

				event.setResult(Result.ALLOW);
				if (!player.isCreative())
					held.setDamageValue(held.getDamageValue() + 4);
			}
		}
	}

	public static LinkedList<BlockPos> getBreakables(BlockPos p, BlockState s, World worldIn, ItemStack heldItem, String face)
	{
		LinkedList<BlockPos> neighbors = new LinkedList<BlockPos>();
		p.immutable();
		BlockPos temp;
		BlockState s_temp;

		// Validate compatibility between the held BulkTool and the clicked block
		boolean isBreakable = heldItem.getToolTypes().contains(worldIn.getBlockState(p).getBlock().getHarvestTool(worldIn.getBlockState(p)));
		boolean isGrowable = (heldItem.getToolTypes().contains(ToolType.HOE) && s.getBlock() instanceof IGrowable && Scythe.getHoeTillingState(s) == null);
		if (isBreakable == false && isGrowable == false)
		{
			return null;
		}

		String tempdir = face;
		for (int i = -1; i <= 1; i++)
		{
			for (int j = -1; j <= 1; j++)
			{
				// Override everything if its a crop, Thanos-snap that shit
				if (heldItem.getToolTypes().contains(ToolType.HOE) && isGrowable == true)
				{
					temp = new BlockPos(j + p.getX(), p.getY(), i + p.getZ());
					s_temp = worldIn.getBlockState(temp);

					if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()) || (heldItem.getToolTypes().contains(ToolType.HOE) && s_temp.getBlock() instanceof IGrowable))
					{
						neighbors.add(temp);
						temp = null;
					}
				}
				// I'm
				else if (heldItem.getToolTypes().contains(ToolType.HOE) && isGrowable == false)
				{
					// Currently the Scythe can break non-crop blocks. This should fix it.
					return null;
				}
				// Allow for Melon, Pumpkin, and Cocoa Bean blocks to be broken/harvested by the Scythe with special-case boolean check
				else if ((heldItem.getToolTypes().contains(ToolType.HOE) && isGrowable == false && (s.getBlock() instanceof StemGrownBlock || s.is(Blocks.COCOA))) || heldItem.getItem().getToolTypes(heldItem).contains(s.getHarvestTool()))
				{
					// Using Direction enum value in tempdir, check which direction Player hit the
					if (tempdir.equals("north") || tempdir.equals("south"))
					{
						// Create new BlockPos for the neighbor blocks & add to list
						temp = new BlockPos(i + p.getX(), j + p.getY(), p.getZ());
						s_temp = worldIn.getBlockState(temp);
						if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()))
						{
							neighbors.add(temp);
							temp = null;
						}
					}
					// This shit gets both boring and repetitive past here
					// Also this is a horrible and lazy way to do this but its a Minecraft mod so idc
					else if (tempdir.equals("east") || tempdir.equals("west"))
					{
						temp = new BlockPos(p.getX(), j + p.getY(), i + p.getZ());
						s_temp = worldIn.getBlockState(temp);
						if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()) || (heldItem.getToolTypes().contains(ToolType.HOE) && s_temp.getBlock() instanceof IGrowable))
						{
							neighbors.add(temp);
							temp = null;
						}
					}
					else if (tempdir.equals("up") || tempdir.equals("down"))
					{
						temp = new BlockPos(j + p.getX(), p.getY(), i + p.getZ());
						s_temp = worldIn.getBlockState(temp);
						if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()) || (heldItem.getToolTypes().contains(ToolType.HOE) && s_temp.getBlock() instanceof IGrowable))
						{
							neighbors.add(temp);
							temp = null;
						}
					}
				}
			}
		}
		// Null the vars for added guarantee and return the list
		dir = "";
		context = null;
		temp = null;
		p.mutable();
		return neighbors;
	}
}
