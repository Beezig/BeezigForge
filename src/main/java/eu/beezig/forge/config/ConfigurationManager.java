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
