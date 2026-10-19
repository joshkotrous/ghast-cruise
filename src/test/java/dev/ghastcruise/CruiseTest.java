package dev.ghastcruise;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.entity.player.Input;

public final class CruiseTest {
    private static void check(boolean value, String name) {
        if (!value) throw new AssertionError(name);
    }
    public static void run() {
        CruiseState state = new CruiseState(); Object ghast = new Object();
        check(!state.active(), "initially off");
        check(!state.toggle(null), "cannot start without driver mount");
        check(state.toggle(ghast), "start"); state.validate(ghast, false);
        check(state.active(), "release W keeps latch");
        check(!state.toggle(ghast), "toggle off");
        state.toggle(ghast); state.validate(ghast, true); check(!state.active(), "backward cancels");
        state.toggle(ghast); state.validate(null, false); check(!state.active(), "menu, focus loss or dismount cancels");
        state.toggle(ghast); state.validate(new Object(), false); check(!state.active(), "mount swap cancels");
        state.validate(ghast, false); check(!state.active(), "does not reenable after remount");
        for (int mask = 0; mask < 128; mask++) {
            Input physical = new Input((mask&1)!=0,(mask&2)!=0,(mask&4)!=0,(mask&8)!=0,(mask&16)!=0,(mask&32)!=0,(mask&64)!=0);
            Input out = CruiseInput.forward(physical);
            check(out.forward() && !out.backward(), "forward state");
            check(out.left()==physical.left() && out.right()==physical.right() && out.jump()==physical.jump()
                    && out.shift()==physical.shift() && out.sprint()==physical.sprint(), "other controls preserved");
            var vector = CruiseInput.vector(out);
            check(vector.y > 0 && vector.lengthSquared() <= 1.00001f, "normalized steering");
        }
        KeyEvent leftCtrl = new KeyEvent(InputConstants.KEY_W, InputConstants.KEYCODE_W, 64);
        KeyEvent rightCtrl = new KeyEvent(InputConstants.KEY_W, InputConstants.KEYCODE_W, 128);
        check(GhastCruise.isToggle(InputConstants.PRESS,leftCtrl), "SDL left Ctrl+W");
        check(GhastCruise.isToggle(InputConstants.PRESS,rightCtrl), "SDL right Ctrl+W");
        check(!GhastCruise.isToggle(InputConstants.REPEAT,leftCtrl), "repeat ignored");
        check(!GhastCruise.isToggle(InputConstants.RELEASE,leftCtrl), "release ignored");
        check(!GhastCruise.isToggle(InputConstants.PRESS,new KeyEvent(InputConstants.KEY_W,119,0)), "plain W ignored");
        System.out.println("PASS: latch cancellation, remount, 128 input combinations, steering, SDL Ctrl chord and repeat suppression");
    }
    public static void main(String[] args) { run(); }
}
