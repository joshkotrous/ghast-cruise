package dev.ghastcruise;

/** Client-thread-only latch tied to the exact mount instance. */
public final class CruiseState {
    private Object mount;
    public boolean active() { return mount != null; }
    public void stop() { mount = null; }
    public boolean toggle(Object eligibleMount) {
        if (active()) stop();
        else mount = eligibleMount;
        return active();
    }
    public void validate(Object eligibleMount, boolean backward) {
        if (mount != eligibleMount || backward) stop();
    }
}
