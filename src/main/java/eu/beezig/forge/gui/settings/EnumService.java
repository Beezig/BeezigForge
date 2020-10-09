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

package eu.beezig.forge.gui.settings;

import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

public class EnumService {
    public static EnumData tryParseAsEnum(Object object) {
        Class cls = object.getClass();
        Method valueOf, values, name;
        try {
            valueOf = cls.getMethod("valueOf", String.class);
            values = cls.getMethod("values");
            name = cls.getMethod("name");
        } catch (NoSuchMethodException ignored) {
            return null;
        }
        try {
            String key = (String) name.invoke(object);
            String display = WordUtils.capitalize(key.toLowerCase(Locale.ROOT).replace("_", " "));
            Object[] possibleValues = (Object[]) values.invoke(null);
            EnumEntry[] parsed = Arrays.stream(possibleValues).map(o -> {
                String newKey;
                try {
                    newKey = (String) name.invoke(object);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                    return null;
                }
                return new EnumEntry(newKey, WordUtils.capitalize(newKey.toLowerCase(Locale.ROOT).replace("_", " ")));
            }).toArray(EnumEntry[]::new);
            EnumData result = new EnumData();
            result.realValue = object;
            result.possibleValues = parsed;
            result.value = new EnumEntry(key, display);
            result.valueOf = valueOf;
            return result;
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static class EnumEntry {
        private String key;
        private String display;

        public EnumEntry(String key, String display) {
            this.key = key;
            this.display = display;
        }

        public String getKey() {
            return key;
        }

        public String getDisplay() {
            return display;
        }
    }
    public static class EnumData {
        private EnumEntry value;
        private EnumEntry[] possibleValues;
        private Object realValue;
        private Method valueOf;

        public EnumEntry getValue() {
            return value;
        }

        public EnumEntry[] getPossibleValues() {
            return possibleValues;
        }

        public Object getRealValue() {
            return realValue;
        }

        public void setValue(EnumEntry value) {
            try {
                realValue = valueOf.invoke(null, value.key);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
                return;
            }
            this.value = value;
        }
    }
}
