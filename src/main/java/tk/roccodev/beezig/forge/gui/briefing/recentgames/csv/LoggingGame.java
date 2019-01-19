package tk.roccodev.beezig.forge.gui.briefing.recentgames.csv;

import tk.roccodev.beezig.forge.gamefields.TIMV;
import tk.roccodev.beezig.forge.modules.pointstag.Games;

import java.util.Date;

import static tk.roccodev.beezig.forge.modules.pointstag.Games.*;

public enum LoggingGame {

    bedwars(BED, "bedwars", true, -1, 7, 6),
    sky(SKY, "skywars", true, -1, 5, 4),
    timv(TIMV, "trouble-in-mineville", false, 7, 9, -1),
    bp(BP, "blockparty", false, -1, 2, -1),
    cai(CAI, "cowboys-and-indians", true, 3, 4, 2),
    dr(DR, "deathrun", false, 4, 5, -1),
    gnt(GNT, "skygiants", true, -1, 6, 5),
    hide(HIDE, "hide-and-seek", true, -1, 3, 1);

    private Games game;
    private boolean canWin;
    private int gameIdSlot, timestampSlot, victorySlot;
    private String linkName;

    LoggingGame(Games game, String linkName, boolean canWin, int gameIdSlot, int timestampSlot, int victorySlot) {
        this.game = game;
        this.canWin = canWin;
        this.gameIdSlot = gameIdSlot;
        this.timestampSlot = timestampSlot;
        this.victorySlot = victorySlot;
        this.linkName = linkName;
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
}
