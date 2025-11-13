package dev._83080441.skyrain.item;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.item.custom.Hammer;
import dev._83080441.skyrain.item.custom.Wand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SkyRain.MOD_ID);

    public static final DeferredItem<Item> CINNABAR_ORE = ITEMS.register("cinnabar_ore", () -> new Item(new Item.Properties()));

    //public static final DeferredItem<Item> DAGGER = ITEMS.register("dagger", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUICKSILVER = ITEMS.register("quicksilver", () -> new Item(new Item.Properties()));

    //Custom Items
    public static final DeferredItem<Item> WAND = ITEMS.register("wand", () -> new Wand(new Item.Properties().durability(32)));

    //Custom Food
    public static final DeferredItem<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(FoodItems.RADISH)) {
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

    }

    ;

    //Utilizables

    public static final DeferredItem<SwordItem> DAGGER = ITEMS.register(
            "dagger",
            () -> new SwordItem(ModTool.MONOLITH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(
                            ModTool.MONOLITH,
                            5,
                            0))
            )
    );

    public static final DeferredItem<PickaxeItem> MONOLITH_PICKAXE = ITEMS.register(
            "monolith_pickaxe",
            () -> new PickaxeItem(ModTool.MONOLITH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(
                            ModTool.MONOLITH,
                            5,
                            0))
            )
    );

    public static final DeferredItem<ShovelItem> MONOLITH_SHOVEL = ITEMS.register(
            "monolith_shovel",
            () -> new ShovelItem(ModTool.MONOLITH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(
                            ModTool.MONOLITH,
                            5,
                            0))
            )
    );

    public static final DeferredItem<AxeItem> MONOLITH_AXE = ITEMS.register(
            "monolith_axe",
            () -> new AxeItem(ModTool.MONOLITH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(
                            ModTool.MONOLITH,
                            5,
                            0))
            )
    );

    public static final DeferredItem<HoeItem> MONOLITH_HOE = ITEMS.register(
            "monolith_hoe",
            () -> new HoeItem(ModTool.MONOLITH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(
                            ModTool.MONOLITH,
                            5,
                            0))
            )
    );

    public static final DeferredItem<Hammer> HAMMER = ITEMS.register(
            "hammer",
            () -> new Hammer(ModTool.MONOLITH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(
                            ModTool.MONOLITH,
                            7f,
                            -5
                    )))
    );

    public static final DeferredItem<ArmorItem> MONOLITH_HELMET = ITEMS.register(
            "monolith_helmet",
            () -> new ArmorItem(ModArmorMaterials.MONOLITH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(99)))
    );

    public static final DeferredItem<ArmorItem> MONOLITH_CHESTPLATE = ITEMS.register(
            "monolith_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MONOLITH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(99)))
    );

    public static final DeferredItem<ArmorItem> MONOLITH_LEGGINS = ITEMS.register(
            "monolith_leggins",
            () -> new ArmorItem(ModArmorMaterials.MONOLITH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(99)))
    );

    public static final DeferredItem<ArmorItem> MONOLITH_BOOTS = ITEMS.register(
            "monolith_boots",
            () -> new ArmorItem(ModArmorMaterials.MONOLITH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(99)))
    );
}
