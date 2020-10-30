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
import eu.beezig.forge.gui.welcome.WelcomeI18n;

public class CustomModulesStep extends WelcomeGuiStep {
    private final String desc = WelcomeI18n.colorMessage(this, "desc", "§b");
    private final String footer = WelcomeI18n.colorMessage(this, "desc2", "§b");

    public CustomModulesStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Modules";
    }

    @Override
    protected String getTranslationKey() {
        return "modules";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int centerX = width / 2;
        dCenterStr(WelcomeI18n.title(this), centerX, 60, 4.0);
        drawCenteredWrapped(desc, centerX, 140, 2.0);
        drawCenteredWrapped(footer, centerX, 200, 2.0);
    }
}
