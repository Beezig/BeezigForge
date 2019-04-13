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

package eu.beezig.forge.gamefields;

import eu.beezig.forge.init.ClassFinder;

import java.lang.reflect.Field;

public class TIMV {

    private static Field currentEnderchestField;

    public static void callInit() {
        try {
            currentEnderchestField = ClassFinder.findClass("eu.beezig.core.games.TIMV").getField("currentEnderchests");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void setEnderchests(long enderchests) {
        try {
            currentEnderchestField.set(null, enderchests);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
