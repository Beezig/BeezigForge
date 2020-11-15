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
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingsList extends GuiListExtended {
    private final GuiBeezigSettings parentScreen;
    private final List<IGuiListEntry> entries = new ArrayList<>();
    private ScaledResolution scaledResolution;

    public SettingsList(GuiBeezigSettings parentScreen, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.parentScreen = parentScreen;
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int wheel = Mouse.getEventDWheel();
        if(wheel != 0) {
            // Scroll multiplier
            amountScrolled -= (Math.signum(wheel) * Math.min(3F, entries.size() * 0.1F));
        }
    }

    public void populate(Map<String, List<SettingInfo>> source) {
        for(Map.Entry<String, List<SettingInfo>> category : source.entrySet()) {
            if(!category.getKey().isEmpty()) entries.add(new Category("§b§l" + category.getKey()));
            for(SettingInfo settingInfo : category.getValue()) {
                if (settingInfo.value instanceof Boolean) {
                    entries.add(new SettingEntry.BoolSettingEntry(parentScreen, settingInfo.name, settingInfo.desc, settingInfo.key, settingInfo.value));
                } else if(settingInfo.value instanceof String) {
                    entries.add(new SettingEntry.StringSettingEntry(parentScreen, settingInfo.name, settingInfo.desc, settingInfo.key, settingInfo.value));
                } else if(settingInfo.value instanceof Number) {
                    entries.add(new SettingEntry.NumberSettingEntry(parentScreen, settingInfo.name, settingInfo.key, settingInfo.desc, settingInfo.value));
                } else {
                    EnumService.EnumData enumData = EnumService.tryParseAsEnum(settingInfo.value);
                    if(enumData != null)
                        entries.add(new SettingEntry.EnumSettingEntry(parentScreen, settingInfo.name, settingInfo.desc, settingInfo.key, enumData));
                }
            }
        }
    }

    @Override
    public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
        super.setDimensions(widthIn, heightIn, topIn, bottomIn);
        scaledResolution = new ScaledResolution(mc);
    }

    public void addEntry(IGuiListEntry entry) {
        entries.add(entry);
    }

    @Override
    public int getListWidth() {
        return 400;
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 31 * scaledResolution.getScaleFactor();
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return false;
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
            fr.drawString(display, x + 200 - fr.getStringWidth(display) / 2, y, color);
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
