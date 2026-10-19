package dev.ghastcruise.mixin;

import dev.ghastcruise.GhastCruise;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyboardHandler.class, remap = false)
public abstract class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), remap = false)
    private void ghastcruise$key(long window, int action, KeyEvent event, CallbackInfo ci) {
        GhastCruise.key(Minecraft.getInstance(), window, action, event);
    }
}
