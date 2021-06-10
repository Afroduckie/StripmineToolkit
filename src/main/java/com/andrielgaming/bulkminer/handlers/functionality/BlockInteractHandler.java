package com.andrielgaming.bulkminer.handlers.functionality;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import com.andrielgaming.bulkminer.items.tools.BulkTool;
import com.andrielgaming.bulkminer.items.tools.Scythe;
import com.andrielgaming.bulkminer.network.BulkminerPacketHandler;
import com.andrielgaming.bulkminer.network.packets.BlockInteractionPack;
import com.andrielgaming.bulkminer.utils.BlockfaceRayTracer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

/*
 * TODO: NOTES
 * -IPlantable interface might be usable for comparison, could maybe find plantable seeds without the hash set methodology I'm using currently
 */

// Handler to cover all bulktool right-click functionalities, such as scythe harvesting
@Mod.EventBusSubscriber
public class BlockInteractHandler
{
	public static ItemStack held;
	public static Block broken;
	public static PlayerEntity player;
	public static BlockPos pos;
	public static BlockRayTraceResult context;
	public static String dir;
	public static final HashSet<Item> invalidreplant = new HashSet<Item>(Arrays.asList(Items.COCOA_BEANS, Items.REDSTONE, Items.STRING));

	@SubscribeEvent
	public static void onToolRightClick(BlockToolInteractEvent event)
	{
		player = event.getPlayer();
		pos = event.getPos();
		held = player.getMainHandItem();
		broken = event.getState().getBlock();
		World worldIn = player.getCommandSenderWorld();
		BlockState stat = worldIn.getBlockState(pos);
		ToolType harvestTool = event.getToolType();
		PlayerInventory backpack = player.inventory;
		ItemStack offhandStack = player.inventory.offhand.get(0);
		// Target blockstate and block instance
		Block target = stat.getBlock();

		if (target.is(Blocks.FARMLAND))
		{
			return;
		}

		// TODO: might be unnecessary for this class entirely, but might be warranted for LBJaxe
		dir = BlockfaceRayTracer.getBlockFace(player, pos, held, broken).getName();

		if (held.getItem() instanceof BulkTool && pos != null && stat != null)
		{
			// Send to getInteractables to grab all modifiable neighbors
			LinkedList<BlockPos> neighbors = getInteractables(pos, stat, player.getCommandSenderWorld(), held);
			if (neighbors != null && !neighbors.isEmpty())
			{
				// BlockState corresponding to interaction w/ its harvest tool
				BlockState modded = target.getToolModifiedState(stat, worldIn, pos, player, held, target.getHarvestTool(stat));
				// Will be null if block cannot be tilled by hoe tools
				BlockState tilled = target.getToolModifiedState(stat, worldIn, pos, player, held, ToolType.HOE);

				if (neighbors != null && !neighbors.isEmpty())
				{
					if (!(target instanceof CropsBlock))
					{
						for (BlockPos p : neighbors)
						{
							// For SOME mother-shitting reason, the packet called only works if done IN REVERSE so fuck it, if it works
							if(!player.level.isClientSide)
							{
								if (held.getItem() instanceof Scythe)
									player.level.setBlock(p, tilled, 11);
								else player.level.setBlock(p, modded, 11);
							}
							else BulkminerPacketHandler.INSTANCE.sendTo(new BlockInteractionPack(p), Minecraft.getInstance().getConnection().getConnection(), NetworkDirection.PLAY_TO_CLIENT);
						}
					}
				}
			}
		}
		stat = null;
		pos = null;
	}

	// Here in case I want to implement bulk planting w/o replanting
	/*
	 * @OnlyIn(Dist.CLIENT)
	 * @SubscribeEvent
	 * public static void interceptOffhand(RightClickBlock event)
	 * {
	 * // If player is right-clicking, has seed in offhand, and is holding a Scythe, cancel the event to let other event take over
	 * if (event.getPlayer().getHeldItemMainhand().getItem() instanceof Scythe && event.getHand() == Hand.OFF_HAND)
	 * {
	 * }
	 * }
	 */

	@OnlyIn(Dist.CLIENT)
	public static LinkedList<BlockPos> getInteractables(BlockPos p, BlockState s, World worldIn, ItemStack heldItem)
	{
		LinkedList<BlockPos> neighbors = new LinkedList<BlockPos>();
		p.immutable();
		BlockPos temp;
		BlockState s_temp;

		for (int i = -1; i <= 1; i++)
		{
			for (int j = -1; j <= 1; j++)
			{
				temp = new BlockPos(j + p.getX(), p.getY(), i + p.getZ());
				s_temp = worldIn.getBlockState(temp);
				neighbors.add(temp);
			}
		}
		// Return BlockPos to mutable status and return list
		p.mutable();
		return neighbors;
	}
}
