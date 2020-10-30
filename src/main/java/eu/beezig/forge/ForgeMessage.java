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

package eu.beezig.forge;

import eu.beezig.forge.api.BeezigAPI;

public class ForgeMessage {
    public static String info = "§7▏ §aBeezig§7 ▏ §3";
    public static String error = "§7▏ §cBeezig§7 ▏ §c";

    public static String formatNumber(long l) {
        return BeezigAPI.formatNumber(l);
    }
    public static String translate(String key, Object... format) {
        return BeezigAPI.translate(key, format);
    }
    public static String translateOnOff(String key, boolean toggle) {
        return ForgeMessage.translate(key, ForgeMessage.translate(toggle ? "msg.on" : "msg.off"));
    }
}