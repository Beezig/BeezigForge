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

package eu.beezig.forge.gui.briefing.json.lergin;

import eu.beezig.forge.utils.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

        return result.subList(0, 50);
    }

}
