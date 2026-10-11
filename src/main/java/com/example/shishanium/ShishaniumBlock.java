package com.example.shishanium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShishaniumBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(
            box(3, 0, 3, 5, 16, 5), box(11, 0, 3, 13, 16, 5),
            box(3, 0, 11, 5, 16, 13), box(11, 0, 11, 13, 16, 13),
            box(7, 0, 7, 9, 16, 9));

    protected ShishaniumBlock(Properties properties) { super(properties); }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return SHAPE; }
    @Override public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return SHAPE; }
    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShishaniumBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return type == ShishaniumMod.SHISHANIUM_BE.get() ? (lvl, pos, st, be) -> {
            if (be instanceof ShishaniumBlockEntity shishanium) ShishaniumBlockEntity.tick(lvl, pos, st, shishanium);
        } : null;
    }
}
