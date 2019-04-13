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

package eu.beezig.forge.gui.briefing.json;

import eu.beezig.forge.utils.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeezigArticles {

    private static final String URL = "https://rocco.dev/beezighosting/news/news.json";

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
