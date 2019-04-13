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
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Tab extends Gui {

    private static int id;

    private String title;
    private int index;
    private ResourceLocation icon;
    protected int x, y, width, height; /* All related to icons */
    protected int windowWidth;
    protected List<GuiButton> buttonList = new ArrayList<>();
    protected TabRenderer renderer;

    protected Tab(String title, ResourceLocation icon) {
        this.title = title;
        this.icon = icon;
        this.index = id++;
    }

    String getTitle() {
        return title;
    }

    ResourceLocation getIcon() {
        return icon;
    }

    void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void init(int windowWidth, int windowHeight) {}

    void init(TabRenderer renderer, int windowWidth, int windowHeight, List<GuiButton> btnList) {
        this.renderer = renderer;
        buttonList = btnList;
        init(windowWidth, windowHeight);
    }

    void setWindow(int width) {
        this.windowWidth = width;
    }

    boolean isHovered(int mouseX, int mouseY) {
       return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    int getIndex() {
        return index;
    }

    protected void handleMouse() {}

    protected void onMouseClick(int mouseX, int mouseY) {}

    protected void drawTab(int mouseX, int mouseY) {}

    protected void string(String text, int x, int y, int color) {
        drawString(Minecraft.getMinecraft().fontRendererObj, text, x, getStartY() + y, color);
    }

    protected void centered(String text, int x, int y, int color) {
        string(text, x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
    }

    protected int getCenterX() {
        return windowWidth / 2;
    }

    protected int getStartY() {
        return y + 60;
    }

    protected List<GuiButton> getButtonList() {
        return buttonList;
    }

    public TabRenderer getRenderer() {
        return renderer;
    }

    protected void onActionPerformed(GuiButton btn) {}
}
