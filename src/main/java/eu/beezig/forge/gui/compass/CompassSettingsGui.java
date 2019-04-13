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

package eu.beezig.forge.gui.compass;

import eu.beezig.forge.config.compass.CompassConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import eu.beezig.forge.modules.compass.CompassManager;
import eu.beezig.forge.modules.compass.render.CompassRenderListener;

import java.io.IOException;

public class CompassSettingsGui extends GuiScreen {

    private int lastX, lastY;
    private boolean dragging;

    @Override
    public void initGui() {
        Mouse.setGrabbed(false);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 - 50, 120, 20,
                "Enabled: " + (CompassManager.enabled ? "Yes" : "No")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 25, 120, 20,
                "Reset Position"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 60, this.height / 2 + 25, 120, 20,
                "Enabled in Teams: " + (CompassManager.enabled4 ? "Yes" : "No")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 60, this.height / 2 + 50, 120, 20,
                "Enabled in Duos: " + (CompassManager.enabled2 ? "Yes" : "No")));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 60, this.height / 2 + 75, 120, 20,
                "Enabled in Solo: " + (CompassManager.enabled1 ? "Yes" : "No")));
        this.buttonList.add(new GuiSlider(3, this.width / 2 - 60, this.height / 2,
                120, 20, "Dot Size: ", "",
                1d, 10d, CompassManager.size, false, true, slider -> {
            CompassManager.size = slider.getValueInt();
            CompassConfigManager.size.set(slider.getValueInt());
        }));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2 + 100, 120, 20,
                "Done"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(CompassManager.enabled) {
            CompassRenderListener.drawDummy(this.width);
        }
        if(dragging) {
            CompassRenderListener.offX += mouseX - lastX;
            CompassRenderListener.offY += mouseY - lastY;
            lastX = mouseX;
            lastY = mouseY;

        }
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                CompassManager.enabled = !CompassManager.enabled;
                CompassConfigManager.enabled.set(CompassManager.enabled);
                button.displayString = "Enabled: " + (CompassManager.enabled ? "Yes" : "No");
                break;
            case 1:
                CompassConfigManager.x.set(0);
                CompassConfigManager.y.set(0);
                CompassRenderListener.offX = 0;
                CompassRenderListener.offY = 0;
                break;
            case 2:
                onGuiClosed();
                Minecraft.getMinecraft().currentScreen = null;
                Minecraft.getMinecraft().setIngameFocus();
                break;
            case 4:
                CompassManager.enabled4 = !CompassManager.enabled4;
                CompassConfigManager.enabled4.set(CompassManager.enabled4);
                button.displayString = "Enabled in Teams: " + (CompassManager.enabled4 ? "Yes" : "No");
                break;
            case 5:
                CompassManager.enabled2 = !CompassManager.enabled2;
                CompassConfigManager.enabled2.set(CompassManager.enabled2);
                button.displayString = "Enabled in Duos: " + (CompassManager.enabled2 ? "Yes" : "No");
                break;
            case 6:
                CompassManager.enabled1 = !CompassManager.enabled1;
                CompassConfigManager.enabled4.set(CompassManager.enabled1);
                button.displayString = "Enabled in Solo: " + (CompassManager.enabled1 ? "Yes" : "No");
                break;
        }
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        CompassConfigManager.x.set(CompassRenderListener.offX);
        CompassConfigManager.y.set(CompassRenderListener.offY);
        CompassConfigManager.save();
        super.onGuiClosed();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= (this.width - 184) / 2 + CompassRenderListener.offX &&
                mouseY >= CompassRenderListener.offY && mouseX <= (this.width + 184) / 2 +
                CompassRenderListener.offX && mouseY <= CompassRenderListener.offY + 20)  {
            this.dragging = true;
            this.lastX = mouseX;
            this.lastY = mouseY;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

}
