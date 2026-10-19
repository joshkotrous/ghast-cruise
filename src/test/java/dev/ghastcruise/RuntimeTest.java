package dev.ghastcruise;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
public final class RuntimeTest implements PreLaunchEntrypoint {
    public void onPreLaunch() {
        try {
            for (String name : new String[]{"net.minecraft.client.Minecraft", "net.minecraft.client.KeyboardHandler", "net.minecraft.client.player.KeyboardInput"})
                Class.forName(name, false, RuntimeTest.class.getClassLoader());
            CruiseTest.run();
            System.out.println("PASS: all three client mixin targets transformed under Fabric");
            System.exit(0);
        } catch (Throwable error) { error.printStackTrace(); System.exit(2); }
    }
}
