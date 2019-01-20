package tk.roccodev.beezig.forge.gui.briefing.recentgames;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import tk.roccodev.beezig.forge.gui.briefing.json.BeezigArticle;
import tk.roccodev.beezig.forge.gui.briefing.json.BeezigArticles;
import tk.roccodev.beezig.forge.gui.briefing.recentgames.csv.CsvMerger;
import tk.roccodev.beezig.forge.gui.briefing.recentgames.csv.GameData;
import tk.roccodev.beezig.forge.gui.briefing.recentgames.csv.LoggingGame;
import tk.roccodev.beezig.forge.gui.briefing.tabs.Tab;
import tk.roccodev.beezig.forge.gui.briefing.tabs.TabGuiButton;
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
    private int gamesLimit = 100;
    private LoggingGame gamemodeFilter;

    public RecentGamesTab() {
        super("Recent Games", new ResourceLocation("beezigforge/gui/recent.png"));
    }

    @Override
    protected void init(int windowWidth, int windowHeight) {
        super.init(windowWidth, windowHeight);
        new Thread(() -> csv = new CsvMerger(windowWidth)).start();
        getButtonList().add(new TabGuiButton(this,40, windowWidth / 2 + 200, getStartY() + 10,
                100, 20, "Show: 100 games"));
        getButtonList().add(new TabGuiButton(this,41, windowWidth / 2 + 200, getStartY() + 40,
                100, 20, "Filter: All"));

    }

    @Override
    protected void onActionPerformed(GuiButton btn) {
        if(csv == null || csv.getRecentGames() == null) return;
        switch(btn.id) {
            case 40 /* Game limit */:
                if(gamesLimit == 400) {
                    gamesLimit = csv.getRecentGames().size();
                    btn.displayString = "Show: All games";
                }
                else if(gamesLimit == csv.getRecentGames().size()) {
                    gamesLimit = 100;
                    btn.displayString = "Show: 100 games";
                }
                else {
                    gamesLimit += 100;
                    btn.displayString = "Show: " + gamesLimit + " games";
                }
                break;
            case 41 /* Filter */:
                if(gamemodeFilter == null) {
                    gamemodeFilter = LoggingGame.values()[0];
                    btn.displayString = "Filter: " + gamemodeFilter.name().toUpperCase();
                }
                else {
                    if(gamemodeFilter.ordinal() + 1 >= LoggingGame.values().length) {
                        gamemodeFilter = null;
                        btn.displayString = "Filter: All";
                    }
                    else {
                        gamemodeFilter = LoggingGame.values()[gamemodeFilter.ordinal() + 1];
                        btn.displayString = "Filter: " + gamemodeFilter.name().toUpperCase();
                    }
                }
                break;
        }
    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        if(csv == null)
            centered("Loading, please wait...", windowWidth / 2, 0, Color.WHITE.getRGB());
        else {
            int y = getStartY() + (int)scrollY;
            for(GameData game : csv.getRecentGames().subList(0, gamesLimit)) {
                if(gamemodeFilter != null && game.getGamemode() != gamemodeFilter) continue;
                int stringY = y;

                // Adapt strings to fit into the box
                String[] title = game.getTitle();
                stringY += title.length * 12;

                if(game.getGameId() != null) stringY += 12;

                stringY += game.getContent().length * 12;

                String[] author = game.getBelow();
                stringY += author.length * 12;

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
                for(String s : game.getContent()) {
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
