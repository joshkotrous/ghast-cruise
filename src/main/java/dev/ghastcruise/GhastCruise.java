package dev.ghastcruise;

import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import com.mojang.blaze3d.platform.InputConstants;

public final class GhastCruise {
    private static final CruiseState STATE = new CruiseState();
    private GhastCruise() {}

    private static HappyGhast eligibleMount(Minecraft client) {
        if (client.player == null || client.level == null || !client.player.isAlive()
                || !client.isWindowActive() || client.isPaused()
                || client.gui.screen() != null || client.gui.overlay() != null) return null;
        if (client.player.getVehicle() instanceof HappyGhast ghast
                && ghast.isAlive() && ghast.getControllingPassenger() == client.player) return ghast;
        return null;
    }

    public static void validate(Minecraft client) {
        boolean wasActive = STATE.active();
        STATE.validate(eligibleMount(client), client.options.keyDown.isDown() || client.options.keyShift.isDown());
        if (wasActive && !STATE.active()) status(client, false);
    }

    public static void key(Minecraft client, long window, int action, KeyEvent event) {
        if (window != client.getWindow().handle() || action != InputConstants.PRESS) return;
        validate(client);
        boolean toggle = isToggle(action, event);
        if (!toggle) return;
        HappyGhast mount = eligibleMount(client);
        if (mount == null || client.options.keyDown.isDown() || client.options.keyShift.isDown()) return;
        status(client, STATE.toggle(mount));
    }

    public static boolean isToggle(int action, KeyEvent event) {
        return action == InputConstants.PRESS && event.key() == InputConstants.KEY_W
                && (event.modifiers() & InputConstants.MOD_CONTROL) != 0;
    }

    public static boolean shouldMove(Minecraft client) {
        validate(client);
        return STATE.active();
    }

    private static void status(Minecraft client, boolean enabled) {
        if (client.player != null) client.player.sendOverlayMessage(
                Component.translatable(enabled ? "ghastcruise.enabled" : "ghastcruise.disabled"));
    }
}
