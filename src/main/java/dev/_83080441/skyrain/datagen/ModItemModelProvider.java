package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SkyRain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.CINNABAR_ORE.get());

        basicItem(ModItems.FROST_FIRE.get());
        basicItem(ModItems.WAND.get());
        basicItem(ModItems.RADISH.get());
        basicItem(ModItems.STARLIGHT_ASHES.get());
        basicItem(ModItems.QUICKSILVER.get());

        buttonItem(ModBlocks.MONOLITH_BUTTON, ModBlocks.MONOLITH);
        fenceItem(ModBlocks.MONOLITH_FENCE, ModBlocks.MONOLITH);
        wallItem(ModBlocks.MONOLITH_WALL, ModBlocks.MONOLITH);

        doorItem(ModBlocks.MONOLITH_DOOR, "monolith_door");

        handheldItem(ModItems.DAGGER);
        handheldItem(ModItems.MONOLITH_AXE);
        handheldItem(ModItems.MONOLITH_SHOVEL);
        handheldItem(ModItems.MONOLITH_PICKAXE);
        handheldItem(ModItems.MONOLITH_HOE);

    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SkyRain.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(SkyRain.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(SkyRain.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void doorItem(DeferredBlock<?> block, String textureName) {
        this.withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(SkyRain.MOD_ID, "block/" + textureName + "_bottom"));
    }

    public ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(SkyRain.MOD_ID, "item/" + item.getId().getPath()));
    }
}
