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

package eu.beezig.forge.gui.briefing;

import eu.beezig.forge.gui.briefing.tabs.TabRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.List;

public class BriefingGui extends GuiScreen {

    private TabRenderer render;
    private Class labyTabs;
    private int selected = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        render.renderTabs(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        try {
            labyTabs = Class.forName("net.labymod.gui.elements.Tabs");
            labyTabs.getMethod("initGuiScreen", List.class, GuiScreen.class)
                    .invoke(null, buttonList, this);
        }
        catch(Exception ignored) {}
        this.render = new TabRenderer(width, height, buttonList);
        render.setSelected(selected);
        super.initGui();

    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        render.onKeyTyped(keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        render.onMouseClick(mouseX, mouseY);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        render.onMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        try {
            if(labyTabs != null)
                labyTabs.getMethod("actionPerformedButton", GuiButton.class)
                        .invoke(null, button);
        }
        catch (Exception ignored) {}
        render.onActionPerformed(button);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public void setSelectedTab(int selectedTab) {
        selected = selectedTab;
    }
}
