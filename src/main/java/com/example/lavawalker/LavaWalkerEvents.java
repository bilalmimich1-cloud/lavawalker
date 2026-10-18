package com.example.lavawalker;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Immunité feu/lave + vitesse bonus quand on marche sur la lave. */
@EventBusSubscriber(modid = LavaWalker.MODID)
public class LavaWalkerEvents {

    // Vitesse sur la lave : 0 = +20%, 1 = +40%, 2 = +60%, 3 = +80%, 4 = +100%...
    private static final int SPEED_LEVEL = 3;

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.isOnFire()) {
            player.clearFire();
        }

        if (player instanceof ServerPlayer
                && player.isInLava()
                && player.getFluidHeight(FluidTags.LAVA) <= 0.5
                && !player.isShiftKeyDown()) {
            MobEffectInstance current = player.getEffect(MobEffects.SPEED);
            if (current == null || current.getAmplifier() < SPEED_LEVEL || current.getDuration() < 10) {
                player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20, SPEED_LEVEL, false, false, false));
            }
        }
    }
}
