package dev._83080441.skyrain.item;

import dev._83080441.skyrain.SkyRain;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SkyRain.MOD_ID);
    public static final DeferredItem<Item> DAGGER = ITEMS.register("dagger", () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

    };
}
