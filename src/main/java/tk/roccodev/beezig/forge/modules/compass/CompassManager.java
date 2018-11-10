package tk.roccodev.beezig.forge.modules.compass;

public class CompassManager {

    public static boolean enabled = true;
    public static double size = 4D;

    public static boolean enabled4, enabled2, enabled1;

    public static boolean shouldRender(String mode) {
        if(mode == null || mode.isEmpty() ||  !enabled) return false;
        if(mode.equals("Teams") && enabled4) return true;
        if(mode.equals("Duo") && enabled2) return true;
        if(mode.equals("Solo") && enabled1) return true;
        return mode.equals("Double Fun") && enabled2;
    }

}
