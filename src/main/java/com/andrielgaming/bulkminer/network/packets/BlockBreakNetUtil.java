package com.andrielgaming.bulkminer.network.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.DistExecutor;

public class BlockBreakNetUtil
{
	public static DistExecutor.SafeRunnable breakBlocks(BlockPos pl, PlayerEntity player)
	{
		return new DistExecutor.SafeRunnable()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void run()
			{
				player.level.destroyBlock(pl, !player.isCreative());
			}
		};
	}
}
