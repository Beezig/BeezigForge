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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public abstract class SettingEntry implements GuiListExtended.IGuiListEntry {
    public static class BoolSettingEntry extends SettingEntry {
        public BoolSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, Object value) {
            super(parentScreen, name, (boolean) value ? "Disable" : "Enable", desc, value);
        }

        @Override
        protected void onButtonClick(GuiButton button) {
            this.value = !((boolean) value);
            actionButton.displayString = (boolean) value ? "Disable" : "Enable";
            actionButton.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(actionButton.displayString) + 5;
        }

        @Override
        protected String formatValue() {
            return (boolean) value ? "§aOn" : "§cOff";
        }
    }

    public static class StringSettingEntry extends SettingEntry {
        public StringSettingEntry(GuiBeezigSettings parentScreen, String name, String desc, Object value) {
            super(parentScreen, name, "Edit...", desc, value);
        }

        @Override
        protected void onButtonClick(GuiButton button) {
            parentScreen.mc.displayGuiScreen(new GuiTextInput(parentScreen, s -> {
                if(s != null) value = s;
            }, value.toString(), "Enter a value for " + name));
        }
    }

    protected final GuiBeezigSettings parentScreen;
    protected final GuiButton actionButton;
    protected final String name;
    private final IChatComponent desc;
    protected Object value;

    private SettingEntry(GuiBeezigSettings parentScreen, String name, String buttonName, String desc, Object value) {
        this.name = name;
        this.desc = new ChatComponentText("");
        this.desc.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(desc)));
        this.parentScreen = parentScreen;
        this.value = value;
        this.actionButton = new GuiButton(1, 0, 0, Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonName) + 5, 10, buttonName);
    }

    protected abstract void onButtonClick(GuiButton button);

    @Override
    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        int relX = mouseX - x;
        int relY = mouseY - y;
        int color = 0xff_ff_ff_ff;
        fr.drawString(name, x, y + 2, color);
        fr.drawString(formatValue(), x + 110, y + 2, color);
        if(parentScreen.getSettings().isSelected(slotIndex)) {
            actionButton.xPosition = x + (260 - 50);
            actionButton.yPosition = y;
            actionButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }
        if(relX < 110 && relY > 0 && relY <= fr.FONT_HEIGHT) parentScreen.handleComponentHover(desc, mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int slotIndex, int x, int y, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        if(actionButton.mousePressed(Minecraft.getMinecraft(), x, y)) {
            onButtonClick(actionButton);
            return true;
        }
        return false;
    }

    protected String formatValue() {
        return value.toString();
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

    }
}
