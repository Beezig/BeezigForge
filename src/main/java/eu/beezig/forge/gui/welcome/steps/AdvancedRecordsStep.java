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

public class AdvancedRecordsStep extends WelcomeGuiStep {

    private boolean setting;
    private final String desc = WelcomeI18n.colorMessage(this, "desc", "§b");

    public AdvancedRecordsStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Advanced Records";
    }

    @Override
    protected String getTranslationKey() {
        return "advrec";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1001, width / 2 - 80, 200, 160, 20,
                WelcomeI18n.button(this, "setting", setting = (boolean) BeezigAPI.getSetting("ADVANCED_RECORDS"))));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int centerX = width / 2;
        dCenterStr(WelcomeI18n.title(this), centerX, 60, 4.0);
        drawCenteredWrapped(desc, centerX, 140, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1001 /* Toggle */:
                setting = !setting;
                BeezigAPI.setSetting("ADVANCED_RECORDS", setting);
                button.displayString = WelcomeI18n.button(this, "setting", setting);
                break;
        }
        super.actionPerformed(button);
    }
}
