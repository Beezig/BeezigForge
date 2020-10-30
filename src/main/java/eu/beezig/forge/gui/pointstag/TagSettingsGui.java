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

import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.config.pointstag.TagConfigManager;
import eu.beezig.forge.modules.pointstag.PointsTagCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraftforge.common.MinecraftForge;
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
        this.buttonList.add(new GuiButton(3011, this.width / 2 - 155, this.height / 2 - 114, 150, 20,
                ForgeMessage.translateOnOff("gui.ptags.tags", PointsTagCache.enabled)));

        this.buttonList.add(new GuiButton(2000, this.width / 2 + 5, this.height / 2 - 114, 150, 20,
                ForgeMessage.translate("gui.ptags.formatting")));

        this.buttonList.add(new GuiButton(1603, this.width / 2 - 155, this.height / 2 -  63, 150, 20,
                ForgeMessage.translateOnOff("gui.ptags.self", PointsTagCache.self)));

        this.buttonList.add(new GuiButton(2020, this.width / 2 - 75, this.height / 2 - 88, 150, 20,
                ForgeMessage.translateOnOff("gui.ptags.tokens", PointsTagCache.showTokens)));

        this.buttonList.add(new GuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
            }

            @Override
            public void onTick(int id, float value) {
                PointsTagCache.offset = value / 10f;
                TagConfigManager.offset.setValue(value / 10f);
            }

            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {

            }
        }, 707, this.width / 2 + 5, this.height / 2 - 63,
                "Tag Offset: ", -10, 10, (float) (PointsTagCache.offset * 10f), new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return ForgeMessage.translate("gui.ptags.offset", value);
            }
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
                TagConfigManager.enabled.setValue(PointsTagCache.enabled);
                button.displayString = ForgeMessage.translateOnOff("gui.ptags.tags", PointsTagCache.enabled);
                break;
            case 1603:
                PointsTagCache.self = !PointsTagCache.self;
                TagConfigManager.showSelf.setValue(PointsTagCache.self);
                button.displayString = ForgeMessage.translateOnOff("gui.ptags.self", PointsTagCache.self);
                break;
            case 2000:
                mc.displayGuiScreen(new TagFormattingGui());
                break;
            case 2020:
                PointsTagCache.showTokens = !PointsTagCache.showTokens;
                TagConfigManager.showTokens.setValue(PointsTagCache.showTokens);
                PointsTagCache.clear();
                button.displayString = ForgeMessage.translateOnOff("gui.ptags.tokens", PointsTagCache.showTokens);
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
