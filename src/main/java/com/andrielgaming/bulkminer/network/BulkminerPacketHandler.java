package com.andrielgaming.bulkminer.network;

import com.andrielgaming.bulkminer.BulkMiner;
import com.andrielgaming.bulkminer.network.packets.BlockBreakPack;
import com.andrielgaming.bulkminer.network.packets.BlockInteractionPack;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/*
 * Bulktools do not work on servers as of yet, as I never developed this with the intention of using it on servers.
 * Class intended to use SimpleImpl to tell the server "hey shithead, I broke or modified blocks over here"
 * May need separate packet for blocks breaking vs the Scythe behavior.
 */

public class BulkminerPacketHandler
{
	private static int id = 0;
	private static final String PROTOCOL_VERSION = "0.2";
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
	            .named(new ResourceLocation(BulkMiner.MOD_ID, "network"))
	            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
	            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
	            .networkProtocolVersion(() -> PROTOCOL_VERSION)
	            .simpleChannel();
	
	public static void init()
	{
		INSTANCE.registerMessage(++id, BlockBreakPack.class, BlockBreakPack::encode, BlockBreakPack::decode, BlockBreakPack::handle);//, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		INSTANCE.registerMessage(++id, BlockInteractionPack.class, BlockInteractionPack::encode, BlockInteractionPack::decode, BlockInteractionPack::handle);
	}
}
