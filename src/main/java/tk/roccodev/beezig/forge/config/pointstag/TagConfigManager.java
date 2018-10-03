package tk.roccodev.beezig.forge.config.pointstag;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import tk.roccodev.beezig.forge.pointstag.PointsTagCache;

public class TagConfigManager {

    private static Configuration config;

    public static Property enabled;
    public static Property offset;
    public static Property showSelf;



    public static void init(Configuration config) {
        TagConfigManager.config = config;
        enabled = config.get("ignored", "enabled", true,
                "Whether the points tags should show.");

        showSelf = config.get("ignored", "showself", true,
                "Whether the points tags should show on yourself");

        offset = config.get("ignored", "offset", 0d,
                "Offset of the tags, for compatibility");


        PointsTagCache.self = showSelf.getBoolean();
        PointsTagCache.enabled = enabled.getBoolean();
        PointsTagCache.offset = offset.getDouble();

    }

    public static void save() {
        config.save();
    }

}
