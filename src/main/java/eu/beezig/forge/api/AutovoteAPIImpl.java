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
