package com.andrielgaming.bulkminer.network.packets;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

// Packet sent client-to-server to break blocks interacted with by a BulkTool (left-click behaviors only)

public class BlockBreakPack
{
	public BlockPos key;

	public BlockBreakPack()
	{}

	public BlockBreakPack(BlockPos key)
	{
		this.key = key;
	}

	public static void encode(BlockBreakPack message, PacketBuffer buffer)
	{
		buffer.writeBlockPos(message.key);
	}

	public static BlockBreakPack decode(PacketBuffer buffer)
	{
		return new BlockBreakPack(buffer.readBlockPos());
	}

	public static void handle(BlockBreakPack message, Supplier<NetworkEvent.Context> contextSupplier)
	{
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() ->
		{
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> BlockBreakNetUtil.breakBlocks(message.key, contextSupplier.get().getSender()));
		});
		context.setPacketHandled(true);
	}
}
