package tk.roccodev.beezig.forge.gui.briefing.json.lergin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tk.roccodev.beezig.forge.utils.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LerginFetcher {

    private static final String STAFF = "https://api.lergin.de/hive/team/0";
    private static final String MAPS = "https://api.lergin.de/hive/maps";

    public static List<StaffChange> getStaffChanges() {
        List<StaffChange> result = new ArrayList<>();

        JSONArray changes = JSON.downloadJSONArray(STAFF);

        for(Object o : changes) {
            JSONObject j = (JSONObject)o;
            StaffChange sc = new StaffChange();
            sc.setDate(new Date((long)j.get("date")));
            sc.setName(j.get("name").toString());
            sc.setType(j.get("type").toString());
            sc.setUuid(j.get("uuid").toString());

            result.add(sc);
        }

        return result;
    }

    public static List<NewMap> getMapChanges() {
        List<NewMap> result = new ArrayList<>();

        JSONArray changes = JSON.downloadJSONArray(MAPS);

        for(Object o : changes) {
            JSONObject j = (JSONObject)o;
            NewMap map = new NewMap();
            map.setDate((long)j.get("date"));
            if(j.get("mapName") != null)
                map.setName(j.get("mapName").toString());
            map.setMode(j.get("gameType").toString());

            result.add(map);
        }

        return result;
    }

}
