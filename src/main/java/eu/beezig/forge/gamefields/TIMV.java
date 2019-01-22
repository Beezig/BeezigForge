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
