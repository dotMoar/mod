package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.item.ModItems;
import dev._83080441.skyrain.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SkyRain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.RADISH.get())
                .add(Items.COAL);

        tag(ItemTags.SWORDS)
                .add(ModItems.DAGGER.get());

        tag(ItemTags.AXES)
                .add(ModItems.MONOLITH_AXE.get());

        tag(ItemTags.HOES)
                .add(ModItems.MONOLITH_HOE.get());

        tag(ItemTags.SHOVELS)
                .add(ModItems.MONOLITH_SHOVEL.get());

        tag(ItemTags.PICKAXES)
                .add(ModItems.MONOLITH_PICKAXE.get());
    }
}
