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

package eu.beezig.forge.gui.briefing.recentgames.csv;

public class ValueEntries {

    static final String rp = "beezigforge/gui/games/";

    public static String getValue(String[] data, LoggingGame game) {
        switch(game) {

            case bp:
                return safeValue(data, 0, "§7Points:§r ");
            case dr:
                return safeValue(data, 6, "§7Time:§r ");
            case cai:
                return safeValue(data, 0, "§7Points:§r ");
            case gnt:
                return safeValue(data, 8, "§7Points:§r ") + " §r/ " + safeValue(data, 2, "§7Kills:§r ")
                        + " §r/ " + safeValue(data, 3, "§7Deaths:§r ") + " §r/ " +
                        safeValue(data, 4, "§7Giants: §r");
            case sky:
                return safeValue(data, 0, "§7Points:§r ") + " §r/ " + safeValue(data, 2, "§7Kills:§r ");
            case hide:
                return safeValue(data, 2, "§7Kills:§r ") + " §r/ " + safeValue(data, 5, "§7Time Alive:§r ");
            case timv:
                return parseTimvRole(data) + " §r/ " + parseTimvKarma(data);
            case bedwars:
                return safeValue(data, 0, "§7Points:§r ") + " §r/ " + safeValue(data, 3, "§7Kills:§r ") + "§r / "
                        + safeValue(data, 4, "§7Deaths:§r ") + " §r/ " + safeValue(data, 5, "§7Beds:§r ");

        }
        return null;
    }

    private static String safeValue(String[] in, int slot, String prefix) {
        if(slot < 0 || slot >= in.length) return prefix + "N/A";
        return prefix + in[slot];
    }

    private static String parseTimvRole(String[] data) {
        if(0 >= data.length) return null;
        String role = data[0];
        return "§7Role: §r" + (role.equals("Innocent") ? "§aInnocent" : (role.equals("Traitor") ? "§cTraitor" : "§9Detective"));
    }

    private static String parseTimvKarma(String[] data) {
        if(1 >= data.length) return null;
        long karma = Long.parseLong(data[1]);

        if(karma > 0) return "§7Karma§r: §a+" + karma;
        else if(karma < 0) return "§7Karma§r: §c" + karma;
        else return "§7Karma§r: §e0";
    }

}
