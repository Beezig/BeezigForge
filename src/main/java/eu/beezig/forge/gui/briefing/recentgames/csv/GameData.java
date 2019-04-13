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

import eu.beezig.forge.gui.briefing.tabs.TabRenderUtils;
import eu.beezig.forge.gui.briefing.tabs.Tabs;

import java.util.Date;

public class GameData {

    private LoggingGame gamemode;
    private Date date;
    private String gameId;
    private boolean won;
    private boolean supportsWinning;
    private String value;
    private String link, map, mode;

    private int x, y, width, height;
    private String clickHere;
    private boolean shown;
    private TabRenderUtils render = TabRenderUtils.getInstance();

    private String[] title = new String[0],
            below = new String[0],
            content = new String[0];

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public String getLink() {
        return link;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public LoggingGame getGamemode() {
        return gamemode;
    }

    public boolean supportsWinning() {
        return supportsWinning;
    }

    long getTime() {
        if(date == null) return 0;
        return date.getTime();
    }

    public void setSupportsWinning(boolean supportsWinning) {
        this.supportsWinning = supportsWinning;
    }

    public void setGamemode(LoggingGame gamemode) {
        this.gamemode = gamemode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGameId() {
        return gameId;
    }

    public String getChatComponent() {
        return clickHere;
    }

    public void setGameId(String gameId) {
        if(gameId != null) {
            clickHere = "§6[Game Log]";
            link = "https://hivemc.com/" + gamemode.getLinkName() + "/game/" + gameId;
        }
        this.gameId = gameId;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void initText(int windowWidth) {
        String color = getGamemode().canWin() ? (isWon() ? "§a" : "§c") : "§b";

        // Adapt strings to fit into the box
        title = render.listFormattedStringToWidth(color + "§l" + getGamemode().getGame().getCommonName(),
                windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5 - 10).toArray(new String[0]);

        if(getValue() != null) {
            content = render.listFormattedStringToWidth(getValue(),
                    windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5).toArray(new String[0]);
        }

        String date = getDate() == null ? "Unknown Date" : Tabs.time.format(getDate());

        if(getMap() != null && !getMap().isEmpty()) date += "§3 on §b" + getMap();
        if(getMode() != null && !getMode().isEmpty()) date += " (" + getMode() + ")";

         below = render.listFormattedStringToWidth(date,
                windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5).toArray(new String[0]);
    }


    public String[] getContent() {
        return content;
    }

    public String[] getBelow() {
        return below;
    }

    public String[] getTitle() {
        return title;
    }
}
