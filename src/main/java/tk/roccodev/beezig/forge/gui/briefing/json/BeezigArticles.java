package tk.roccodev.beezig.forge.gui.briefing.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tk.roccodev.beezig.forge.utils.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeezigArticles {

    private static final String URL = "https://roccodev.pw/beezighosting/news/news.json";

    public static List<BeezigArticle> fetch() {
        JSONArray json = JSON.downloadJSONArray(URL);
        List<BeezigArticle> result = new ArrayList<>();

        for(Object o : json) {
            JSONObject j = (JSONObject) o;
            BeezigArticle article = new BeezigArticle();
            article.setTitle(j.get("title").toString());
            article.setContent(j.get("content").toString());
            article.setPostedAt(new Date((long)j.get("postedAt")));

            result.add(article);
        }

        return result;
    }

}
