package com.andrielgaming.bulkminer.registries;

import com.andrielgaming.bulkminer.BulkMiner;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
    public static final RegistryObject<Block> OBSIDIAN_BLOCK = BLOCKS.register("obsidian_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50f, 1600f).sound(SoundType.GILDED_BLACKSTONE).harvestLevel(3).harvestTool(ToolType.PICKAXE)));

    // Compressed Obsidian
    public static final RegistryObject<Block> COMPRESSED_OBSIDIAN = BLOCKS.register("compressed_obsidian", () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(75f, 2000f).sound(SoundType.GILDED_BLACKSTONE).harvestLevel(3).harvestTool(ToolType.PICKAXE)));
}
// Explosion