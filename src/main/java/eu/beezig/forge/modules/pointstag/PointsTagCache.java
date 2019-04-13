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

package eu.beezig.forge.modules.pointstag;

import java.util.HashMap;
import java.util.UUID;

public class PointsTagCache {

    private static HashMap<UUID, PointsTag> cache = new HashMap<>();
    public static boolean enabled;
    public static double offset = 0;
    public static boolean self;
    public static String formatting;
    public static boolean colorAll;
    public static boolean colorRank;


    public static void putIfAbsent(UUID uuid, PointsTag cached) {
        cache.putIfAbsent(uuid, cached);
    }

    public static PointsTag get(UUID uuid) {
        return cache.get(uuid);
    }

    public static void clear() {
        cache.clear();
    }

}