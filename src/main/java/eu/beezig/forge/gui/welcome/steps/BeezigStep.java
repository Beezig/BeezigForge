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

package eu.beezig.forge.gui.welcome.steps;

import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.gui.welcome.GuiCheckBox;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public class BeezigStep extends WelcomeGuiStep {
    private GuiCheckBox telemetryBox;
    private boolean skip = true;

    public BeezigStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Beezig";
    }

    @Override
    protected String getTranslationKey() {
        return "beezig";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1001, width / 2 - 155, height - 38, 150, 20,
                ForgeMessage.translate("tut.beezig.skip")));
        String boxText = ForgeMessage.translate("tut.beezig.telemetry");
        // 11: box width
        buttonList.add(telemetryBox = new GuiCheckBox(3, (width - 11 - fontRendererObj.getStringWidth(boxText)) / 2, height - 60, boxText, true));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int centerX = width / 2;
        dCenterStr("§3Beezig", centerX, height / 2 - 100, 10.0);
        dCenterStr("§b" + ForgeMessage.translate("tut.beezig.desc"), centerX, height / 2, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0) {
            skip = false;
            BeezigAPI.setSettingAsIs("TELEMETRY", telemetryBox.isChecked());
        } else if(button.id == 1001) {
            // Skip
            mc.displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if(skip) {
            BeezigAPI.setSettingAsIs("TELEMETRY", telemetryBox.isChecked());
            PointTagsStep.endTutorial();
        }
    }
}
