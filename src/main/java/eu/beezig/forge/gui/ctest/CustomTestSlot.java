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

package eu.beezig.forge.gui.ctest;

import eu.beezig.forge.API;
import eu.beezig.forge.gui.autovote.AutovoteMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSlot;

import java.util.ArrayList;
import java.util.List;

public class CustomTestSlot extends GuiSlot {

    List<String> savedLines = new ArrayList<>();

    private FontRenderer frObj;
    private CustomTestGui parent;
    private int selected = 0;

    CustomTestSlot(Minecraft mcIn, CustomTestGui parent) {
        super(mcIn, parent.width, parent.height, 32, parent.height - 65 + 4, 18);
        frObj = mc.fontRendererObj;
        this.parent = parent;
    }

    @Override
    protected int getSize() {
        return savedLines.size();
    }

    @Override
    protected int getContentHeight() {
        return getSize() * 18;
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        selected = slotIndex;
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return selected == slotIndex;
    }

    @Override
    protected void drawBackground() {
        parent.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        parent.drawCenteredString(frObj, savedLines.get(entryID), width / 2, p_180791_3_ + 1, 16777215);
    }

    int getSelected() {
        return selected;
    }

    void setData(List<String> data) {
        savedLines = data;
        selected = 0;
    }

    void save() {
        API.inst.setTIMVMessages(savedLines);
    }

    void remove() {
        savedLines.remove(selected);
    }

    void add(String in) {
        savedLines.add(in);
    }
}
