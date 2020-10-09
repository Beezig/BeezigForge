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

import eu.beezig.forge.api.BeezigAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public abstract class SettingEntry implements GuiListExtended.IGuiListEntry {
    public static class BoolSettingEntry extends SettingEntry {
        public BoolSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, String key, Object value) {
            super(parentScreen, name, key, "Change", desc, value);
        }

        @Override
        protected void onButtonClick() {
            this.value = !((boolean) value);
            BeezigAPI.setSettingAsIs(this.key, value);
        }

        @Override
        protected String formatValue() {
            return (boolean) value ? "On" : "Off";
        }

        @Override
        protected int valueColor() {
            return (boolean) value ? 0x55ff55 : 0xff5555;
        }
    }

    public static class StringSettingEntry extends SettingEntry {
        public StringSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, String key, Object value) {
            super(parentScreen, name, key, "Edit...", desc, value);
        }

        @Override
        protected void onButtonClick() {
            parentScreen.mc.displayGuiScreen(new GuiTextInput(parentScreen, s -> {
                if(s != null) {
                    value = s;
                    BeezigAPI.setSettingAsIs(this.key, s);
                }
            }, value.toString(), "Enter a value for " + name));
        }

        @Override
        protected String formatValue() {
            return StringUtils.abbreviate(value.toString(), 30);
        }

        @Override
        protected int valueColor() {
            return 0xf5ebc1;
        }
    }

    public static class EnumSettingEntry extends SettingEntry {
        public EnumSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, String key, Object value) {
            super(parentScreen, name, key, "Pick...", desc, value);
        }

        @Override
        protected void onButtonClick() {
            EnumService.EnumData value = (EnumService.EnumData) this.value;
            List<EnumService.EnumEntry> values = Arrays.asList(value.getPossibleValues());
            // Optimization - if there are only 2 possible values just switch back and forth
            if(values.size() == 2) {
                EnumService.EnumEntry entry = values.stream().filter(v -> !v.equals(value.getValue())).findAny().orElseThrow(() -> new IllegalStateException("Value wasn't among the possible values."));
                value.setValue(entry);
                BeezigAPI.setSettingAsIs(this.key, value.getRealValue());
            } else {
                parentScreen.mc.displayGuiScreen(new EnumList(parentScreen, values, values.indexOf(value.getValue()), entry -> {
                    if (entry != null) {
                        value.setValue(entry);
                        BeezigAPI.setSettingAsIs(this.key, value.getRealValue());
                    }
                }));
            }
        }

        @Override
        protected String formatValue() {
            return ((EnumService.EnumData) value).getValue().getDisplay();
        }
    }

    protected final GuiBeezigSettings parentScreen;
    protected final String name, key;
    private final IChatComponent desc;
    protected final IChatComponent hoverAction;
    protected Object value;

    private SettingEntry(GuiBeezigSettings parentScreen, String name, String key, String hoverAction, String desc, Object value) {
        this.name = name;
        this.desc = new ChatComponentText("");
        this.desc.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(desc)));
        this.parentScreen = parentScreen;
        this.hoverAction = new ChatComponentText("");
        this.hoverAction.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(hoverAction)));
        this.value = value;
        this.key = key;
    }

    protected abstract void onButtonClick();

    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        int relX = mouseX - x;
        int relY = mouseY - y;
        int color = 0xff_ff_ff_ff;
        fr.drawString(name, x, y + 2, color);
        if(relX > 300 && relY > 0 && relY <= fr.FONT_HEIGHT) {
            fr.drawStringWithShadow(formatValue(), x + 300, y + 2, 0xe3bd14);
            parentScreen.handleComponentHover(hoverAction, mouseX, mouseY);
        }
        else fr.drawStringWithShadow(formatValue(), x + 300, y + 2, valueColor());
        if(relX < 300 && relY > 0 && relY <= fr.FONT_HEIGHT) parentScreen.handleComponentHover(desc, mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int slotIndex, int x, int y, int p4, int relX, int relY) {
        if(relX > 300 && relY > 0 && relY <= Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) {
            onButtonClick();
            return true;
        }
        return false;
    }

    protected String formatValue() {
        return value.toString();
    }

    protected int valueColor() {
        return 0xffffff;
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

    }
}
