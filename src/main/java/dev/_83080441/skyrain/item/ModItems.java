package dev._83080441.skyrain.item;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.item.custom.Wand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SkyRain.MOD_ID);

    public static final DeferredItem<Item> CINNABAR_ORE = ITEMS.register("cinnabar_ore", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DAGGER = ITEMS.register("dagger", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUICKSILVER = ITEMS.register("quicksilver", () -> new Item(new Item.Properties()));

    //Custom Items
    public static final DeferredItem<Item> WAND = ITEMS.register("wand", () -> new Wand(new Item.Properties().durability(32)));

    //Custom Food
    public static final DeferredItem<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(FoodItems.RADISH)){
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.translatable("tooltip.skyrain.radish"));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
    });

    //Fuel Items
    public static final DeferredItem<Item> FROST_FIRE = ITEMS.register("frost_fire",
            () -> new FuelItems(new Item.Properties(), 800));
    public static final DeferredItem<Item> STARLIGHT_ASHES = ITEMS.register("starlight_ashes",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);

    };
}
