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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class EnumList extends GuiScreen {
    private final GuiBeezigSettings parentScreen;
    private final Slots slots;
    private final Consumer<EnumService.EnumEntry> callback;
    private boolean save;

    public EnumList(GuiBeezigSettings parentScreen, List<EnumService.EnumEntry> entries, int selectedId, Consumer<EnumService.EnumEntry> callback) {
        this.parentScreen = parentScreen;
        this.slots = new Slots(entries, selectedId, width, height, 67, height - 44, 16);
        this.callback = callback;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1, width / 2 + 5, height - 38, 150, 20, I18n.format("gui.done")));
        buttonList.add(new GuiButton(2, width / 2 - 155, height - 38, 150, 20, I18n.format("gui.cancel")));
        slots.setDimensions(width, height, 32, height - 61);
        slots.registerScrollButtons(7, 8);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        slots.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        slots.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, "Select one from the list below.", width / 2, 16, 0xffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        save(slots.getSelected());
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 2) {
            mc.displayGuiScreen(parentScreen);
        } else if(button.id == 1) {
            save = true;
            mc.displayGuiScreen(parentScreen);
        }
    }

    private void save(EnumService.EnumEntry entry) {
        callback.accept(save ? entry : null);
    }

    private class Slots extends GuiSlot {
        private final List<EnumService.EnumEntry> entries;
        private int selectedId;

        public Slots(List<EnumService.EnumEntry> entries, int selectedId, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
            super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
            this.selectedId = selectedId;
            this.entries = entries;
        }

        @Override
        protected boolean isSelected(int slotIndex) {
            return selectedId == slotIndex;
        }

        public EnumService.EnumEntry getSelected() {
            return entries.get(selectedId);
        }

        @Override
        protected void drawBackground() {
            EnumList.this.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
            EnumService.EnumEntry entry = entries.get(entryID);
            EnumList.this.drawCenteredString(EnumList.this.fontRendererObj, entry.getDisplay(), this.width / 2, p_180791_3_ + 1, 0xffffff);
        }

        @Override
        protected int getSize() {
            return entries.size();
        }

        @Override
        protected int getContentHeight() {
            return getSize() * 16;
        }

        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
            selectedId = slotIndex;
            if(isDoubleClick) {
                save = true;
                mc.displayGuiScreen(parentScreen);
            }
        }
    }
}
