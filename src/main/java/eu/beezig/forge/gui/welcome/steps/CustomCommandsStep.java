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

public class CustomCommandsStep extends WelcomeGuiStep {
    private final String desc = WelcomeI18n.colorMessage(this, "desc", "§b");
    private final String footer = WelcomeI18n.colorMessage(this, "footer", "§b", "§b/beezig §bcommands§r");
    private final String report = WelcomeI18n.colorMessage(this, "report", "§b", "§b/breport§r");
    private final String ps = WelcomeI18n.colorMessage(this, "ps", "§b", "§b/ps§r");
    private final String monthly = WelcomeI18n.colorMessage(this, "monthly", "§b", "§b/monthly§r");

    public CustomCommandsStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Commands";
    }

    @Override
    protected String getTranslationKey() {
        return "cmds";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int centerX = width / 2;
        dCenterStr(WelcomeI18n.title(this), centerX, 60, 4.0);
        drawCenteredWrapped(desc, centerX, 140, 2.0);
        drawCenteredWrapped(report, centerX, 200, 1.5);
        drawCenteredWrapped(ps, centerX, 200 + (int)(10 * 1.5), 1.5);
        drawCenteredWrapped(monthly, centerX, 200 + (int)(20 * 1.5), 1.5);
        drawCenteredWrapped(footer, centerX, 300, 2.0);
    }
}
