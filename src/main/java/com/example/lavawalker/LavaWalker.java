package com.example.lavawalker;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(LavaWalker.MODID)
public class LavaWalker {
    public static final String MODID = "lavawalker";

    public LavaWalker(IEventBus modEventBus, ModContainer modContainer) {
        // Rien à enregistrer : tout passe par le mixin et les événements ci-dessous.
    }
}
