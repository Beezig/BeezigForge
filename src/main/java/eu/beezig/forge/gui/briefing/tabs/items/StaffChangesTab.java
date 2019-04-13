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

import eu.beezig.forge.gui.briefing.json.lergin.StaffChange;
import eu.beezig.forge.gui.briefing.tabs.ImageDownloader;
import eu.beezig.forge.gui.briefing.tabs.Tab;
import eu.beezig.forge.gui.briefing.tabs.TabRenderUtils;
import eu.beezig.forge.gui.briefing.tabs.Tabs;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import eu.beezig.forge.gui.briefing.json.lergin.LerginFetcher;

import java.awt.*;
import java.util.List;


public class StaffChangesTab extends Tab {

    private List<StaffChange> staffChanges = null;
    private TabRenderUtils render = new TabRenderUtils(getStartY());
    private double scrollY;

    public StaffChangesTab() {
        super("Staff Changes", new ResourceLocation("beezigforge/gui/staff.png"));
    }

    @Override
    protected void init(int windowWidth, int windowHeight) {
        super.init(windowWidth, windowHeight);
        new Thread(() -> staffChanges = LerginFetcher.getStaffChanges()).start();

    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        if(staffChanges == null)
            centered("Loading, please wait...", windowWidth / 2, 0, Color.WHITE.getRGB());
        else {
            int y = getStartY() + (int)scrollY;
            for(StaffChange sc : staffChanges) {
                int stringY = y;
                // Adapt strings to fit into the box
                List<String> title = render.listFormattedStringToWidth(sc.getType().getColor() + "§l" +
                                sc.getType().getPrefix() + " " +
                                sc.getName(),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5 - 10);
                for(String s : title) {
                    stringY += 12;
                }
                List<String> content = render.listFormattedStringToWidth(Tabs.sdf.format(sc.getDate()),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5);
                stringY += content.size() * 12;
                List<String> author = render.listFormattedStringToWidth(sc.getType().getDisplay(),
                        windowWidth / 3 * 2 - 5 - windowWidth / 3 + 5);
                stringY += author.size() * 12;


                stringY += 12;
                render.drawRectBorder(windowWidth / 3 - 25, y, windowWidth / 3 * 2 + 25, stringY < getStartY() ? 0 : stringY, Color.GRAY.getRGB(), 1.0);
                final String url = String.format("https://minotar.net/helm/%s/16.png", sc.getUuid());
                ImageDownloader.instance.drawImageUrl(url, sc.getUuid(), windowWidth / 3.0 - 15.0, y + 7.0, 256.0, 256.0, 20.0, 20.0, getStartY());
                stringY = y;
                stringY += 6.5;
                for(String s : title) {
                    if(stringY > getStartY())
                    render.drawString(s, windowWidth / 3 + 15, stringY, 1.2);
                    stringY += 12;
                }
                for(String s : author) {
                    if(stringY > getStartY())
                    render.drawString("§3" + s, windowWidth / 3 + 15, stringY);
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
}
