package com.andrielgaming.bulkminer.network.packets;

import com.andrielgaming.bulkminer.items.tools.Scythe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.DistExecutor;

public class BlockInteractionNetUtil
{
	public static DistExecutor.SafeRunnable interact(BlockPos pl, PlayerEntity player)
	{
		return new DistExecutor.SafeRunnable()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void run()
			{
				World world = player.level;
				ItemStack tool = player.getMainHandItem();
				BlockState stat = world.getBlockState(pl);
				Block target = stat.getBlock();
				BlockState modded = target.getToolModifiedState(stat, world, pl, player, tool, target.getHarvestTool(stat));
				BlockState tilled = target.getToolModifiedState(stat, world, pl, player, tool, ToolType.HOE);
				stat = stat.getToolModifiedState(world, pl, player, tool, stat.getBlock().getHarvestTool(stat));
				if (tool.getItem() instanceof Scythe)
					player.level.setBlock(pl, tilled, 11);
				else player.level.setBlock(pl, modded, 11);
				stat = null;
			}
		};
	}
}
