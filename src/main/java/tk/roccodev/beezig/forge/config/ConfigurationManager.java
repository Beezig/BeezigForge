package tk.roccodev.beezig.forge.config;

import net.minecraftforge.common.config.Configuration;
import tk.roccodev.beezig.forge.config.pointstag.TagConfigManager;

import java.io.File;

public class ConfigurationManager {

    public static void initAll(File file) {
        TagConfigManager.init(new Configuration(new File(file.getParent() + "/BeezigForge/tags.conf")));
    }

}
