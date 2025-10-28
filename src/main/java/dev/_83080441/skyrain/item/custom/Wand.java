package dev._83080441.skyrain.item.custom;

import dev._83080441.skyrain.block.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Wand extends Item {
    private static final Map<Block, Block> BLOCK_MAP = Map.of(
            Blocks.STONE, Blocks.STONE_BRICKS,
            Blocks.END_STONE, Blocks.END_STONE_BRICKS,
            Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS,
            Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK,
            Blocks.OBSIDIAN, ModBlocks.MONOLITH.get()
    );

    public Wand(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (BLOCK_MAP.containsKey(clickedBlock)) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(context.getClickedPos(), BLOCK_MAP.get(clickedBlock).defaultBlockState());

                assert context.getPlayer() != null;
                context.getItemInHand().hurtAndBreak(
                        1,
                        context.getPlayer(),
                        EquipmentSlot.MAINHAND
                );

                level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.skyrain.wand.shift_down"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.skyrain.wand"));

        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
