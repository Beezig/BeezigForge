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

import eu.beezig.forge.ForgeMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AutovoteGui extends GuiScreen {

    public static final int STEP_SELECT_MODE = 0;
    public static final int STEP_CHANGE_MAPS = 1;

    private GuiButton btnNext;
    private GuiButton btnBack;
    private AutovoteSlot list;
    private int step;
    private int opt = -1;
    private Map<String, List<String>> mapData;

    public AutovoteGui(AutovoteGui parent, int step, int... opts) {
        this.step = step;
        this.mapData = parent.mapData;
        if (opts.length > 0)
            this.opt = opts[0];
    }

    public AutovoteGui(int step) {
        this.step = step;
    }

    public void setMapData(Map<String, List<String>> mapData) {
        this.mapData = mapData;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.list = new AutovoteSlot(Minecraft.getMinecraft(), this, opt);
        this.list.registerScrollButtons(7, 8);

        if (step == STEP_CHANGE_MAPS) {
            buttonList.add(btnBack = new GuiButton(1, this.width / 2 - 155, this.height - 28, 150, 20, ForgeMessage.translate("gui.back")));
            buttonList.add(new GuiButton(2, this.width / 2 - 155 + 20, this.height - 53, 60, 20, ForgeMessage.translate("gui.autovote.add")));
            buttonList.add(new GuiButton(3, this.width / 2 - 155 + 90, this.height - 53, 60, 20, ForgeMessage.translate("gui.remove")));
            buttonList.add(new GuiButton(4, this.width / 2 - 155 + 160, this.height - 53, 60, 20, ForgeMessage.translate("gui.autovote.up")));
            buttonList.add(new GuiButton(5, this.width / 2 - 155 + 230, this.height - 53, 60, 20, ForgeMessage.translate("gui.autovote.down")));
            buttonList.add(btnNext = new GuiButton(0, this.width / 2 - 155 + 160, this.height - 28, 150, 20, ForgeMessage.translate("gui.save")));
            list.setData(mapData.get(AutovoteMode.get(opt).name().toLowerCase(Locale.ROOT)));
        } else {
            buttonList.add(btnBack = new GuiButton(1, this.width / 2 - 155, this.height - 38, 150, 20, I18n.format("gui.cancel")));
            buttonList.add(btnNext = new GuiButton(0, this.width / 2 - 155 + 160, this.height - 38, 150, 20, ForgeMessage.translate("gui.next")));
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
        drawCenteredString(fontRendererObj, ForgeMessage.translate("gui.autovote") + " - " + ForgeMessage.translate(
                step == STEP_SELECT_MODE ? "gui.autovote.select" :
                        ForgeMessage.translate(opt == AutovoteMode.SHU.ordinal() ? "gui.autovote.maps.shu" : "gui.autovote.maps") + " " +
                                ForgeMessage.translate("gui.autovote.maps.priority")), this.width / 2, 16, 16777215);
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
        switch (button.id) {
            case 1 /* Back */:
                if (step == STEP_CHANGE_MAPS)
                    Minecraft.getMinecraft().displayGuiScreen(new AutovoteGui(this, STEP_SELECT_MODE));
                else
                    Minecraft.getMinecraft().displayGuiScreen(null);
                break;
            case 0 /* Next */:
                if (step == STEP_CHANGE_MAPS) {
                    list.save();
                    Minecraft.getMinecraft().displayGuiScreen(null);
                } else
                    changeMaps();
                break;
            case 2 /* Add map */:
                list.save();
                Minecraft.getMinecraft().displayGuiScreen(new AutovoteMapInput(this, AutovoteMode.get(opt)));
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
        Minecraft.getMinecraft().displayGuiScreen(new AutovoteGui(this, STEP_CHANGE_MAPS, list.getSelected()));
    }
}
