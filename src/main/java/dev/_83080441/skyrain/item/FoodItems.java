package dev._83080441.skyrain.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodItems {
    public static final FoodProperties RADISH = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.25f)
            .effect(()->new MobEffectInstance(MobEffects.CONFUSION, 400), 1f)
            .build();

}
