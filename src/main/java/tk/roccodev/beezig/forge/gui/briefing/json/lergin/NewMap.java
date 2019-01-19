package tk.roccodev.beezig.forge.gui.briefing.json.lergin;

import tk.roccodev.beezig.forge.modules.pointstag.Games;

import java.util.Date;

public class NewMap {

    private String mode, name;
    private Date date;

    public String getMode() {
        return mode;
    }

    void setMode(String mode) {
        Games game = Games.value(mode);
        if(game == null) {
            this.mode = mode;
            return;
        }
        this.mode = game.getCommonName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = new Date(date);
    }
}
