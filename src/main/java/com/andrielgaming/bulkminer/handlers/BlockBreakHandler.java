package com.andrielgaming.bulkminer.handlers;

import java.util.LinkedList;

import com.andrielgaming.bulkminer.items.tools.BulkTool;
import com.andrielgaming.bulkminer.items.tools.Hammer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
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

    // Modifies the block breaking speed of all BulkTool children
    @SubscribeEvent
    public static void modifyBreakspeed(BreakSpeed event)
    {
	if (event.getPlayer().getHeldItemMainhand().getItem() instanceof BulkTool)
	    event.setNewSpeed(event.getOriginalSpeed() * 0.7f);
    }
    
    // Hammer break handling
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

	// Grab a bunch of vectors, raytrace to the block broken, and raytrace once more
	// to get the specific block face clicked on
	Vector3d start = player.getEyePosition(1.0f);
	Vector3d end = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
	context = player.getEntityWorld().rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, null));
	Direction tempo = context.getFace();
	context = new BlockRayTraceResult(start, tempo, pos, false);
	// Grab string name of the block face
	dir = context.getFace().getName2();

	// Check if tool used is one of the strip-mining tools thru BulkTool inheritance
	// TODO: validate
	if (held.getItem() instanceof BulkTool && context != null && dir != null && pos != null)
	{
	    LinkedList<BlockPos> positions = getBreakables(event.getPos(), event.getState(), event.getPlayer().getEntityWorld(), held);
	    if (positions != null)
	    {
		for (BlockPos p : positions) player.getEntityWorld().destroyBlock(p, !player.isCreative());
		event.setResult(Result.ALLOW);
		if (!player.isCreative())
		    held.setDamage(held.getDamage() + 4);
	    }
	}
    }

    public static LinkedList<BlockPos> getBreakables(BlockPos p, BlockState s, World worldIn, ItemStack heldItem)
    {
	LinkedList<BlockPos> neighbors = new LinkedList<BlockPos>();
	p.toImmutable();
	BlockPos temp;

	// Validate compatibility between the held BulkTool and the clicked block
	if (!heldItem.getToolTypes().contains(worldIn.getBlockState(p).getBlock().getHarvestTool(worldIn.getBlockState(p))))
	{
	    return null;
	}

	// Iterates between all the BlockPos values relative to given BlockPos parameter
	// 'p'
	// ints i and j refer to positions relative to p, meaning it starts at x-1, y-1,
	// z for a north block
	String tempdir = dir;
	for (int i = -1; i <= 1; i++)
	{
	    for (int j = -1; j <= 1; j++)
	    {
		// Using Direction enum value in tempdir, check which direction Player hit the
		// block
		if (tempdir.equals("north") || tempdir.equals("south"))
		{
		    // Create new BlockPos for the neighbor blocks & add to list
		    temp = new BlockPos(i + p.getX(), j + p.getY(), p.getZ());
		    if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()))
		    {
			neighbors.add(temp);
			temp = null;
		    }
		}
		else if (tempdir.equals("east") || tempdir.equals("west"))
		{
		    temp = new BlockPos(p.getX(), j + p.getY(), i + p.getZ());
		    if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()))
		    {
			neighbors.add(temp);
			temp = null;
		    }
		}
		else if (tempdir.equals("up") || tempdir.equals("down"))
		{
		    temp = new BlockPos(j + p.getX(), p.getY(), i + p.getZ());
		    if (heldItem.getItem().getToolTypes(heldItem).contains(worldIn.getBlockState(temp).getHarvestTool()))
		    {
			neighbors.add(temp);
			temp = null;
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
