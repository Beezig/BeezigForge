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

import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;
import eu.beezig.forge.gui.welcome.WelcomeI18n;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public class StaffFeaturesStep extends WelcomeGuiStep {
    private final String desc = WelcomeI18n.colorMessage(this, "desc", "§c");
    private boolean reports;

    public StaffFeaturesStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Staff features";
    }

    @Override
    protected String getTranslationKey() {
        return "staff";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1004, width / 2 - 80, 240, 160, 20,
                WelcomeI18n.button(this, "setting", (reports = (boolean) BeezigAPI.getSetting("REPORTS_NOTIFY")))));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int centerX = width / 2;
        dCenterStr(WelcomeI18n.title(this, "§c"), centerX, 60, 4.0);
        drawCenteredWrapped(desc, centerX, 140, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1004) { /* Toggle reports */
            reports = !reports;
            BeezigAPI.setSetting("REPORTS_NOTIFY", reports);
            button.displayString = WelcomeI18n.button(this, "setting", reports);
        }
        super.actionPerformed(button);
    }
}
