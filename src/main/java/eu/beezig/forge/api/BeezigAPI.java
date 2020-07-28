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

package eu.beezig.forge.api;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeezigAPI {
    static boolean onHive;
    static String currentGame;
    static Function<UUID, Integer> userRoleFunc;
    static Function<String, String> titleFunc;
    static Function<Long, String> formatFunc;
    static Function<String, String> translateFunc;
    static Function<Pair<String, Object[]>, String> translFormatFunc;
    static Function<String, String> mapFunc;
    static Supplier<String> regionFunc;

    public static boolean isOnHive() {
        return onHive;
    }

    public static String getCurrentGame() {
        return currentGame;
    }

    public static int getUserRole(UUID id) {
        return userRoleFunc.apply(id);
    }

    public static String getTitle(String api) {
        return titleFunc.apply(api);
    }

    public static String formatNumber(long l) {
        return formatFunc.apply(l);
    }

    public static String translate(String key, Object... format) {
        if(format.length == 0) return translateFunc.apply(key);
        else return translFormatFunc.apply(new ImmutablePair<>(key, format));
    }

    public static String normalizeMapName(String name) {
        return mapFunc.apply(name);
    }

    public static String getRegion() {
        return regionFunc.get();
    }
}
