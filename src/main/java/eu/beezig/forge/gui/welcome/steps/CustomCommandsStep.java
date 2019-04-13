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

import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;

public class CustomCommandsStep extends WelcomeGuiStep {

    public CustomCommandsStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Commands";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Commands", centerX, 60, 4.0);

        dCenterStr("Beezig includes §bcustom commands§f.", centerX, 140, 2.0);
        dCenterStr("Our users love these:", centerX, 158, 2.0);

        dCenterStr("- §b/report§f to report people.", centerX, 200, 1.5);
        dCenterStr("- §b/ps§f to check the stats of everyone in your lobby.", centerX, 200 + (int)(9 * 1.5), 1.5);
        dCenterStr("- §b/monthly§f to check someone's monthly stats.", centerX, 200 + (int)(9 * 1.5) * 2, 1.5);
        dCenterStr("- §b/bestgame§f to find someone's best game.", centerX, 200 + (int)(9 * 1.5) * 3, 1.5);

        dCenterStr("You can see all the available commands by running §b/beezig commands§f.", centerX, 300, 2.0);
    }
}
