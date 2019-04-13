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

package eu.beezig.forge.gui.briefing.xml;

import eu.beezig.forge.gui.briefing.tabs.Tabs;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RssParser {

    private static final String ANNOUNCEMENTS_URL = "http://feeds.feedburner.com/hive-announce";

    private static Document parseFeed() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(ANNOUNCEMENTS_URL);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Article get(Element parent) {
        Article result = new Article();
        result.setTitle(find("title", parent));
        try {
            result.setAuthor("§3by §b" + find("dc:creator", parent) + "§3 on §b"
                    + Tabs.sdf.format(Tabs.formatHive.parse(find("pubDate", parent))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result.setLink(find("link", parent));
        String escapedContent = StringEscapeUtils.unescapeHtml4(find("content:encoded", parent).replaceAll("<.*?>", ""))
                        .replaceAll("\u200b", "");

        String[] lines = escapedContent.split("\n");
        result.setContent(String.join("\n", Arrays.copyOf(lines, lines.length - 1)));

        return result;
    }

    private static String find(String tag, Element parent) {
        NodeList query = parent.getElementsByTagName(tag);
        if(query.getLength() == 0) return null;
        return query.item(0).getTextContent();
    }

    public static List<Article> getArticles() {
        Document d = parseFeed();
        if(d == null) return null;
        Element doc = d.getDocumentElement();
        NodeList articles = doc.getElementsByTagName("item");
        List<Article> result = new ArrayList<>();

        for(int i = 0; i < articles.getLength(); i++) {
            result.add(get((Element)articles.item(i)));
        }
        return result;
    }

}
