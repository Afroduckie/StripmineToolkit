package com.andrielgaming.bulkminer.items.tools;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
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

public class Dredge extends BulkTool
{
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
	/** Map used to lookup shovel right click interactions */
	protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.defaultBlockState()));

	public Dredge(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder)
	{
		super(attackDamageIn, attackSpeedIn, tier, EFFECTIVE_ON, builder.addToolType(net.minecraftforge.common.ToolType.SHOVEL, tier.getLevel()));
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(BlockState blockIn)
	{
		return blockIn.is(Blocks.SNOW) || blockIn.is(Blocks.SNOW_BLOCK);
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context)
	{
		World world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		if (context.getClickedFace() == Direction.DOWN)
		{
			return ActionResultType.PASS;
		}
		else
		{//AxeItem
			PlayerEntity playerentity = context.getPlayer();
			BlockState blockstate1 = blockstate.getToolModifiedState(world, blockpos, playerentity, context.getItemInHand(), net.minecraftforge.common.ToolType.SHOVEL);
			BlockState blockstate2 = null;
			if (blockstate1 != null && world.isEmptyBlock(blockpos.above()))
			{// TODO fix this bullshit
				//world.playSound(playerentity, blockpos, SoundEvents., SoundCategory.BLOCKS, 1.0F, 1.0F);
				blockstate2 = blockstate1;
			}
			// VALIDATE- 16.5 removed old blockstate gets, no comparable method to what I used in 16.4, trying BooleanProperties now
			else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT))
			{
				//if (world.isClientSide())
				//{
					//TODO fix this bullshit too
					world.levelEvent((PlayerEntity) null, 1009, blockpos, 0);
				//}
				/* TODO fix this bullshit on top of the other two bullshits
				 * 	Its just sound effects and putting out fires, no biggie
				 * */
				blockstate.setValue(CampfireBlock.WATERLOGGED,  Boolean.valueOf(true));
				//Not sure this is needed now-		blockstate2.setValue(CampfireBlock.LIT,  Boolean.valueOf(false));
			}

			if (blockstate2 != null)
			{
				//if (world.isClientSide())
				//{
					world.setBlock(blockpos, blockstate2, 11);
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
			else
			{
				return ActionResultType.PASS;
			}
		}
	}

	@javax.annotation.Nullable
	public static BlockState getShovelPathingState(BlockState originalState)
	{
		return SHOVEL_LOOKUP.get(originalState.getBlock());
	}
}
