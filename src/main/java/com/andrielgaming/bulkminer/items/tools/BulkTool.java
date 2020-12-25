package com.andrielgaming.bulkminer.items.tools;

import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ToolItem;

/* Solely exists to be a parent class to all individual strip mining tools so I can use instanceof calls and more dynamic, homogenized code
 * 	The only real function it has code-wise is to pass its parameters from its children to ToolItem
 * */
public abstract class BulkTool extends ToolItem
{
    public BulkTool(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveBlocksIn, Properties builderIn)
    {
	super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builderIn);
	// TODO Auto-generated constructor stub
    }

}
