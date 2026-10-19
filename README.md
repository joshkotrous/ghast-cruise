# Ghast Cruise

![Ghast Cruise icon](docs/icon.png)

**Ctrl+W cruise control for Happy Ghasts.** A small client-side Fabric mod for Minecraft 26.3.

## Controls

1. Ride a Happy Ghast in the driver's seat.
2. Press **Ctrl+W** once, then release W. Forward flight stays enabled.
3. Press **Ctrl+W** again, press your **backward key** (S by default), or dismount to cancel.

Opening a menu/chat/inventory, losing window focus, dying, changing mounts or losing the driver's seat also cancels cruise. It does not resume automatically. An action-bar message reports ON/OFF. Steering, looking up/down, jumping and other normal controls remain available. Cancellation releases automatic forward input; normal flight inertia remains.

The shortcut is fixed to Ctrl+W (either Ctrl key). The backward cancellation respects your movement keybind. Passenger seats and other mobs are unaffected. It does not hold an OS key, increase speed, avoid obstacles, navigate or keep you safe while away from the keyboard.

## Install

Requires **Minecraft 26.3**, **Fabric Loader 0.19.5 or newer** and **Java 25**. Put `ghast-cruise-1.0.0+mc26.3.jar` in your client's `mods` folder and restart. **Fabric API is not required.** Servers do not need the mod: it sends normal vanilla movement input. Follow each multiplayer server's automation rules.

Download the jar from [GitHub Releases](https://github.com/joshkotrous/ghast-cruise/releases). Remove the jar and restart to uninstall. No world/save changes or persistent cruise state.

## Build

The build uses Python 3, JDK 25 and your installed Minecraft/Fabric libraries. Game binaries are not included or downloaded by this repository.

```text
python build.py --install "C:/path/to/minecraft/Install" --java-home "C:/path/to/jdk25"
```

Output: `build/ghast-cruise-1.0.0+mc26.3.jar`.

The implementation targets 26.3's SDL input constants and GUI API. It does not use older GLFW key codes. Three small client mixins observe key presses, validate the latch each frame, and add forward input after vanilla keyboard sampling. Vanilla physical key states are never overwritten.

## Validation

Automated tests cover mount-bound cancellation and rearming, all 128 combinations of movement buttons, normalized steering, both Ctrl keys, and ignored key repeats/releases. An isolated Fabric prelaunch loaded all three transformed client targets successfully. These tests do not constitute a manual in-world flight test; the initial download is marked beta pending that verification.

## License and support

MIT. Original icon artwork included under the same license. Report bugs at [GitHub Issues](https://github.com/joshkotrous/ghast-cruise/issues), including Minecraft/loader versions and your mod list. No telemetry, networking service, or configuration file is added.
