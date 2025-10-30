package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SkyRain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CINNABAR_BLOCK.get());

        tag(BlockTags.FENCES).add(ModBlocks.MONOLITH_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.MONOLITH_FENCE_GATE.get());
        tag(BlockTags.WALLS).add(ModBlocks.MONOLITH_WALL.get());

    }


}
