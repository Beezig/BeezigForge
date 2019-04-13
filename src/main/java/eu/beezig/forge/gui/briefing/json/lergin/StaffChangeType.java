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

public enum StaffChangeType {


    MODERATOR_REMOVE("§c", "Retiring Moderator", "－"),
    MODERATOR_ADD("§c", "New Moderator", "＋"),
    SENIOR_MODERATOR_REMOVE("§4", "Retiring Sr. Moderator", "－"),
    SENIOR_MODERATOR_ADD("§4", "New Sr. Moderator", "＋"),
    NECTAR_REMOVE("§3", "Retiring Builder", "－"),
    NECTAR_ADD("§3", "New Builder", "＋"),
    DEVELOPER_REMOVE("§7", "Retiring Developer", "－"),
    DEVELOPER_ADD("§7", "New Developer", "＋"),
    OWNER_REMOVE("§e", "Retiring Owner", "－"),
    OWNER_ADD("§e", "New Owner", "＋");


    private String display;
    private String color;
    private String prefix;

    StaffChangeType(String color, String display, String prefix) {
        this.display = display;
        this.color = color;
        this.prefix = prefix;
    }

    public String getDisplay() {
        return color + display;
    }

    public String getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }
}
