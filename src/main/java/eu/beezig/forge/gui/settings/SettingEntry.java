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

import eu.beezig.forge.ForgeMessage;
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
import java.util.regex.Pattern;

public abstract class SettingEntry implements GuiListExtended.IGuiListEntry {
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
        this.hoverAction.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("§7" + hoverAction)));
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
        String value = formatValue();
        fr.drawString(name, x, y + 2, 0xD1D1D1);
        if(relX > 300 && relX <= 300 + Math.max(50, fr.getStringWidth(value)) && relY > 0 && relY <= fr.FONT_HEIGHT) {
            fr.drawStringWithShadow(value, x + 300, y + 2, 0xe3bd14);
            parentScreen.handleComponentHover(hoverAction, mouseX, mouseY);
        }
        else fr.drawStringWithShadow(value, x + 300, y + 2, valueColor());
        if(relX < Math.max(200, fr.getStringWidth(name)) && relY > 0 && relY <= fr.FONT_HEIGHT) parentScreen.handleComponentHover(desc, mouseX, mouseY);
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

    public static class BoolSettingEntry extends SettingEntry {
        public BoolSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, String key, Object value) {
            super(parentScreen, name, key, ForgeMessage.translate("gui.settings.action.change"), desc, value);
        }

        @Override
        protected void onButtonClick() {
            this.value = !((boolean) value);
            BeezigAPI.setSettingAsIs(this.key, value);
        }

        @Override
        protected String formatValue() {
            return ForgeMessage.translate("msg." + ((boolean) value ? "on" : "off"));
        }

        @Override
        protected int valueColor() {
            return (boolean) value ? 0x55ff55 : 0xff5555;
        }
    }

    public static class StringSettingEntry extends SettingEntry {
        public StringSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, String key, Object value) {
            super(parentScreen, name, key, ForgeMessage.translate("gui.settings.action.edit"), desc, value);
        }

        @Override
        protected void onButtonClick() {
            parentScreen.mc.displayGuiScreen(new GuiTextInput(parentScreen, s -> {
                if(s != null) {
                    value = s;
                    BeezigAPI.setSettingAsIs(this.key, s);
                }
            }, value.toString(), ForgeMessage.translate("gui.settings.edit", "§b" + name)));
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
            super(parentScreen, name, key, ForgeMessage.translate("gui.settings.action.pick"), desc, value);
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

        @Override
        protected int valueColor() {
            return 0xcc54c8;
        }
    }

    public static class NumberSettingEntry extends SettingEntry {
        private static final Pattern INTEGER_REGEX = Pattern.compile("^\\d+$");
        private static final Pattern DECIMAL_REGEX = Pattern.compile("^\\d+\\.?\\d*$");

        public NumberSettingEntry(GuiBeezigSettings parentScreen, String name, String key, String desc, Object value) {
            super(parentScreen, name, key, ForgeMessage.translate("gui.settings.action.edit"), desc, value);
        }

        @Override
        protected void onButtonClick() {
            Pattern toUse = value instanceof Float || value instanceof Double ? DECIMAL_REGEX : INTEGER_REGEX;
            GuiTextInput input = new GuiTextInput(parentScreen, v -> {
                if(v == null) return;
                try {
                    if (value instanceof Float) value = Float.parseFloat(v);
                    else if (value instanceof Double) value = Double.parseDouble(v);
                    else if (value instanceof Integer) value = Integer.parseInt(v, 10);
                    else if (value instanceof Short) value = Short.parseShort(v, 10);
                    else if (value instanceof Byte) value = Byte.parseByte(v, 10);
                    else value = value.getClass().cast(Long.parseLong(v, 10));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    return;
                }
                BeezigAPI.setSettingAsIs(this.key, value);
            }, value.toString(), ForgeMessage.translate("gui.settings.number.hint", "§b" + this.name));
            input.setValidator(toUse);
            input.setValidatorError(ForgeMessage.translate("gui.settings.number.error"));
            parentScreen.mc.displayGuiScreen(input);
        }

        @Override
        protected String formatValue() {
            return BeezigAPI.formatNumber(((Number)value).longValue());
        }

        @Override
        protected int valueColor() {
            return 0x55ffff;
        }
    }
}
