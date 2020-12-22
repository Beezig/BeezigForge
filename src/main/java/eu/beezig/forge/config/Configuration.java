package eu.beezig.forge.config;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static transient final Gson GSON = new Gson();
    private Map<String, Property> properties = new HashMap<>();
    private transient File file;

    public static Configuration fromFile(File file) throws IOException {
        if(!file.exists()) {
            Configuration cfg = new Configuration();
            cfg.file = file;
            return cfg;
        }
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        Configuration cfg = GSON.fromJson(json, Configuration.class);
        cfg.file = file;
        return cfg;
    }

    public Property get(String key, Object defaultValue) {
        Property prop = properties.get(key);
        if(prop == null) {
            prop = new Property(defaultValue);
            properties.put(key, prop);
        }
        return prop;
    }

    public void save() throws IOException {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        String json = GSON.toJson(this);
        FileUtils.write(file, json, StandardCharsets.UTF_8, false);
    }
}
