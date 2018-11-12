package tk.roccodev.beezig.forge.modules.pointstag;

import org.json.simple.JSONObject;
import tk.roccodev.beezig.forge.API;
import tk.roccodev.beezig.forge.ActiveGame;
import tk.roccodev.beezig.forge.Log;
import tk.roccodev.beezig.forge.utils.JSON;

public class PointsTag {

    private String key, value, rank;
    private PointsTagStatus status;

    public void downloadData(String uuid) {
        status = PointsTagStatus.LOADING;
        new Thread(() -> {
            String gameStr = ActiveGame.current().replace("ARCADE_", "");
            Games game = Games.value(gameStr);
            String prefix;
            String pts;
            boolean ranks;
            if(game == null) {
                prefix = "Points";
                pts = "total_points";
                ranks = false;
            }
            else {
                prefix = game.getDisplay();
                pts = game.getPoints();
                ranks = true;
            }
            this.key = prefix;
            JSONObject obj = JSON.downloadJSON("https://api.hivemc.com/v1/player/" + uuid + "/" + gameStr);
            this.value = Log.df((long)obj.get(pts));
            this.status = PointsTagStatus.DONE;
            if(ranks) {
                String rankStr = API.inst.getRankString(obj.get("title").toString(), gameStr);
                if(rankStr != null) rank = rankStr;
                if(game == Games.BED &&
                        obj.get("title").toString().startsWith("Sleepy ")
                        && (long)obj.get(pts) > 1500L)
                    rank = "§f§l✸ Zzzzzz";
            }

        }).start();
    }

    public PointsTag() {
        status = PointsTagStatus.UNKNOWN;
        key = "Points";
        rank = "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public PointsTagStatus getStatus() {
        return status;
    }

    public void setStatus(PointsTagStatus status) {
        this.status = status;
    }
}
