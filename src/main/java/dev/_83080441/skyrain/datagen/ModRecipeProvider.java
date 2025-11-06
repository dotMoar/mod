package dev._83080441.skyrain.datagen;

import dev._83080441.skyrain.SkyRain;
import dev._83080441.skyrain.block.ModBlocks;
import dev._83080441.skyrain.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        List<ItemLike> CINNABAR_SMELTABLES = List.of(
                    ModItems.CINNABAR_ORE,
                    ModBlocks.CINNABAR_BLOCK
                );

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MONOLITH.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ModItems.QUICKSILVER.get())
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.QUICKSILVER.get(), 9)
                .requires(ModBlocks.MONOLITH)
                .unlockedBy("has_quicksilver", has(ModBlocks.MONOLITH))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.QUICKSILVER.get(), 18)
                .requires(ModBlocks.MAGIC_BLOCK)
                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
                .save(recipeOutput, "skyrain:quicksilver_from_magic_block");

        oreSmelting(recipeOutput,CINNABAR_SMELTABLES, RecipeCategory.MISC, ModItems.QUICKSILVER.get(), 0.25f, 200, "quicksilver" );
        oreBlasting(recipeOutput,CINNABAR_SMELTABLES, RecipeCategory.MISC, ModItems.QUICKSILVER.get(), 0.25f, 100, "quicksilver" );



        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.MONOLITH_SLAB.get(), ModItems.QUICKSILVER.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.MONOLITH_WALL.get(), ModItems.QUICKSILVER.get());

        pressurePlate(recipeOutput, ModBlocks.MONOLITH_PRESSURE.get(), ModItems.QUICKSILVER.get());

        stairBuilder(ModBlocks.MONOLITH_STAIR.get(),Ingredient.of(ModItems.QUICKSILVER))
                .group("monolith")
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER))
                .save(recipeOutput);

        buttonBuilder(ModBlocks.MONOLITH_BUTTON.get(), Ingredient.of(ModItems.QUICKSILVER.get()))
                .group("monolith")
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER))
                .save(recipeOutput);

        fenceBuilder(ModBlocks.MONOLITH_FENCE.get(), Ingredient.of(ModItems.QUICKSILVER.get()))
                .group("monolith")
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER))
                .save(recipeOutput);

        fenceGateBuilder(ModBlocks.MONOLITH_FENCE_GATE.get(), Ingredient.of(ModItems.QUICKSILVER.get()))
                .group("monolith")
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER))
                .save(recipeOutput);


        doorBuilder(ModBlocks.MONOLITH_DOOR.get(), Ingredient.of(ModItems.QUICKSILVER.get()))
                .group("monolith")
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER.get()))
                .save(recipeOutput);

        trapdoorBuilder(ModBlocks.MONOLITH_TRAPDOOR.get(), Ingredient.of(ModItems.QUICKSILVER.get()))
                .group("monolith")
                .unlockedBy("has_monolith", has(ModItems.QUICKSILVER))
                .save(recipeOutput);

    }

    protected static void oreSmelting(@NotNull RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(@NotNull RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.@NotNull Factory<T> factory,
                                                                       List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, SkyRain.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
