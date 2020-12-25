package com.andrielgaming.bulkminer.registries;

import com.andrielgaming.bulkminer.BulkMiner;
import com.andrielgaming.bulkminer.items.tools.Dredge;
import com.andrielgaming.bulkminer.items.tools.Hammer;
import com.andrielgaming.bulkminer.utils.ModItemTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemReg
{
    // Create DeferredRegister for items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BulkMiner.MOD_ID);
    
    // Register blockitems
    //public static final RegistryObject<BlockItem> COMPRESSOR_ITEM = ITEMS.register("compressor_item", () -> new BlockItem(BlockReg.COMP_BLOCK.get(), new Item.Properties().maxStackSize(64).group(ItemGroup.REDSTONE)));

    // Hammers
    public static final RegistryObject<ToolItem> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new Hammer(ModItemTiers.HAMMER_STONE, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new Hammer(ModItemTiers.HAMMER_IRON, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> GOLD_HAMMER = ITEMS.register("gold_hammer", () -> new Hammer(ModItemTiers.HAMMER_GOLD, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new Hammer(ModItemTiers.HAMMER_DIAMOND, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> OBSIDIAN_HAMMER = ITEMS.register("obsidian_hammer", () -> new Hammer(ModItemTiers.HAMMER_OBSIDIAN, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new Hammer(ModItemTiers.HAMMER_NETHERITE, 1, -2.8F, new Item.Properties().group(ItemGroup.TOOLS))); 
    
    // Dredges
    public static final RegistryObject<ToolItem> STONE_DREDGE = ITEMS.register("stone_dredge", () -> new Dredge(ModItemTiers.DREDGE_STONE, 1.5F, -3.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> IRON_DREDGE = ITEMS.register("iron_dredge", () -> new Dredge(ModItemTiers.DREDGE_IRON, 1.5F, -3.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> GOLD_DREDGE = ITEMS.register("gold_dredge", () -> new Dredge(ModItemTiers.DREDGE_GOLD, 1.5F, -3.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> DIAMOND_DREDGE = ITEMS.register("diamond_dredge", () -> new Dredge(ModItemTiers.DREDGE_DIAMOND, 1.5F, -3.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> OBSIDIAN_DREDGE = ITEMS.register("obsidian_dredge", () -> new Dredge(ModItemTiers.DREDGE_OBSIDIAN, 1.5F, -3.0F, new Item.Properties().group(ItemGroup.TOOLS)));
    public static final RegistryObject<ToolItem> NETHERITE_DREDGE = ITEMS.register("netherite_dredge", () -> new Dredge(ModItemTiers.DREDGE_NETHERITE, 1.5F, -3.0F, new Item.Properties().group(ItemGroup.TOOLS))); 
    
    // Sickles
    
    // Saws
    
    // Ditch-witches
}
