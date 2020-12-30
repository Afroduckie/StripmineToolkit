package com.andrielgaming.bulkminer.utils;

import java.util.function.Supplier;
import com.andrielgaming.bulkminer.registries.BlockReg;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ModItemTiers implements IItemTier
{

    // int harvestLevel, int maxUses, float efficiency, float attackDamage, int
    // enchantability, Supplier<Ingredient> repairMaterial
    BK_STONE(1, 256, 4.0F, 1.0F, 5, () ->
    {
	return Ingredient.fromItems(Items.SMOOTH_STONE);
    }),

    BK_IRON(2, 780, 6.0F, 2.0F, 14, () ->
    {
	return Ingredient.fromItems(Items.IRON_BLOCK);
    }),
    
    BK_GOLD(4, 296, 12.0F, 0.0F, 22, () ->
    {
	return Ingredient.fromItems(Items.GOLD_BLOCK);
    }),
    
    BK_DIAMOND(3, 1650, 8.0F, 3.0F, 10, () ->
    {
	return Ingredient.fromItems(Items.DIAMOND_BLOCK);
    }),
    
    BK_OBSIDIAN(4, 2048, 9.0F, 4.0F, 15, () ->
    {
	return Ingredient.fromItems(BlockReg.COMPRESSED_OBSIDIAN.get());
    }),
    
    BK_NETHERITE(4, 4096, 12.0F, 4.0F, 15, () ->
    {
	return Ingredient.fromItems(Items.NETHERITE_INGOT);
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private ModItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial)
    {
	this.harvestLevel = harvestLevel;
	this.maxUses = maxUses;
	this.efficiency = efficiency;
	this.attackDamage = attackDamage;
	this.enchantability = enchantability;
	this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getMaxUses()
    { return this.maxUses; }

    @Override
    public float getEfficiency()
    { return this.efficiency; }

    @Override
    public float getAttackDamage()
    { return this.attackDamage; }

    @Override
    public int getHarvestLevel()
    { return this.harvestLevel; }

    @Override
    public int getEnchantability()
    { return this.enchantability; }

    @Override
    public Ingredient getRepairMaterial()
    { return this.repairMaterial.getValue(); }
}
