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

package eu.beezig.forge.gui.autovote;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSlot;
import eu.beezig.forge.API;

import java.util.ArrayList;
import java.util.List;

public class AutovoteSlot extends GuiSlot {

    List<String> savedMaps = new ArrayList<>();

    private FontRenderer frObj;
    private AutovoteGui parent;
    private int selected = 0;
    private int mode;

    AutovoteSlot(Minecraft mcIn, AutovoteGui parent, int mode) {
        super(mcIn, parent.width, parent.height, 32, parent.height - 65 + 4, 18);
        frObj = mc.fontRendererObj;
        this.parent = parent;
        this.mode = mode;
    }

    @Override
    protected int getSize() {
        return savedMaps.size();
    }

    @Override
    protected int getContentHeight() {
        return getSize() * 18;
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        selected = slotIndex;
        if(isDoubleClick && parent.getStep() == AutovoteGui.STEP_SELECT_MODE)
            parent.changeMaps();
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
        parent.drawCenteredString(frObj, savedMaps.get(entryID).replace("_", " "), width / 2, p_180791_3_ + 1, 16777215);
    }

    int getSelected() {
        return selected;
    }

    void setData(List<String> data) {
        if(data != null)
            savedMaps = data;
        selected = 0;
    }

    void save() {
        API.autovote.setMapsForMode(AutovoteMode.get(mode).name(), new ArrayList<>(savedMaps));
    }

    void remove() {
        savedMaps.remove(selected);
    }

    void add(String in) {
        savedMaps.add(in);
    }

    private void replace(int el1, int el2) {
        if(el2 < 0 || el2 >= savedMaps.size()) return;
        String tmp = savedMaps.get(el1);
        String tmp2 = savedMaps.get(el2);
        savedMaps.set(el1, tmp2);
        savedMaps.set(el2, tmp);
    }

    void moveUp() {
        replace(selected, selected - 1);
        selected--;
    }

    void moveDown() {
        replace(selected, selected + 1);
        selected++;
    }

}
