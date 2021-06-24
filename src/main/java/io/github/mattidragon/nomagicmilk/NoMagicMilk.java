package io.github.mattidragon.nomagicmilk;

import io.github.mattidragon.nomagicmilk.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class NoMagicMilk implements ModInitializer {
    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }
}
