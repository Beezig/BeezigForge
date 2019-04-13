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
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.io.File;
import java.io.IOException;

public class CustomModulesStep extends WelcomeGuiStep {

    public CustomModulesStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Modules";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Custom modules", centerX, 60, 4.0);

        dCenterStr("With Beezig, you can display §byour stats§f when", centerX, 140, 2.0);
        dCenterStr("you play.", centerX, 158, 2.0);

        dCenterStr("If you use §bLabyMod§f, you have to §benable them§f.", centerX, 200, 2.0);
    }
}
