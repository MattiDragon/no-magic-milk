package io.github.mattidragon.nomagicmilk.mixin;

import io.github.mattidragon.nomagicmilk.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.Map;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Redirect(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    public boolean injectEffects(LivingEntity entity) {
        Map<StatusEffect, StatusEffectInstance> map = entity.getActiveStatusEffects();
        ModConfig config = ModConfig.getInstance();
        
        boolean changed = false;
    
        for (Iterator<StatusEffect> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            StatusEffect effect = iterator.next();
            StatusEffectInstance instance = map.get(effect);
        
            boolean isAllowed = config.effectFilter.contains(String.valueOf(Registry.STATUS_EFFECT.getId(effect)));
            if (config.isBlacklist) isAllowed = !isAllowed;
        
            if (isAllowed) {
                map.put(effect, new StatusEffectInstance(effect,
                        (int) (instance.getDuration() * config.durationMultiplier),
                        (int) ((instance.getAmplifier() + 1) * config.amplifierMultiplier) - 1,
                        instance.isAmbient(),
                        instance.shouldShowParticles(),
                        instance.shouldShowIcon()));
                if (instance.equals(map.get(effect))) changed = true;
                if (entity instanceof ServerPlayerEntity player) {
                    player.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(player.getId(), map.get(effect)));
                }
            } else {
                iterator.remove();
                ((LivingEntityAccess)entity).removeStatusEffect(instance);
                changed = true;
            }
        }
        
        return changed;
    }
}
