package dev.ghastcruise;

import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;

public final class CruiseInput {
    private CruiseInput() {}
    public static Input forward(Input input) {
        return new Input(true, false, input.left(), input.right(), input.jump(), input.shift(), input.sprint());
    }
    public static Vec2 vector(Input input) {
        float x = input.left() == input.right() ? 0 : input.left() ? 1 : -1;
        float y = input.forward() == input.backward() ? 0 : input.forward() ? 1 : -1;
        return new Vec2(x, y).normalized();
    }
}
