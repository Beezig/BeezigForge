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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A GUI containing two lists, the left one representing the current state and the right one as a view of the model.
 * @param <E> the row type
 */
public class GuiDualList<E extends GuiDualList.DualListEntry> extends GuiScreen {
    private final List<E> model, left, right;
    private GuiListExtended leftView, rightView;
    private int selectedLeft, selectedRight;

    private final Runnable callback;
    private final GuiScreen parent;

    public GuiDualList(GuiScreen parent, List<E> model, List<E> current, Runnable callback) {
        this.callback = callback;
        this.parent = parent;
        this.model = Collections.unmodifiableList(model);
        this.left = current;
        this.right = model.stream().filter(e -> !this.left.contains(e)).collect(Collectors.toList());
        for(E left : this.left) {
            left.setLeft(true);
            left.setParent(this);
        }
        for(E right : this.right) {
            right.setLeft(false);
            right.setParent(this);
        }
    }

    @Override
    public void initGui() {
        int relHeight = (height - 32) / 2;
        buttonList.add(new GuiButton(1, width / 2 - 15, relHeight - 12, 30, 20, "<<"));
        buttonList.add(new GuiButton(2, width / 2 - 15, relHeight + 12, 30, 20, ">>"));
        buttonList.add(new GuiButton(3, width / 2 - 370, relHeight - 12, 60, 20, ForgeMessage.translate("gui.autovote.up")));
        buttonList.add(new GuiButton(4, width / 2 - 370, relHeight + 12, 60, 20, ForgeMessage.translate("gui.autovote.down")));
        buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 29, I18n.format("gui.done")));
        leftView = new DualList(mc, 220, 400, 32, 32 + 400, 16) {
            @Override
            public IGuiListEntry getListEntry(int index) {
                return GuiDualList.this.left.get(index);
            }

            @Override
            protected int getSize() {
                return GuiDualList.this.left.size();
            }

            @Override
            protected boolean isSelected(int slotIndex) {
                return selectedLeft == slotIndex;
            }

            @Override
            protected void drawListHeader(int x, int y, Tessellator p_148129_3_) {
                drawHeader(ForgeMessage.translate("gui.settings.list.current"), x, y, width, top);
            }
        };
        leftView.setSlotXBoundsFromLeft(width / 2 - 280);
        rightView = new DualList(mc, 220, 400, 32, 32 + 400, 16) {
            @Override
            public IGuiListEntry getListEntry(int index) {
                return GuiDualList.this.right.get(index);
            }

            @Override
            protected int getSize() {
                return GuiDualList.this.right.size();
            }

            @Override
            protected boolean isSelected(int slotIndex) {
                return selectedRight == slotIndex;
            }

            @Override
            protected void drawListHeader(int x, int y, Tessellator p_148129_3_) {
                drawHeader(ForgeMessage.translate("gui.settings.list.available"), x, y, width, top);
            }
        };
        rightView.setSlotXBoundsFromLeft(width / 2 + 50);
    }

    private void drawHeader(String text, int x, int y, int width, int top) {
        String header = EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + text;
        this.mc.fontRendererObj.drawString(header, x + width / 2 - this.mc.fontRendererObj.getStringWidth(header) / 2, Math.min(top + 3, y), 0xffffffff);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                moveLeft(selectedRight);
                break;
            case 2:
                moveRight(selectedLeft);
                break;
            case 3:
                moveUp(selectedLeft);
                break;
            case 4:
                moveDown(selectedLeft);
                break;
            case 200:
                callback.run();
                mc.displayGuiScreen(parent);
                break;
        }
    }

    public void moveLeft(int index) {
        if(index >= right.size()) return;
        E removed = right.remove(index);
        if(removed != null) {
            removed.setLeft(true);
            left.add(removed);
            selectedLeft = left.indexOf(removed);
            selectedRight = Math.max(0, selectedRight - 1);
        }
    }

    public void moveRight(int index) {
        if(index >= left.size()) return;
        E removed = left.remove(index);
        if(removed != null) {
            right.add(Math.min(Math.max(0, right.size() - 1), model.indexOf(removed)), removed);
            removed.setLeft(false);
            selectedRight = right.indexOf(removed);
            selectedLeft = Math.max(0, selectedLeft - 1);
        }
    }

    public void moveUp(int index) {
        if(index == 0) return;
        Collections.swap(left, index, index - 1);
        selectedLeft--;
    }

    public void moveDown(int index) {
        if(index < left.size() - 1) {
            Collections.swap(left, index, index + 1);
            selectedLeft++;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        leftView.handleMouseInput();
        rightView.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        leftView.drawScreen(mouseX, mouseY, partialTicks);
        rightView.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        leftView.mouseClicked(mouseX, mouseY, mouseButton);
        rightView.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        leftView.mouseReleased(mouseX, mouseY, state);
        rightView.mouseClicked(mouseX, mouseY, state);
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent evt) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public abstract static class DualListEntry implements GuiListExtended.IGuiListEntry {
        private GuiDualList<? extends DualListEntry> parent;
        private boolean left;

        public void setLeft(boolean left) {
            this.left = left;
        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}

        public void setParent(GuiDualList<? extends DualListEntry> parent) {
            this.parent = parent;
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            if(left) parent.selectedLeft = slotIndex;
            else parent.selectedRight = slotIndex;
            return true;
        }
    }

    private abstract static class DualList extends GuiListExtended {
        public DualList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
            super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
            this.setHasListHeader(true, (int)((float)mcIn.fontRendererObj.FONT_HEIGHT * 1.5F));
        }

        @Override
        protected int getScrollBarX() {
            return left + super.getScrollBarX();
        }
    }
}
