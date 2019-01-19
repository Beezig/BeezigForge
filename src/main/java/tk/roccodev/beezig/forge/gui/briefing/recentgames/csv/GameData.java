package tk.roccodev.beezig.forge.gui.briefing.recentgames.csv;

import java.util.Date;

public class GameData {

    private LoggingGame gamemode;
    private Date date;
    private String gameId;
    private boolean won;
    private boolean supportsWinning;
    private String value;
    private String link;

    private int x, y, width, height;
    private String clickHere;
    private boolean shown;

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
}
