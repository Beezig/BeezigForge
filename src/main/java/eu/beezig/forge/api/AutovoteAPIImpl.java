package eu.beezig.forge.api;


import eu.beezig.forge.init.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public interface AutovoteAPIImpl {

    List<String> getMapsForMode(String mode);
    void setMapsForMode(String mode, ArrayList<String> maps);



    static AutovoteAPIImpl fromObject(final Object from) {
        return new AutovoteAPIImpl() {
            @Override
            public List<String> getMapsForMode(String mode) {
                return (List<String>) ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "getMapsForMode", String.class), mode);
            }

            @Override
            public void setMapsForMode(String mode, ArrayList<String> maps) {
                ClassUtils.invokeMethod(from, ClassUtils.findMethod(from.getClass(), "setMapsForMode", String.class, ArrayList.class), mode, maps);
            }
        };
    }

}
