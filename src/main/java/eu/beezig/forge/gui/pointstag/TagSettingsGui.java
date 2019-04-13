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

package eu.beezig.forge.gui.pointstag;

import eu.beezig.forge.config.pointstag.TagConfigManager;
import eu.beezig.forge.modules.pointstag.PointsTagCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class TagSettingsGui extends GuiScreen {

    private Minecraft mc;

    public TagSettingsGui() {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        Mouse.setGrabbed(false);
        this.buttonList.add(new GuiButton(3011, this.width / 2 - 155, this.height / 2 - 100 - 34, 150, 20,
                "Points Tag: " + (PointsTagCache.enabled ? "Enabled" : "Disabled")));

        this.buttonList.add(new GuiButton(2000, this.width / 2 + 5, this.height / 2 - 100 - 34, 150, 20,
                "Edit formatting..."));

        this.buttonList.add(new GuiButton(1603, this.width / 2 - 155, this.height / 2 -  83 + 22, 150, 20,
                "Show self: " +  (PointsTagCache.self ? "Enabled" : "Disabled")));

        this.buttonList.add(new GuiSlider(707, this.width / 2 + 5, this.height / 2 - 83 + 22,
                150, 20, "Tag Offset: ", "",
                -10d, 10d, PointsTagCache.offset, false, true, slider -> {
                PointsTagCache.offset = slider.getValueInt() / 10d;
                TagConfigManager.offset.set(slider.getValueInt() / 10d);
        }));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 3011:
                PointsTagCache.enabled = !PointsTagCache.enabled;
                TagConfigManager.enabled.set(PointsTagCache.enabled);
                button.displayString = "Points Tag: " + (PointsTagCache.enabled ? "Enabled" : "Disabled");
                break;
            case 1603:
                PointsTagCache.self = !PointsTagCache.self;
                TagConfigManager.showSelf.set(PointsTagCache.self);
                button.displayString = "Show self: " +  (PointsTagCache.self ? "Enabled" : "Disabled");
                break;
            case 2000:
                mc.displayGuiScreen(new TagFormattingGui());
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        TagConfigManager.save();
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        mc.displayGuiScreen(this);
    }
}
