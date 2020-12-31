package com.andrielgaming.bulkminer.handlers.functionality;

import java.util.LinkedList;

import com.andrielgaming.bulkminer.items.tools.BulkTool;
import com.andrielgaming.bulkminer.items.tools.Scythe;
import com.andrielgaming.bulkminer.utils.BlockfaceRayTracer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onToolRightClick(BlockToolInteractEvent event)
    {
	// Save a bunch of shit for vector calculations and other methods further down
	player = event.getPlayer();
	pos = event.getPos();
	held = player.getHeldItemMainhand();
	broken = event.getState().getBlock();
	World world = player.getEntityWorld();
	BlockState stat = world.getBlockState(pos);

	// Grab string name of the block face from static raytracer class
	dir = BlockfaceRayTracer.getBlockFace(player, pos, held, broken).getName2();

	if (held.getItem() instanceof BulkTool && pos != null && stat != null)
	{
	    // Send to getInteractables to grab all modifiable neighbors
	    LinkedList<BlockPos> neighbors = getInteractables(pos, stat, player.getEntityWorld(), held);
	    if (neighbors != null && !neighbors.isEmpty())
	    {
		for (BlockPos i : neighbors)
		{
		    if (held.getToolTypes().contains(world.getBlockState(i).getHarvestTool()) || (world.getBlockState(i).getBlock() instanceof SpreadableSnowyDirtBlock && held.getItem() instanceof Scythe))
		    {
			System.out.println("DEBUG- asjkhldfaljkdhfalsjkdhfalskdjhfalskdjhfalskdjfhaslkdjhfasldkjfhasdlkjfhasdlkjfhasldkjhfalksdjfhasldkjfhaslkdjfhaslkdjhfaslkdjfh,.mnsczdxvblkjholiuwehg");
			ToolType harvest = event.getToolType();
			BlockState modified = world.getBlockState(i).getBlock().getToolModifiedState(world.getBlockState(i), world, i, player, event.getHeldItemStack(), harvest);
			if (modified != null)
			    world.setBlockState(i, modified, 11);
			event.setFinalState(modified);
		    }
		}
	    }
	}
    }

    @OnlyIn(Dist.CLIENT)
    public static LinkedList<BlockPos> getInteractables(BlockPos p, BlockState s, World worldIn, ItemStack heldItem)
    {
	LinkedList<BlockPos> neighbors = new LinkedList<BlockPos>();
	p.toImmutable();
	BlockPos temp;
	BlockState s_temp;

	for (int i = -1; i <= 1; i++)
	{
	    for (int j = -1; j <= 1; j++)
	    {
		temp = new BlockPos(j + p.getX(), p.getY(), i + p.getZ());
		s_temp = worldIn.getBlockState(temp);
		if (heldItem.getToolTypes().contains(s_temp.getHarvestTool()) || (heldItem.getItem() instanceof Scythe && Scythe.getHoeTillingState(s).getBlock() == Blocks.FARMLAND))
		{
		    if (worldIn.getBlockState(temp.up()).getBlock() instanceof IGrowable)
		    {
			worldIn.destroyBlock(p.up(), !player.isCreative());
		    }
		    neighbors.add(temp);
		}
		// Break out of loop and method if not a valid source block
		else return null;
	    }
	}
	// Return BlockPos to mutable status and return list
	p.toMutable();
	return neighbors;
    }
}
