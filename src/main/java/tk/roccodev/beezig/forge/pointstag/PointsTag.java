package tk.roccodev.beezig.forge.pointstag;

import org.json.simple.JSONObject;
import tk.roccodev.beezig.forge.ActiveGame;
import tk.roccodev.beezig.forge.Log;
import tk.roccodev.beezig.forge.utils.JSON;

public class PointsTag {

    private String prefix, suffix;
    private PointsTagStatus status;

    public void downloadData(String uuid) {
        status = PointsTagStatus.LOADING;
        new Thread(() -> {
            String gameStr = ActiveGame.current().replace("ARCADE_", "");
            Games game = Games.value(gameStr);
            String prefix;
            String pts;
            if(game == null) {
                prefix = "Points";
                pts = "total_points";
            }
            else {
                prefix = game.getDisplay();
                pts = game.getPoints();
            }
            this.prefix = prefix;
            JSONObject obj = JSON.downloadJSON("https://api.hivemc.com/v1/player/" + uuid + "/" + gameStr);
            this.suffix = Log.df((long)obj.get(pts));
            this.status = PointsTagStatus.DONE;


        }).start();
    }

    public PointsTag() {
        status = PointsTagStatus.UNKNOWN;
        prefix = "Points";
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public PointsTagStatus getStatus() {
        return status;
    }

    public void setStatus(PointsTagStatus status) {
        this.status = status;
    }
}
