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

package eu.beezig.forge.gui.briefing.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TabRenderer extends Gui {

    private static int colorDefault = new Color(56, 143, 145).darker().getRGB();
    private static int colorSelected = new Color(66, 210, 214).brighter().getRGB();

    private int width, height;
    private int selected;
    private TabRenderUtils render;

    public TabRenderer(int width, int height, java.util.List<GuiButton> btnList) {
        this.width = width;
        this.height = height;
        this.render = TabRenderUtils.getInstance();

        for(Tab tab : Tabs.tabs) {
            tab.init(this, width, height, btnList);
        }

    }
    
    
    public void renderTabs(int mouseX, int mouseY) {

        int offsetIcon = -143;
        int offsetLine = -155;
        int offsetLineEnd = -104;

        for(int i = 0; i < Tabs.tabs.length; i++) {
            Tab tab = Tabs.tabs[i];
            this.drawHorizontalLine(this.getWidth() / 2 + offsetLine, this.getWidth() / 2 + offsetLineEnd, 70,
                    selected == i
                    ? colorSelected
                    : colorDefault);
            Minecraft.getMinecraft().getTextureManager().bindTexture(tab.getIcon());
            this.render.drawTexture(this.getWidth() / 2 + offsetIcon, 38.0, 256.0, 256.0, 25.0, 25.0);

            tab.setPosition(width / 2 + offsetIcon, 38, 25, 25);
            tab.setWindow(width);

            offsetIcon += 50;
            offsetLine += 50;
            offsetLineEnd += 50;
        }

        this.drawTitle(Tabs.tabs[selected].getTitle());
        Tabs.tabs[selected].drawTab(mouseX, mouseY);
    }

    private void drawTitle(String title) {
        TabRenderUtils.getInstance().drawCenteredString(title, width / 2, 80.0, 1.45);
    }

    private void switchSelected(int adding) {
        int newIndex = selected + adding;
        if(newIndex >= Tabs.tabs.length) newIndex = 0;
        if(newIndex < 0) newIndex = Tabs.tabs.length - 1;
        selected = newIndex;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void onKeyTyped(int keyCode) {
        if(keyCode == Keyboard.KEY_RIGHT)
            switchSelected(1);
        else if(keyCode == Keyboard.KEY_LEFT)
            switchSelected(-1);
    }

    public void onMouseClick(int mouseX, int mouseY) {
        Tabs.tabs[selected].onMouseClick(mouseX, mouseY);

        Tab hovering = Tabs.getTabByBox(mouseX, mouseY);
        if(hovering != null) {
            selected = hovering.getIndex();
        }
    }

    public void onActionPerformed(GuiButton btn) {
        Tabs.tabs[selected].onActionPerformed(btn);
    }

    public void onMouseInput() {
        Tabs.tabs[selected].handleMouse();
    }

    private int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSelected() { return selected; }
}
