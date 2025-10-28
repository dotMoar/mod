package dev._83080441.skyrain.ui.tabs;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, SkyRain.MOD_ID
    );

    public static final Supplier<CreativeModeTab> Sky_TAB = CREATIVE_TAB.register(
            "sky",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DAGGER.get()))
                    .title(Component.translatable("creativetab.skyrain.sky"))
                    .displayItems((itemDisplayParameters , output) ->{
                        output.accept(ModItems.DAGGER);
                        output.accept(ModItems.CINNABAR_ORE);
                        output.accept(ModItems.QUICKSILVER);
                        output.accept(ModItems.WAND);

                        output.accept(ModItems.RADISH);

                        output.accept(ModItems.FROST_FIRE);
                        output.accept(ModItems.STARLIGHT_ASHES);

                        output.accept(ModBlocks.MONOLITH);
                        output.accept(ModBlocks.MAGIC_BLOCK);
                        output.accept(ModBlocks.CINNABAR_BLOCK);
                    })
                    .build()
    );

    public static void register(IEventBus bus) {
        CREATIVE_TAB.register(bus);
    }
}
