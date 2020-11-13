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

package eu.beezig.forge.commands;

import com.google.common.collect.Lists;
import eu.beezig.forge.gui.settings.GuiDualList;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeezigForgeTestCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "bftest";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bftest";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        List<TestSlot> model = Lists.newArrayList(new TestSlot("Ab"), new TestSlot("Cd"), new TestSlot("Ef"));
        List<TestSlot> left = new ArrayList<>(model.subList(0, 1));
        new GuiDualList<>(null, model, left, () -> System.out.println(left.size())).show();
    }

    private static class TestSlot extends GuiDualList.DualListEntry {
        private final String text;

        public TestSlot(String text) {
            this.text = text;
        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {

        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, 0xffffffff);
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return super.mousePressed(slotIndex, p_148278_2_, p_148278_3_, p_148278_4_, p_148278_5_, p_148278_6_);
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestSlot testSlot = (TestSlot) o;
            return Objects.equals(text, testSlot.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }
    }
}
