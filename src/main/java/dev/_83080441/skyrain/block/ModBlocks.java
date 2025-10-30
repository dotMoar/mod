package dev._83080441.skyrain.block;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.custom.MagicBlock;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SkyRain.MOD_ID);

    public static final DeferredBlock<Block> MONOLITH = registerBlock("monolith",
            () -> new Block(BlockBehaviour.Properties
                    .of()
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .requiresCorrectToolForDrops()
                    .explosionResistance(3600000.0F)
                    .sound(SoundType.FUNGUS)
            ));

    public static final DeferredBlock<Block> CINNABAR_BLOCK = registerBlock("cinnabar_block",
            () -> new Block(BlockBehaviour.Properties
                    .of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            ));

    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock("magic_block",
            () -> new MagicBlock(BlockBehaviour.Properties
                    .of()
                    .strength(1f)
                    .sound(SoundType.STONE)
            ));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    //Stair
    public static final DeferredBlock<StairBlock> MONOLITH_STAIR = registerBlock("monolith_stair",
            () -> new StairBlock(ModBlocks.MONOLITH.get().defaultBlockState(),
                    BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
                    )
    );

    //Slab
    public static final DeferredBlock<SlabBlock> MONOLITH_SLAB = registerBlock("monolith_slab",
            () -> new SlabBlock(BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
            )
    );

    //Pressure Plate
    public static final DeferredBlock<PressurePlateBlock> MONOLITH_PRESSURE = registerBlock("monolith_pressure",
            () -> new PressurePlateBlock(BlockSetType.IRON,
                    BlockBehaviour.Properties
                    .of()
                    .strength(-1.0F, 3600000.0F)
                    .requiresCorrectToolForDrops()
                    .explosionResistance(3600000.0F)
                    .sound(SoundType.FUNGUS)
            )
    );

    //Button Block
    public static final DeferredBlock<ButtonBlock> MONOLITH_BUTTON = registerBlock("monolith_button",
            () -> new ButtonBlock(BlockSetType.IRON, 20,
                    BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
                            .noCollission()
            )
    );

    //Fence
    public static final DeferredBlock<FenceBlock> MONOLITH_FENCE = registerBlock("monolith_fence",
            () -> new FenceBlock(BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
            )
    );

    //Fence gate
    public static final DeferredBlock<FenceGateBlock> MONOLITH_FENCE_GATE = registerBlock("monolith_fence_gate",
            () -> new FenceGateBlock(WoodType.BAMBOO,
                    BlockBehaviour.Properties
                    .of()
                    .strength(-1.0F, 3600000.0F)
                    .requiresCorrectToolForDrops()
                    .explosionResistance(3600000.0F)
                    .sound(SoundType.FUNGUS)
            )
    );

    //WallBlock
    public static final DeferredBlock<WallBlock> MONOLITH_WALL = registerBlock("monolith_wall",
            () -> new WallBlock(
                    BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
            )
    );

    //DoorBlock
    public static final DeferredBlock<DoorBlock> MONOLITH_DOOR = registerBlock("monolith_door",
            () -> new DoorBlock(BlockSetType.BAMBOO,
                    BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
                            .noOcclusion()
            )
    );

    //Trapdoor
    public static final DeferredBlock<TrapDoorBlock> MONOLITH_TRAP_DOOR = registerBlock("monolith_trap_door",
            () -> new TrapDoorBlock(BlockSetType.BAMBOO,
                    BlockBehaviour.Properties
                            .of()
                            .strength(-1.0F, 3600000.0F)
                            .requiresCorrectToolForDrops()
                            .explosionResistance(3600000.0F)
                            .sound(SoundType.FUNGUS)
                            .noOcclusion()
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
