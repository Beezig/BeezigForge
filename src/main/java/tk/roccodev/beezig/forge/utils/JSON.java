package tk.roccodev.beezig.forge.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tk.roccodev.beezig.forge.BeezigForgeMod;

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
