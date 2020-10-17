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

package eu.beezig.forge.gui.briefing.json.lergin;

import eu.beezig.forge.ForgeMessage;

import java.util.Locale;

public enum StaffChangeType {
    MODERATOR_REMOVE("§c", "－"),
    MODERATOR_ADD("§c", "＋"),
    SENIOR_MODERATOR_REMOVE("§4",  "－"),
    SENIOR_MODERATOR_ADD("§4",  "＋"),
    NECTAR_REMOVE("§3", "－"),
    NECTAR_ADD("§3", "＋"),
    DEVELOPER_REMOVE("§7",  "－"),
    DEVELOPER_ADD("§7", "＋"),
    OWNER_REMOVE("§e", "－"),
    OWNER_ADD("§e", "＋");

    private String color;
    private String prefix;

    StaffChangeType(String color, String prefix) {
        this.color = color;
        this.prefix = prefix;
    }

    public String getDisplay() {
        return color + ForgeMessage.translate("staff." + name().toLowerCase(Locale.ROOT));
    }

    public String getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }
}
