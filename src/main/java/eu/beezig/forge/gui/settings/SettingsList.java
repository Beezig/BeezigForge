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

package eu.beezig.forge.gui.settings;

import eu.beezig.core.api.SettingInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingsList extends GuiListExtended {
    private final GuiBeezigSettings parentScreen;
    private final List<IGuiListEntry> entries = new ArrayList<>();
    private int selectedId = 0;

    public SettingsList(GuiBeezigSettings parentScreen, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.parentScreen = parentScreen;
    }

    public void populate(Map<String, List<SettingInfo>> source) {
        for(Map.Entry<String, List<SettingInfo>> category : source.entrySet()) {
            entries.add(new Category("§l" + category.getKey()));
            for(SettingInfo settingInfo : category.getValue()) {
                if (settingInfo.value instanceof Boolean) {
                    entries.add(new SettingEntry.BoolSettingEntry(parentScreen, settingInfo.name, settingInfo.desc, settingInfo.value));
                } else if(settingInfo.value instanceof String) {
                    entries.add(new SettingEntry.StringSettingEntry(parentScreen, settingInfo.name, settingInfo.desc, settingInfo.value));
                } else {
                    EnumService.EnumData enumData = EnumService.tryParseAsEnum(settingInfo.value);
                    if(enumData != null)
                        entries.add(new SettingEntry.EnumSettingEntry(parentScreen, settingInfo.name, settingInfo.desc, enumData));
                }
            }
        }
    }

    @Override
    public int getListWidth() {
        return 260;
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 30;
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        if(entries.get(selectedId) instanceof Category) return false;
        return selectedId == slotIndex;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        super.elementClicked(slotIndex, isDoubleClick, mouseX, mouseY);
        this.selectedId = slotIndex;
    }

    @Override
    protected void drawContainerBackground(Tessellator tessellator) {}

    @Override
    public IGuiListEntry getListEntry(int index) {
        return entries.get(index);
    }

    @Override
    protected int getSize() {
        return entries.size();
    }

    private static class Category implements IGuiListEntry {
        private final String display;

        public Category(String display) {
            this.display = display;
        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {

        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            int color = 0xff_ff_ff_ff;
            fr.drawString(display, x + 130 - fr.getStringWidth(display) / 2, y, color);
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

        }
    }
}
