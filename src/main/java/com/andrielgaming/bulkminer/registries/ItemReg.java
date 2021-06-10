package com.andrielgaming.bulkminer.registries;

import com.andrielgaming.bulkminer.BulkMiner;
import com.andrielgaming.bulkminer.items.tools.Dredge;
import com.andrielgaming.bulkminer.items.tools.Hammer;
import com.andrielgaming.bulkminer.items.tools.Scythe;
import com.andrielgaming.bulkminer.utils.ModItemTiers;

import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ToolItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemReg
{
    // Create DeferredRegister for items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BulkMiner.MOD_ID);

    // Hammers
    public static final RegistryObject<ToolItem> STONE_BK = ITEMS.register("stone_hammer", () -> new Hammer(ModItemTiers.BK_STONE, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> IRON_BK = ITEMS.register("iron_hammer", () -> new Hammer(ModItemTiers.BK_IRON, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> GOLD_BK = ITEMS.register("gold_hammer", () -> new Hammer(ModItemTiers.BK_GOLD, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> DIAMOND_BK = ITEMS.register("diamond_hammer", () -> new Hammer(ModItemTiers.BK_DIAMOND, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> OBSIDIAN_BK = ITEMS.register("obsidian_hammer", () -> new Hammer(ModItemTiers.BK_OBSIDIAN, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> NETHERITE_BK = ITEMS.register("netherite_hammer", () -> new Hammer(ModItemTiers.BK_NETHERITE, 1, -2.8F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));

    // Dredges
    public static final RegistryObject<ToolItem> STONE_DREDGE = ITEMS.register("stone_dredge", () -> new Dredge(ModItemTiers.BK_STONE, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> IRON_DREDGE = ITEMS.register("iron_dredge", () -> new Dredge(ModItemTiers.BK_IRON, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> GOLD_DREDGE = ITEMS.register("gold_dredge", () -> new Dredge(ModItemTiers.BK_GOLD, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> DIAMOND_DREDGE = ITEMS.register("diamond_dredge", () -> new Dredge(ModItemTiers.BK_DIAMOND, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> OBSIDIAN_DREDGE = ITEMS.register("obsidian_dredge", () -> new Dredge(ModItemTiers.BK_OBSIDIAN, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> NETHERITE_DREDGE = ITEMS.register("netherite_dredge", () -> new Dredge(ModItemTiers.BK_NETHERITE, 1.5F, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));

    // Sickles
    
    //TODO Disabled in current version due to crashes in SP
    /*public static final RegistryObject<ToolItem> STONE_SCYTHE = ITEMS.register("stone_scythe", () -> new Scythe(ModItemTiers.BK_STONE, 0, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> IRON_SCYTHE = ITEMS.register("iron_scythe", () -> new Scythe(ModItemTiers.BK_IRON, 0, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> GOLD_SCYTHE = ITEMS.register("gold_scythe", () -> new Scythe(ModItemTiers.BK_GOLD, 0, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe", () -> new Scythe(ModItemTiers.BK_DIAMOND, 0, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> OBSIDIAN_SCYTHE = ITEMS.register("obsidian_scythe", () -> new Scythe(ModItemTiers.BK_OBSIDIAN, 0, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<ToolItem> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe", () -> new Scythe(ModItemTiers.BK_NETHERITE, 0, -3.0F, new Item.Properties().tab(ItemGroup.TAB_TOOLS)));*/

    // Lumberjaxes
    
    // Saws

    // Ditch-witches
}
