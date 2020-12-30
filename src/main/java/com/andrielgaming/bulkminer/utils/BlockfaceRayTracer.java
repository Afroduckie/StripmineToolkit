package com.andrielgaming.bulkminer.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockfaceRayTracer
{
    @OnlyIn(Dist.CLIENT)
    public static BlockRayTraceResult getTraceResult(PlayerEntity player, BlockPos pos, ItemStack tool, Block blk)
    {
	BlockRayTraceResult trace;
	Vector3d start = player.getEyePosition(1.0f);
	Vector3d end = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
	trace = player.getEntityWorld().rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, null));
	Direction face = trace.getFace();
	trace = new BlockRayTraceResult(start, face, pos, false);
	return trace;
    }
    
    @OnlyIn(Dist.CLIENT)
    public static Direction getBlockFace(PlayerEntity player, BlockPos pos, ItemStack tool, Block blk)
    {
	BlockRayTraceResult trace = getTraceResult(player, pos, tool, blk);
	return trace.getFace();
    }
}
