package com.andrielgaming.bulkminer.registries;

import com.andrielgaming.bulkminer.BulkMiner;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockReg
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BulkMiner.MOD_ID);

    // Material Compressor
    //public static final RegistryObject<Compressor> COMP_BLOCK = BLOCKS.register("comp_block", () -> new Compressor(Block.Properties.create(Material.IRON).hardnessAndResistance(2.7f, 3.7f).sound(SoundType.LANTERN).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
    // Compact Obsidian
    		// Hardness and resistances are broken because someone on the Forge dev team is smoking crack when they make these updates
    public static final RegistryObject<Block> OBSIDIAN_BLOCK = BLOCKS.register("obsidian_block", () -> new Block(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundType.GILDED_BLACKSTONE).harvestLevel(3).harvestTool(ToolType.PICKAXE)));

    // Compressed Obsidian
    public static final RegistryObject<Block> COMPRESSED_OBSIDIAN = BLOCKS.register("compressed_obsidian", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundType.GILDED_BLACKSTONE).harvestLevel(3).harvestTool(ToolType.PICKAXE)));
}
// Explosion