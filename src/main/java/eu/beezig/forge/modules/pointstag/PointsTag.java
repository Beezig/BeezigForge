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

import eu.beezig.forge.API;
import eu.beezig.forge.Log;
import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.utils.JSON;
import org.json.simple.JSONObject;

import java.util.Locale;

public class PointsTag {

    private String key, value, rank;
    private PointsTagStatus status;

    public void downloadData(String uuid) {
        status = PointsTagStatus.LOADING;
        new Thread(() -> {
            try {
                String gameStr = BeezigAPI.getCurrentGame().replace("ARCADE_", "");
                Games game = Games.value(gameStr.toUpperCase(Locale.ROOT));
                String prefix;
                String pts;
                boolean ranks;
                if (game == null) {
                    prefix = "Points";
                    pts = "total_points";
                    ranks = false;
                } else {
                    prefix = game.getDisplay();
                    pts = game.getPoints();
                    ranks = true;
                }
                this.key = prefix;
                JSONObject obj = JSON.downloadJSON("https://api.hivemc.com/v1/player/" + uuid + "/" + gameStr);
                this.value = Log.df((long) obj.get(pts));
                this.status = PointsTagStatus.DONE;
                if (ranks) {
                    String rankStr = BeezigAPI.getTitle(obj.get("title").toString());
                    if (rankStr != null) rank = rankStr;
                    if (game == Games.BED &&
                            obj.get("title").toString().startsWith("Sleepy ")
                            && (long) obj.get(pts) > 1500L)
                        rank = "§f§l✸ Zzzzzz";
                }
            } catch (Exception e) {
                e.printStackTrace();
                status = PointsTagStatus.ERRORED;
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
