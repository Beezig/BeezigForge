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

import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.gui.briefing.tabs.Tab;
import eu.beezig.forge.gui.briefing.tabs.TabGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.net.URI;


public class OtherTab extends Tab {


    public OtherTab() {
        super(ForgeMessage.translate("gui.news.tab.other"), new ResourceLocation("beezigforge/gui/other.png"));
    }

    @Override
    protected void init(int windowWidth, int windowHeight) {
        super.init(windowWidth, windowHeight);

        getButtonList().add(new TabGuiButton(this,42, windowWidth / 2 - 50, 108, 100, 20,
                ForgeMessage.translate("gui.news.click")));
        getButtonList().add(new TabGuiButton(this,43, windowWidth / 2 - 50, 148, 100, 20,
                ForgeMessage.translate("gui.news.click")));
        getButtonList().add(new TabGuiButton(this,44, windowWidth / 2 - 50, 188, 100, 20,
                ForgeMessage.translate("gui.news.click")));

    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        centered(ForgeMessage.translate("gui.news.tab.other.discord"), windowWidth / 2, 0, Color.CYAN.getRGB());
        centered(ForgeMessage.translate("gui.news.tab.other.website"), windowWidth / 2, 40, Color.CYAN.getRGB());
        centered(ForgeMessage.translate("gui.news.tab.other.wiki"), windowWidth / 2, 80, Color.CYAN.getRGB());

    }

    @Override
    protected void onActionPerformed(GuiButton btn) {
        super.onActionPerformed(btn);
        switch (btn.id) {
            case 42 /* Join Discord */:
                browse("https://go.beezig.eu/discord");
                break;
            case 43 /* Website */:
                browse("https://beezig.eu");
                break;
            case 44 /* Wiki */:
                browse("https://beezig.eu/wiki");
                break;
        }
    }

    private void browse(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
