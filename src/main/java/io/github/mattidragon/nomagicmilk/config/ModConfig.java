package io.github.mattidragon.nomagicmilk.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "no_magic_milk")
public class ModConfig implements ConfigData {
    public static ModConfig getInstance() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    @ConfigEntry.BoundedDiscrete(max = 1)
    public float durationMultiplier = 0.0f;
    @ConfigEntry.BoundedDiscrete(max = 1)
    public float amplifierMultiplier = 0.0f;
    
    public boolean isBlacklist = true;
    public List<String> effectFilter = new ArrayList<>();
}
