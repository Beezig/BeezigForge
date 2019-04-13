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

package eu.beezig.forge.modules.compass;

public class CompassManager {

    public static boolean enabled = true;
    public static double size = 4D;

    public static boolean enabled4, enabled2, enabled1;

    public static boolean shouldRender(String mode) {
        if(mode == null || mode.isEmpty() ||  !enabled) return false;
        if(mode.equals("Teams") && enabled4) return true;
        if(mode.equals("Duo") && enabled2) return true;
        if(mode.equals("Solo") && enabled1) return true;
        return mode.equals("Double Fun") && enabled2;
    }

}
