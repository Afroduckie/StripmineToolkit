package com.andrielgaming.bulkminer.network.packets;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class BlockInteractionPack
{
	public BlockPos key;

	public BlockInteractionPack()
	{}

	public BlockInteractionPack(BlockPos key)
	{
		this.key = key;
	}

	public static void encode(BlockInteractionPack message, PacketBuffer buffer)
	{
		buffer.writeBlockPos(message.key);
	}

	public static BlockInteractionPack decode(PacketBuffer buffer)
	{
		return new BlockInteractionPack(buffer.readBlockPos());
	}

	public static void handle(BlockInteractionPack message, Supplier<NetworkEvent.Context> contextSupplier)
	{
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() ->
		{
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> BlockInteractionNetUtil.interact(message.key, contextSupplier.get().getSender()));
		});
		context.setPacketHandled(true);
	}
}
