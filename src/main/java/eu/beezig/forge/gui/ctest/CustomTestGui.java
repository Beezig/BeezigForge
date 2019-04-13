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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

public class CustomTestGui extends GuiScreen {


    private GuiButton btnNext;
    private GuiButton btnBack;
    private CustomTestSlot list;


    @Override
    public void initGui() {
        super.initGui();

        this.list = new CustomTestSlot(Minecraft.getMinecraft(), this);
        this.list.registerScrollButtons(7, 8);

        buttonList.add(btnBack = new GuiButton(1, this.width / 2 - 155 - 50, this.height - 38, 60, 20, "Back"));
        buttonList.add(new GuiButton(2, this.width / 2 - 70, this.height - 38, 60, 20, "Add..."));
        buttonList.add(new GuiButton(3, this.width / 2 + 10, this.height - 38, 60, 20, "Remove"));
        buttonList.add(btnNext = new GuiButton(0, this.width / 2 - 155 + 300, this.height - 38, 60, 20, "Save"));
        list.setData(API.inst.getTIMVMessages());

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, "Trouble in Mineville - Custom test messages", this.width / 2, 16, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch(button.id) {
            case 1 /* Back */:
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 0 /* Next */:
                list.save();
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 2 /* Add map */:
                list.save();
                Minecraft.getMinecraft().displayGuiScreen(new CustomTestInput(this));
                break;
            case 3 /* Remove selected map */:
                list.remove();
                break;
        }
    }

    CustomTestSlot getList() {
        return list;
    }

}
