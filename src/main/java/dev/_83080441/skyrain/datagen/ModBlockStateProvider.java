package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.data.PackOutput;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;


public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SkyRain.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CINNABAR_BLOCK);
        blockWithItem(ModBlocks.MONOLITH);
        blockWithItem(ModBlocks.MAGIC_BLOCK);

        stairsBlock(ModBlocks.MONOLITH_STAIR.get(), blockTexture(ModBlocks.MONOLITH.get()));

        slabBlock(ModBlocks.MONOLITH_SLAB.get(), blockTexture(ModBlocks.MONOLITH.get()), blockTexture(ModBlocks.MONOLITH.get()));

        buttonBlock(ModBlocks.MONOLITH_BUTTON.get(), blockTexture(ModBlocks.MONOLITH.get()));

        pressurePlateBlock(ModBlocks.MONOLITH_PRESSURE.get(), blockTexture(ModBlocks.MONOLITH.get()));

        fenceBlock(ModBlocks.MONOLITH_FENCE.get(), blockTexture(ModBlocks.MONOLITH.get()));
        fenceGateBlock(ModBlocks.MONOLITH_FENCE_GATE.get(), blockTexture(ModBlocks.MONOLITH.get()));
        wallBlock(ModBlocks.MONOLITH_WALL.get(), blockTexture(ModBlocks.MONOLITH.get()));

        doorBlockWithRenderType(ModBlocks.MONOLITH_DOOR.get(), modLoc("block/monolith_door_bottom"), modLoc("block/monolith_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.MONOLITH_TRAP_DOOR.get(), modLoc("block/monolith_trap_door"),true, "cutout");

        blockItem(ModBlocks.MONOLITH_STAIR);
        blockItem(ModBlocks.MONOLITH_SLAB);
        blockItem(ModBlocks.MONOLITH_PRESSURE);
        blockItem(ModBlocks.MONOLITH_FENCE_GATE);
        blockItem(ModBlocks.MONOLITH_TRAP_DOOR, "_bottom");
        blockItem(ModBlocks.MONOLITH_STAIR);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));

    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("skyrain:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("skyrain:block/" + deferredBlock.getId().getPath() + appendix));

    }
}
