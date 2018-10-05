package tk.roccodev.beezig.forge.config.compass;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tk.roccodev.beezig.forge.modules.compass.CompassManager;
import tk.roccodev.beezig.forge.modules.compass.render.CompassRenderListener;


public class CompassConfigManager {

    private static Configuration config;

    public static Property enabled;
    public static Property size;
    public static Property x;
    public static Property y;

    public static void init(Configuration config) {
        CompassConfigManager.config = config;
        enabled = config.get("ignored", "enabled", true,
                "Whether the Bedwars Teams Compass should show.");

        size = config.get("ignored", "size", 4D,
                "Size of the dots");

        x = config.get("ignored", "posX", 0,
                "X Coordinate of the position");

        y = config.get("ignored", "posY", 0,
                "Y Coordinate of the position");


        CompassRenderListener.offX = x.getInt();
        CompassRenderListener.offY = y.getInt();
        CompassManager.enabled = enabled.getBoolean();
        CompassManager.size = size.getDouble();



    }

    public static void save() {
        config.save();
    }

}
