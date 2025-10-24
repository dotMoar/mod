package dev._83080441.skyrain.block;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SkyRain.MOD_ID);

    public static final DeferredBlock<Block> MONOLITH = registerBlock("monolith",
            () -> new Block(BlockBehaviour.Properties
                    .of()
                    .strength(-1.0F, 3600000.0F) // opcional si quieres reforzarlo
                    .noLootTable()
                    .requiresCorrectToolForDrops()
                    .explosionResistance(3600000.0F)
                    .sound(SoundType.FUNGUS)
            ));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
