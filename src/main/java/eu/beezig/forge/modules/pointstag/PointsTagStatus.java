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

package eu.beezig.forge.modules.pointstag;

public enum PointsTagStatus {

    UNKNOWN("?"),
    LOADING("?"),
    ERRORED("Error"),
    DONE(null);

    private String display;
    private static boolean steveMode = false;
    private static String steveText;

    PointsTagStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return steveMode ? steveText : display;
    }

    public static void enableSteveMode(String text) {
        steveMode = true;
        steveText = text;
    }

    public static boolean getSteveMode() {
        return steveMode;
    }
}
