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

package eu.beezig.forge.gui.briefing.tabs.items;

import eu.beezig.forge.gui.briefing.tabs.Tab;
import eu.beezig.forge.gui.briefing.tabs.TabRenderUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import eu.beezig.forge.gui.briefing.xml.Article;
import eu.beezig.forge.gui.briefing.xml.RssParser;

import java.awt.*;
import java.net.URI;
import java.util.List;


public class HiveNewsTab extends Tab {

    private List<Article> newsArticles = null;
    private TabRenderUtils render = new TabRenderUtils(getStartY());
    private double scrollY;

    public HiveNewsTab() {
        super("Hive News", new ResourceLocation("beezigforge/gui/hive.png"));
    }

    @Override
    protected void init(int windowWidth, int windowHeight) {
        super.init(windowWidth, windowHeight);
        new Thread(() -> newsArticles = RssParser.getArticles()).start();

    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        if(newsArticles == null)
            centered("Loading, please wait...", windowWidth / 2, 0, Color.WHITE.getRGB());
        else {
            int y = getStartY() + (int)scrollY;
            for(Article article : newsArticles) {
                int stringY = y + 12;
                // Adapt strings to fit into the box
                List<String> title = render.listFormattedStringToWidth("§b§l" + article.getTitle(),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5 - 10);
                stringY += title.size() * 12;
                List<String> content = render.listFormattedStringToWidth(article.getContent(),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5);
                stringY += content.size() * 12;
                List<String> author = render.listFormattedStringToWidth(article.getAuthor(),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5);
                stringY += author.size() * 12;


                stringY += 12;
                render.drawRectBorder(windowWidth / 3 - 25, y, windowWidth / 3 * 2 + 25, stringY < getStartY() ? 0 : stringY, Color.GRAY.getRGB(), 1.0);
                stringY = y;
                stringY += 6.5;
                for(String s : title) {
                    if(stringY > getStartY())
                    render.drawString(s, windowWidth / 3 - 20, stringY, 1.2);
                    stringY += 12;
                }
                for(String s : author) {
                    if(stringY > getStartY())
                    render.drawString(s, windowWidth / 3 - 20, stringY);
                    stringY += 12;
                }
                if(stringY > getStartY())
                drawHorizontalLine(windowWidth / 3 - 10, windowWidth / 3 * 2 + 10, stringY, Color.GRAY.getRGB());
                stringY += 5;
                for(String s : content) {
                    if(stringY > getStartY())
                    render.drawCenteredString(s, windowWidth / 2, stringY, 0.9);
                    stringY += 12;
                }
                if(stringY > getStartY()) {
                    int sWidth = render.getStringWidth(article.getChatComponent());
                    article.setPosition(windowWidth / 2 - sWidth / 2, stringY, sWidth,
                            12);
                    article.setShown(true);
                    render.drawCenteredString(article.getChatComponent(), windowWidth / 2, stringY, 1.2);
                    stringY += 15;
                }
                else article.setShown(false);
                stringY += 8;
                y = stringY;
            }
        }
    }

    @Override
    protected void onMouseClick(int mouseX, int mouseY) {
        super.onMouseClick(mouseX, mouseY);
        activateComponent(mouseX, mouseY);
    }

    @Override
    protected void handleMouse() {
        super.handleMouse();
        final int wheel = Mouse.getEventDWheel();
        if (wheel > 0) {
            this.scrollY += 30;
        }
        else if (wheel < 0) {
            this.scrollY -= 30;
        }
        if (wheel != 0) {
            this.checkOutOfBorders();
        }
    }

    private void checkOutOfBorders() {
        if (this.scrollY > 0.0) {
            this.scrollY = 0.0;
        }
    }

    private void activateComponent(int mouseX, int mouseY) {
        if(newsArticles == null) return;
        for(Article article : newsArticles) {
            if(article.isShown() && article.isHovered(mouseX, mouseY)) {
                try {
                    Desktop.getDesktop().browse(new URI(article.getLink()));
                    break;
                } catch (Exception e) {
                    System.err.println("Couldn't open URL: ");
                    e.printStackTrace();
                }
            }

        }
    }
}
