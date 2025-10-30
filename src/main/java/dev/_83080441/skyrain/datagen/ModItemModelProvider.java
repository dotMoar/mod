package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SkyRain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.CINNABAR_ORE.get());
        basicItem(ModItems.DAGGER.get());
        basicItem(ModItems.FROST_FIRE.get());
        basicItem(ModItems.WAND.get());
        basicItem(ModItems.RADISH.get());
        basicItem(ModItems.STARLIGHT_ASHES.get());
        basicItem(ModItems.QUICKSILVER.get());


    }
}
