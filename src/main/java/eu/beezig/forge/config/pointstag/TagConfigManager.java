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

package eu.beezig.forge.config.pointstag;


import eu.beezig.forge.config.Configuration;
import eu.beezig.forge.config.Property;
import eu.beezig.forge.modules.pointstag.PointsTagCache;

import java.io.IOException;

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
        enabled = config.get("enabled", true);
        showSelf = config.get("showself", true);
        offset = config.get("offset", 0d);
        formatting = config.get("formatting", "§3{k}: §a{v}");
        colorAll = config.get("colorAll", true);
        colorRank = config.get("colorRank", true);

        PointsTagCache.self = showSelf.getBoolean();
        PointsTagCache.enabled = enabled.getBoolean();
        PointsTagCache.offset = offset.getDouble();
        PointsTagCache.formatting = formatting.getString();
        PointsTagCache.colorAll = colorAll.getBoolean();
        PointsTagCache.colorRank = colorRank.getBoolean();
    }

    public static void save() {
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
