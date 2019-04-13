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

package eu.beezig.forge.utils;

import eu.beezig.forge.BeezigForgeMod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class JSON {

    public static JSONObject downloadJSON(String url) {
        try {
            return (JSONObject) new JSONParser().parse(read(url));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray downloadJSONArray(String url) {
        try {
            return (JSONArray) new JSONParser().parse(read(url));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Reader read(String urlStr) {
        HttpURLConnection conn;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "BeezigForge/" + BeezigForgeMod.VERSION);
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            return new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));

        } catch (Exception e) {
            return null;
        }
    }

}
