package com.example.lavawalker.mixin;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Utilise la méthode vanilla LivingEntity#canStandOnFluid (celle du Strider)
 * pour que le joueur puisse marcher sur la lave.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "canStandOnFluid", at = @At("HEAD"), cancellable = true)
    private void lavawalker$canStandOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player
                && fluidState.is(FluidTags.LAVA)
                && !player.isShiftKeyDown()                       // Shift = s'enfoncer dans la lave
                && player.getFluidHeight(FluidTags.LAVA) <= 0.5) { // trop enfoncé = on nage pour remonter
            cir.setReturnValue(true);
        }
    }
}
