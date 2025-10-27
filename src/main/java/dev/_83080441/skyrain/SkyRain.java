package dev._83080441.skyrain;

import dev._83080441.skyrain.block.ModBlocks;
import dev._83080441.skyrain.item.ModItems;
import dev._83080441.skyrain.ui.tabs.CreativeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(SkyRain.MOD_ID)
public class SkyRain {
    public static final String MOD_ID = "skyrain";
    public static final Logger LOGGER = LogUtils.getLogger();


    public SkyRain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        CreativeTabs.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Crea pestana de tab y registra los items!
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    //        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
    //            event.accept(ModItems.DAGGER);
    //        }
    //
    //        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
    //            event.accept(ModBlocks.MONOLITH);
    //        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = SkyRain.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
