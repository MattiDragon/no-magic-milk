package io.github.mattidragon.nomagicmilk.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccess {
    @Invoker("onStatusEffectRemoved")
    void removeStatusEffect(StatusEffectInstance effect);
}
