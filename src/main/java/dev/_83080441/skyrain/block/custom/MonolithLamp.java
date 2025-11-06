package dev._83080441.skyrain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class MonolithLamp extends Block {
    public static final BooleanProperty CLICK = BooleanProperty.create("clicked");

    public MonolithLamp(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CLICK, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level,
                                               BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            boolean newState = !state.getValue(CLICK);
            level.setBlock(pos, state.setValue(CLICK, newState), 3);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICK);
    }
}