package dev.ghastcruise.mixin;

import dev.ghastcruise.CruiseInput;
import dev.ghastcruise.GhastCruise;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyboardInput.class, remap = false)
public abstract class KeyboardInputMixin extends ClientInput {
    @Inject(method = "tick", at = @At("RETURN"), remap = false)
    private void ghastcruise$forward(CallbackInfo ci) {
        if (!GhastCruise.shouldMove(Minecraft.getInstance())) return;
        keyPresses = CruiseInput.forward(keyPresses);
        moveVector = CruiseInput.vector(keyPresses);
    }
}
