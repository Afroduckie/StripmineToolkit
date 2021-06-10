package com.andrielgaming.bulkminer.items.tools;

import java.util.Map;
import java.util.Set;
import com.andrielgaming.bulkminer.items.tools.BulkTool;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Scythe extends BulkTool//HoeItem
{
	private static final Set<Block> EFFECTIVE_ON_BLOCKS = ImmutableSet.of(Blocks.MELON, Blocks.PUMPKIN, Blocks.COCOA, Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
	public static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState(), Blocks.GRASS_PATH, Blocks.FARMLAND.defaultBlockState(), Blocks.DIRT, Blocks.FARMLAND.defaultBlockState(), Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState()));

	public Scythe(IItemTier itemTier, int attackDamage, float attackSpeed, Item.Properties properties)
	{
		super((float) attackDamage, attackSpeed, itemTier, EFFECTIVE_ON_BLOCKS, properties.addToolType(net.minecraftforge.common.ToolType.HOE, itemTier.getLevel()));
	}

	@SuppressWarnings("deprecation")
	public ActionResultType useOn(ItemUseContext context)
	{//ShovelItem
		World world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
		if (hook != 0)
			return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
		if (context.getClickedFace() != Direction.DOWN && world.getBlockState(context.getClickedPos().above()).isAir())
		{
			BlockState blockstate = world.getBlockState(blockpos).getToolModifiedState(world, blockpos, context.getPlayer(), context.getItemInHand(), net.minecraftforge.common.ToolType.HOE);
			if (blockstate != null)
			{
				PlayerEntity playerentity = context.getPlayer();
				world.playSound(playerentity, blockpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				//if (world.isClientSide())
				//{
					world.setBlock(blockpos, blockstate, 11);
					if (playerentity != null)
					{
						context.getItemInHand().hurtAndBreak(1, playerentity, (player) ->
						{
							player.broadcastBreakEvent(context.getHand());
						});
					}
				//}

				return ActionResultType.sidedSuccess(true);
			}
		}

		return ActionResultType.PASS;
	}

	@javax.annotation.Nullable
	public static BlockState getHoeTillingState(BlockState originalState)
	{
		return HOE_LOOKUP.get(originalState.getBlock());
	}
}
