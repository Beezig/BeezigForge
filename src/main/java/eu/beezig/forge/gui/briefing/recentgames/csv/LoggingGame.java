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

import net.minecraft.util.ResourceLocation;
import eu.beezig.forge.modules.pointstag.Games;

import java.util.Date;

import static eu.beezig.forge.gui.briefing.recentgames.csv.ValueEntries.rp;
import static eu.beezig.forge.modules.pointstag.Games.*;

public enum LoggingGame {

    bedwars(BED, "bedwars", true, 8, 7, 6, 2, 1, new ResourceLocation(rp + "bed.png")),
    sky(SKY, "skywars", true, 6, 5, 4, 1, 3, new ResourceLocation(rp + "sky.png")),
    timv(TIMV, "trouble-in-mineville", false, 7, 9, -1, 2, -1, new ResourceLocation(rp + "timv.png")),
    bp(BP, "blockparty", false, -1, 2, -1, 1, -1, new ResourceLocation(rp + "bp.png")),
    cai(CAI, "cowboys-and-indians", true, 3, 4, 2, 1, -1, new ResourceLocation(rp + "cai.png")),
    dr(DR, "deathrun", false, 4, 5, -1, 1, -1, new ResourceLocation(rp + "dr.png")),
    gnt(GNT, "skygiants", true, 7, 6, 5, 1, 0, new ResourceLocation(rp + "gnt.png")),
    hide(HIDE, "hide-and-seek", true, 4, 3, 1, 0, -1, new ResourceLocation(rp + "hide.png"));

    private Games game;
    private boolean canWin;
    private int gameIdSlot, timestampSlot, victorySlot, mapSlot, modeSlot;
    private String linkName;
    private ResourceLocation icon;



    LoggingGame(Games game, String linkName, boolean canWin, int gameIdSlot, int timestampSlot,
                int victorySlot, int mapSlot, int modeSlot, ResourceLocation icon) {
        this.game = game;
        this.canWin = canWin;
        this.gameIdSlot = gameIdSlot;
        this.timestampSlot = timestampSlot;
        this.victorySlot = victorySlot;
        this.linkName = linkName;
        this.mapSlot = mapSlot;
        this.modeSlot = modeSlot;
        this.icon = icon;
    }

    public Games getGame() {
        return game;
    }

    public boolean canWin() {
        return canWin;
    }

    public String getLinkName() {
        return linkName;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public String getGameId(String[] in) {
        if(gameIdSlot < 0 || gameIdSlot >= in.length) return null;
        return in[gameIdSlot];
    }

    public boolean won(String[] in) {
        if(victorySlot < 0 || victorySlot >= in.length) return false;
        return in[victorySlot].equals("Yes") || in[victorySlot].equals("true");
    }

    public Date getTimestamp(String[] in) {
        if(timestampSlot < 0 || timestampSlot >= in.length) return null;
        return new Date(Long.parseLong(in[timestampSlot]));
    }

    public String getMap(String[] in) {
        if(mapSlot < 0 || mapSlot >= in.length) return null;
        return in[mapSlot];
    }

    public String getMode(String[] in) {
        if(modeSlot < 0 || modeSlot >= in.length) return null;
        return in[modeSlot];
    }
}
