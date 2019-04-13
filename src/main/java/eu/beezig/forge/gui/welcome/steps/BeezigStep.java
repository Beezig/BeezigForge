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

import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;

import java.io.File;
import java.io.IOException;

public class BeezigStep extends WelcomeGuiStep {


    public BeezigStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Beezig";
    }

    @Override
    public void initGui() {
        super.initGui();
       buttonList.add(new GuiButton(1001, width / 2 - 155, height - 38, 150, 20,
                "Skip tutorial"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Beezig", centerX, height / 2 - 100, 10.0);

        dCenterStr("§bHive plugin for 5zig and LabyMod", centerX, height / 2, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1001 /* Skip */:
                Minecraft.getMinecraft().displayGuiScreen(null);
                PointTagsStep.endTutorial();
                break;
        }
        super.actionPerformed(button);
    }
}
