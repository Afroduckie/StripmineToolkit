package com.andrielgaming.bulkminer.handlers.functionality;

import java.util.LinkedList;
import java.util.Random;

import com.andrielgaming.bulkminer.items.tools.BulkTool;
import com.andrielgaming.bulkminer.utils.BlockfaceRayTracer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
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
	if (event.getPlayer().getHeldItemMainhand().getItem() instanceof BulkTool)
	    event.setNewSpeed(event.getOriginalSpeed() * 0.7f);
    }

    //

    // BulkTool break handling
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onBlockBroken(BreakEvent event)
    {
	// Save a bunch of shit for vector calculations and other methods further down
	player = event.getPlayer();
	pos = event.getPos();
	held = player.getHeldItemMainhand();
	broken = event.getState().getBlock();
	BlockState stat = event.getState();

	// Grab string name of the block face from static raytracer class
	dir = BlockfaceRayTracer.getBlockFace(player, pos, held, broken).getName2();
	System.out.println("DEBUGGGG- " + dir);

	// Check if tool used is one of the strip-mining tools thru BulkTool inheritance
	if (held.getItem() instanceof BulkTool && dir != null && pos != null)
	{
	    LinkedList<BlockPos> positions = getBreakables(event.getPos(), event.getState(), event.getPlayer().getEntityWorld(), held);
	    System.out.println("DEBUG- " + positions.isEmpty());
	    if (positions != null && !positions.isEmpty())
	    {
		for (BlockPos p : positions) player.getEntityWorld().destroyBlock(p, !player.isCreative());

		event.setResult(Result.ALLOW);
		if (!player.isCreative())
		    held.setDamage(held.getDamage() + 4);
	    }
	}
    }

    @OnlyIn(Dist.CLIENT)
    public static LinkedList<BlockPos> getBreakables(BlockPos p, BlockState s, World worldIn, ItemStack heldItem)
    {
	LinkedList<BlockPos> neighbors = new LinkedList<BlockPos>();
	p.toImmutable();
	BlockPos temp;
	BlockState s_temp;

	// Validate compatibility between the held BulkTool and the clicked block
	boolean isBreakable = heldItem.getToolTypes().contains(worldIn.getBlockState(p).getBlock().getHarvestTool(worldIn.getBlockState(p)));
	boolean isGrowable = (heldItem.getToolTypes().contains(ToolType.HOE) && s.getBlock() instanceof IGrowable);
	if (isBreakable == false && isGrowable == false)
	{
	    return null;
	}

	String tempdir = dir;
	for (int i = -1; i <= 1; i++)
	{
	    for (int j = -1; j <= 1; j++)
	    {
		// Override everything if its a crop
		if (heldItem.getToolTypes().contains(ToolType.HOE) && isGrowable == true)
		{
		    temp = new BlockPos(j + p.getX(), p.getY(), i + p.getZ());
		    s_temp = worldIn.getBlockState(temp);
		    // Should assume, for now, that any block that gets here is a crop
		    // boolean harvestReady = 
		    if(heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()) || (heldItem.getToolTypes().contains(ToolType.HOE) && s_temp.getBlock() instanceof IGrowable))
		    {
			neighbors.add(temp);
			temp = null;
		    }
		}
		else
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
	p.toMutable();
	return neighbors;
    }
}
