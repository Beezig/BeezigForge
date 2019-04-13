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
