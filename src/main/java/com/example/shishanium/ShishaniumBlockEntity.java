package com.example.shishanium;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ShishaniumBlockEntity extends BlockEntity {
    private static final double RADIUS = 50.0D;
    private static final int EFFECT_DURATION = 40;
    private static final int MAX_AMPLIFIER = 255;

    public ShishaniumBlockEntity(BlockPos pos, BlockState state) {
        super(ShishaniumMod.SHISHANIUM_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ShishaniumBlockEntity blockEntity) {
        if (!(level instanceof ServerLevel serverLevel) || serverLevel.getGameTime() % 10 != 0) return;
        if (isShielded(serverLevel, pos)) return;

        AABB area = new AABB(pos).inflate(RADIUS);
        for (LivingEntity entity : serverLevel.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > RADIUS * RADIUS) continue;
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, EFFECT_DURATION, 0, true, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, EFFECT_DURATION, MAX_AMPLIFIER, true, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, EFFECT_DURATION, MAX_AMPLIFIER, true, true, true));
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, EFFECT_DURATION, MAX_AMPLIFIER, true, true, true));
        }
    }

    private static boolean isShielded(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(Blocks.IRON_BLOCK)
                && level.getBlockState(pos.below()).is(Blocks.IRON_BLOCK)
                && level.getBlockState(pos.north()).is(Blocks.IRON_BLOCK)
                && level.getBlockState(pos.south()).is(Blocks.IRON_BLOCK)
                && level.getBlockState(pos.east()).is(Blocks.IRON_BLOCK)
                && level.getBlockState(pos.west()).is(Blocks.IRON_BLOCK);
    }
}
