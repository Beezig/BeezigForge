package tk.roccodev.beezig.forge.gamefields;

import tk.roccodev.beezig.forge.init.ClassFinder;

import java.lang.reflect.Field;

public class TIMV {

    private static Field currentEnderchestField;

    public static void callInit() {
        try {
            currentEnderchestField = ClassFinder.findClass("tk.roccodev.beezig.games.TIMV").getField("currentEnderchests");
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
