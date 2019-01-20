package tk.roccodev.beezig.forge.gui.briefing.recentgames;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import tk.roccodev.beezig.forge.gui.briefing.json.BeezigArticle;
import tk.roccodev.beezig.forge.gui.briefing.json.BeezigArticles;
import tk.roccodev.beezig.forge.gui.briefing.recentgames.csv.CsvMerger;
import tk.roccodev.beezig.forge.gui.briefing.recentgames.csv.GameData;
import tk.roccodev.beezig.forge.gui.briefing.tabs.Tab;
import tk.roccodev.beezig.forge.gui.briefing.tabs.TabRenderUtils;
import tk.roccodev.beezig.forge.gui.briefing.tabs.Tabs;
import tk.roccodev.beezig.forge.gui.briefing.xml.Article;
import tk.roccodev.beezig.forge.gui.briefing.xml.RssParser;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class RecentGamesTab extends Tab {

    private CsvMerger csv;
    private TabRenderUtils render = new TabRenderUtils(getStartY());
    private double scrollY;

    public RecentGamesTab() {
        super("Recent Games", new ResourceLocation("beezigforge/gui/recent.png"));
    }

    @Override
    protected void init() {
        super.init();
        new Thread(() -> csv = new CsvMerger()).start();

    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        if(csv == null)
            centered("Loading, please wait...", windowWidth / 2, 0, Color.WHITE.getRGB());
        else {
            int y = getStartY() + (int)scrollY;
            for(GameData game : csv.getRecentGames()) {
                int stringY = y;

                String color = game.getGamemode().canWin() ? (game.isWon() ? "§a" : "§c") : "§b";

                // Adapt strings to fit into the box
                List<String> title = render.listFormattedStringToWidth(color + "§l" + game.getGamemode().getGame().getCommonName(),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5 - 10);

                stringY += title.size() * 12;

                if(game.getGameId() != null) stringY += 12;


                List<String> content = new ArrayList<>();

                if(game.getValue() != null) {
                     content.addAll(render.listFormattedStringToWidth(game.getValue(),
                            windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5));
                    stringY += content.size() * 12;
                }

                String date = game.getDate() == null ? "Unknown Date" : Tabs.sdf.format(game.getDate());

                if(game.getMap() != null && !game.getMap().isEmpty()) date += "§3 on §b" + game.getMap();
                if(game.getMode() != null && !game.getMode().isEmpty()) date += " (" + game.getMode() + ")";

                List<String> author = render.listFormattedStringToWidth(date,
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
                        render.drawString("§3" + s, windowWidth / 3 - 20, stringY);
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
                    int sWidth = render.getStringWidth(game.getChatComponent());
                    game.setPosition(windowWidth / 2 - sWidth / 2, stringY, sWidth,
                            12);
                    game.setShown(true);
                    render.drawCenteredString(game.getChatComponent(), windowWidth / 2, stringY, 1.2);
                    stringY += 15;
                }
                else game.setShown(false);
                stringY += 8;
                y = stringY;
            }
        }
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

    @Override
    protected void onMouseClick(int mouseX, int mouseY) {
        super.onMouseClick(mouseX, mouseY);
        activateComponent(mouseX, mouseY);
    }

    private void activateComponent(int mouseX, int mouseY) {
        if(csv == null) return;
        for(GameData game : csv.getRecentGames()) {
            if(game.isShown() && game.isHovered(mouseX, mouseY)) {
                try {
                    Desktop.getDesktop().browse(new URI(game.getLink()));
                    break;
                } catch (Exception e) {
                    System.err.println("Couldn't open URL: ");
                    e.printStackTrace();
                }
            }

        }
    }
}
