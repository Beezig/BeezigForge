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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Games {

    BED("total_points", "Points", true, "BedWars", true),
    TIMV("total_points", "Karma", true, "Trouble in Mineville", true),
    SKY("total_points", "Points", true, "SkyWars", true),
    CAI("total_points", "Points", true, "Cowboys and Indians", false),
    DR("total_points", "Points", true, "DeathRun", true),
    GRAV("points", "Points", true, "Gravity", true),
    HIDE("total_points", "Points", true, "Hide and Seek", true),
    LAB("total_points", "Atoms", true, "TheLab", false),
    GNT("total_points", "Points", true, "SkyGiants", false),
    GNTM("total_points", "Points", true, "SkyGiants:Mini", false),
    MIMV("total_points", "Karma", true, "Murder in Mineville", false),
    SGN("total_points", "Points", false, "Survival Games 2", true),
    BP("total_points", "Points", true, "BlockParty", true),
    SP("points", "Points", false, "Splegg", true),
    HB("points", "Points", false, "The Herobrine", false),
    DRAW("total_points", "Points", false, "DrawIt", true),
    EE("points", "Points", false, "Explosive Eggs", false),
    EF("points", "Points", false, "Electric Floor", false),
    CR("total_points", "Points", false, "Cranked", false),
    HERO("total_points", "Points", false, "SG:Heroes", false),
    OITC("total_points", "Points", false, "One in the Chamber", false),
    SPL("total_points", "Points", false, "Sploop", false),
    MM("points", "Points", false, "MusicMasters", false),
    SLAP("points", "Points", false, "Slaparoo", false),
    RR("points", "Points", false, "RestaurantRush", false),
    BD("total_points", "Points", false, "BatteryDash", false);

    private String points, display, commonName;
    private boolean supportsRanks, supportsDailies;

    Games(String points, String display, boolean supportsRanks, String commonName, boolean supportsDailies){
        this.points = points;
        this.display = display;
        this.supportsRanks = supportsRanks;
        this.commonName = commonName;
        this.supportsDailies = supportsDailies;
    }

    public String getPoints() {
        return points;
    }

    public String getDisplay() {
        return display;
    }

    public static Games value(String search) {
        try {
            return Games.valueOf(search);
        }
        catch(Exception e) {
            return null;
        }
    }

    public static List<Games> dailies() {
        return Stream.of(values()).filter(g -> g.supportsDailies).collect(Collectors.toList());
    }

    public boolean supportsRanks() {
        return supportsRanks;
    }

    public String getCommonName() {
        return commonName;
    }
}
