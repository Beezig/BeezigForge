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

package eu.beezig.forge.init;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

public class ClassFinder {

    private static Object plugmanImpl;
    private static Method findClass;

    public static void init() throws Exception {
        Class the5zigmod = Class.forName("eu.the5zig.mod.The5zigMod");

        Field apiField = the5zigmod.getDeclaredField("api");
        apiField.setAccessible(true);

        Object api = apiField.get(null);
        Object plugman = api.getClass().getMethod("getPluginManager").invoke(api);
        findClass = plugman.getClass().getDeclaredMethod("getClassByName", String.class, URLClassLoader.class);
        findClass.setAccessible(true);
        plugmanImpl = plugman;


    }

    public static Class findClass(String name) {
        try {
           return (Class) findClass.invoke(plugmanImpl, name, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
