package com.andrielgaming.bulkminer;

import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.andrielgaming.bulkminer.registries.BlockReg;
import com.andrielgaming.bulkminer.registries.ItemReg;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("agbulkminer")
public class BulkMiner
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static String MOD_ID = "agbulkminer";

    public BulkMiner()
    {
	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	modEventBus.addListener(this::setup);

	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup); // Register the setup method for modloading
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC); // Register the enqueueIMC method for modloading
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC); // Register the processIMC method for modloading
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff); // Register the doClientStuff method for modloading

	MinecraftForge.EVENT_BUS.register(this);

	BlockReg.BLOCKS.register(modEventBus);
	ItemReg.ITEMS.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
	// some example code to dispatch IMC to another mod
	InterModComms.sendTo("examplemod", "helloworld", () ->
	{
	    // LOGGER.info("Hello world from the MDK");
	    return "Hello from AG_IMC";
	});
    }

    private void processIMC(final InterModProcessEvent event)
    {
	// some example code to receive and process InterModComms from other mods
	LOGGER.info("Got IMC {}", event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
	// do something when the server starts
	LOGGER.info("HELLO from AG_server starting");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
	@SubscribeEvent
	public static void onBlocksRegistry(final RegistryEvent.Register<Item> blockRegistryEvent)
	{
	    final IForgeRegistry<Item> registry = blockRegistryEvent.getRegistry();

	    BlockReg.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block ->
	    {
		final Item.Properties properties = new Item.Properties().group(ItemGroup.MISC);
		final BlockItem blockItem = new BlockItem(block, properties);
		blockItem.setRegistryName(block.getRegistryName());
		registry.register(blockItem);
	    });

	    LOGGER.debug("Registered BlockItems!");

	}
    }
}
