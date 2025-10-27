package dev._83080441.skyrain.item;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.item.custom.Wand;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SkyRain.MOD_ID);

    public static final DeferredItem<Item> CINNABAR_ORE = ITEMS.register("cinnabar_ore", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DAGGER = ITEMS.register("dagger", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUICKSILVER = ITEMS.register("quicksilver", () -> new Item(new Item.Properties()));

    //Custom Items
    public static final DeferredItem<Item> WAND = ITEMS.register("wand", () -> new Wand(new Item.Properties().durability(32)));

    //Custom Food
    public static final DeferredItem<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(FoodItems.RADISH)));

    //Fuel Items
    //public static final DeferredItem<Item> FROST_FIRE =

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

    };
}
