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
