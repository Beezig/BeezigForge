package eu.beezig.forge.config.pointstag;


import eu.beezig.forge.modules.pointstag.PointsTagCache;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class TagConfigManager {

    private static Configuration config;

    public static Property enabled;
    public static Property offset;
    public static Property showSelf;
    public static Property formatting;
    public static Property colorAll;
    public static Property colorRank;



    public static void init(Configuration config) {
        TagConfigManager.config = config;
        enabled = config.get("ignored", "enabled", true,
                "Whether the point tags should show.");

        showSelf = config.get("ignored", "showself", true,
                "Whether the point tags should show on yourself");

        offset = config.get("ignored", "offset", 0d,
                "Offset of the tags, for compatibility");

        formatting = config.get("ignored", "formatting", "§3{k}: §a{v}",
                "Formatting of the tags");

        colorAll = config.get("ignored", "colorAll", true,
                "Whether the point tags should be colored");

        colorRank = config.get("ignored", "colorRank", true,
                "Whether the rank should be colored");


        PointsTagCache.self = showSelf.getBoolean();
        PointsTagCache.enabled = enabled.getBoolean();
        PointsTagCache.offset = offset.getDouble();
        PointsTagCache.formatting = formatting.getString();
        PointsTagCache.colorAll = colorAll.getBoolean();
        PointsTagCache.colorRank = colorRank.getBoolean();

    }

    public static void save() {
        config.save();
    }

}
