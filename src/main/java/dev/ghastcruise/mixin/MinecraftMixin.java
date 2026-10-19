package dev.ghastcruise.mixin;

import dev.ghastcruise.GhastCruise;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
    @Inject(method = "runTick", at = @At("HEAD"), remap = false)
    private void ghastcruise$validate(boolean render, CallbackInfo ci) {
        GhastCruise.validate((Minecraft) (Object) this);
    }
}
