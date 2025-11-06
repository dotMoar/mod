package dev._83080441.skyrain.item;

import dev._83080441.skyrain.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTool {
    public static final Tier MONOLITH = new SimpleTier(
            ModTags.Blocks.INCORRECT_FOR_MONOLITH_TOOLS,
            1400,
            4f,
            3f,
            28,
            () -> Ingredient.of(ModItems.QUICKSILVER)

    );
}
