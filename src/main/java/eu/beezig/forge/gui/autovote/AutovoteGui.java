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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import eu.beezig.forge.API;

import java.io.IOException;

public class AutovoteGui extends GuiScreen {

    public static final int STEP_SELECT_MODE = 0;
    public static final int STEP_CHANGE_MAPS = 1;

    private GuiButton btnNext;
    private GuiButton btnBack;
    private AutovoteSlot list;
    private int step;
    private int opt = -1;

    public AutovoteGui(int step, int... opts) {
        this.step = step;
        if(opts.length > 0)
            this.opt = opts[0];
    }

    @Override
    public void initGui() {
        super.initGui();

        this.list = new AutovoteSlot(Minecraft.getMinecraft(), this, opt);
        this.list.registerScrollButtons(7, 8);

        if(step == STEP_CHANGE_MAPS) {
            buttonList.add(btnBack = new GuiButton(1, this.width / 2 - 155 - 50, this.height - 38, 60, 20, "Back"));
            buttonList.add(new GuiButton(2, this.width / 2 - 155 + 20, this.height - 38, 60, 20, "Add..."));
            buttonList.add(new GuiButton(3, this.width / 2 - 155 + 90, this.height - 38, 60, 20, "Remove"));
            buttonList.add(new GuiButton(4, this.width / 2 - 155 + 160, this.height - 38, 60, 20, "Move Up"));
            buttonList.add(new GuiButton(5, this.width / 2 - 155 + 230, this.height - 38, 60, 20, "Move Down"));
            buttonList.add(btnNext = new GuiButton(0, this.width / 2 - 155 + 300, this.height - 38, 60, 20, "Save"));
            list.setData(API.autovote.getMapsForMode(AutovoteMode.get(opt).name()));
        }
        else {
            buttonList.add(btnBack = new GuiButton(1, this.width / 2 - 155, this.height - 38, 150, 20, "Cancel"));
            buttonList.add(btnNext = new GuiButton(0, this.width / 2 - 155 + 160, this.height - 38, 150, 20, "Next"));
            list.setData(AutovoteMode.getDisplays());
        }

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
            drawCenteredString(fontRendererObj, step == STEP_SELECT_MODE
                    ? "Beezig Autovoting - Select game mode"
                    : "Beezig Autovoting - Add, remove or move maps.", this.width / 2, 16, 16777215);


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
                if(step == STEP_CHANGE_MAPS)
                    Minecraft.getMinecraft().displayGuiScreen(new AutovoteGui(STEP_SELECT_MODE));
                else
                    Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 0 /* Next */:
                if(step == STEP_CHANGE_MAPS) {
                    list.save();
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
                else
                   changeMaps();
                break;
            case 2 /* Add map */:
                list.save();
                Minecraft.getMinecraft().displayGuiScreen(new AutovoteMapInput(this));
                break;
            case 3 /* Remove selected map */:
                list.remove();
                break;
            case 4 /* Move selected map up */:
                list.moveUp();
                break;
            case 5 /* Move selected map down */:
                list.moveDown();
                break;
        }
    }

    AutovoteSlot getList() {
        return list;
    }

    int getStep() {
        return step;
    }

    void changeMaps() {
        Minecraft.getMinecraft().displayGuiScreen(new AutovoteGui(STEP_CHANGE_MAPS, list.getSelected()));
    }
}
