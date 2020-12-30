package com.andrielgaming.bulkminer.handlers.functionality;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

// Handler to cover all bulktool right-click functionalities, such as scythe harvesting
public class BlockInteractHandler
{
    public static ItemStack held;
    public static Block broken;
    public static PlayerEntity player;
    public static BlockPos pos;
    public static BlockRayTraceResult context;
    public static String dir;
    
    
}
