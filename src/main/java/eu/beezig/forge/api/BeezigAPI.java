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

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeezigAPI {
    static boolean onHive;
    static String currentGame;
    static Function<UUID, Integer> userRoleFunc;
    static Function<Map.Entry<String, Integer>, String> titleFunc;
    static Function<Long, String> formatFunc;
    static Function<String, String> translateFunc;
    static Function<Pair<String, Object[]>, String> translFormatFunc;
    static Function<String, String> mapFunc;
    static Supplier<String> regionFunc;
    static Supplier<File> beezigDirFunc;
    static Function<String, Object> getSettingFunc;
    static Consumer<Map.Entry<String, Object>> setSettingFunc, setSettingAsIsFunc;
    static Function<UUID, Optional<Map<String, Object>>> getOverridesFunc;
    static Runnable saveConfig;

    // No fancy time-based cache needed
    private static Map<UUID, Map<String, Object>> overrideCache = new HashMap<>(5);

    public static boolean isOnHive() {
        return onHive;
    }

    public static String getCurrentGame() {
        return currentGame;
    }

    public static int getUserRole(UUID id) {
        return userRoleFunc.apply(id);
    }

    public static String getTitle(String api, int points) {
        return titleFunc.apply(new AbstractMap.SimpleImmutableEntry<>(api, points));
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

    public static File getBeezigDir() {
        return beezigDirFunc.get();
    }

    public static Object getSetting(String name) {
        return getSettingFunc.apply(name);
    }

    public static void setSetting(String name, Object value) {
        setSettingFunc.accept(new AbstractMap.SimpleImmutableEntry<>(name, value));
    }

    public static Optional<Map<String, Object>> getOverrides(UUID uuid) {
        if (overrideCache.containsKey(uuid))
            return Optional.of(overrideCache.get(uuid));
        Optional<Map<String, Object>> ret = getOverridesFunc.apply(uuid);
        ret.ifPresent(stringObjectMap -> overrideCache.put(uuid, stringObjectMap));
        return ret;
    }

    public static void saveConfig() {
        saveConfig.run();
    }

    /**
     * Sets the setting value to be exactly the provided object.
     */
    public static void setSettingAsIs(String name, Object value) {
        setSettingAsIsFunc.accept(new AbstractMap.SimpleImmutableEntry<>(name, value));
    }
}
