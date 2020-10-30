/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.config.compass;

import eu.beezig.forge.config.Configuration;
import eu.beezig.forge.config.Property;
import eu.beezig.forge.modules.compass.CompassManager;
import eu.beezig.forge.modules.compass.render.CompassRenderListener;

import java.io.IOException;


public class CompassConfigManager {

    private static Configuration config;

    public static Property enabled;
    public static Property size;
    public static Property x;
    public static Property y;
    public static Property enabled4, enabled2, enabled1;

    public static void init(Configuration config) {
        CompassConfigManager.config = config;
        enabled = config.get("enabled", true);
        size = config.get("size", 4D);
        x = config.get("posX", 0);
        y = config.get("posY", 0);
        enabled4 = config.get("enabled4", true);
        enabled2 = config.get("enabled2", true);
        enabled1 = config.get("enabled1", true);

        CompassRenderListener.offX = x.getInt();
        CompassRenderListener.offY = y.getInt();
        CompassManager.enabled = enabled.getBoolean();
        CompassManager.size = size.getDouble();
        CompassManager.enabled4 = enabled4.getBoolean();
        CompassManager.enabled2 = enabled2.getBoolean();
        CompassManager.enabled1 = enabled1.getBoolean();
    }

    public static void save() {
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
