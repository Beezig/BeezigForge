package eu.beezig.forge.config;

import eu.beezig.forge.config.compass.CompassConfigManager;
import eu.beezig.forge.config.pointstag.TagConfigManager;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationManager {

    public static String configParent;

    public static void initAll(File file) {
        TagConfigManager.init(new Configuration(new File(file.getParent() + "/BeezigForge/tags.conf")));
        CompassConfigManager.init(new Configuration(new File(file.getParent() + "/BeezigForge/bcompass.conf")));

        configParent = file.getParent() + "/BeezigForge/";
    }

}
