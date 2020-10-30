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

public class AutovoteStep extends WelcomeGuiStep {

    private boolean enabled, random;
    private final String desc = WelcomeI18n.colorMessage(this, "desc", "§b");
    private final String footer = WelcomeI18n.colorMessage(this, "desc2", "§b");

    public AutovoteStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Autovoting";
    }

    @Override
    protected String getTranslationKey() {
        return "av";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1002, width / 2 - 80, 240, 160, 20,
                WelcomeI18n.button(this, "setting", (enabled = (boolean) BeezigAPI.getSetting("AUTOVOTE")))));
        buttonList.add(new GuiButton(1003, width / 2 - 80, 270, 160, 20,
                WelcomeI18n.button(this, "setting.random", (random = (boolean) BeezigAPI.getSetting("AUTOVOTE_RANDOM")))));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int centerX = width / 2;
        dCenterStr(WelcomeI18n.title(this), centerX, 60, 4.0);
        drawCenteredWrapped(desc, centerX, 140, 2.0);
        drawCenteredWrapped(footer, centerX, 200, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1002 /* Toggle */:
                enabled = !enabled;
                BeezigAPI.setSetting("AUTOVOTE", enabled);
                button.displayString = WelcomeI18n.button(this, "setting", enabled);
                break;
            case 1003 /* Vote for random */:
                random = !random;
                BeezigAPI.setSetting("AUTOVOTE_RANDOM", random);
                button.displayString = WelcomeI18n.button(this, "setting.random", random);
                break;
        }
        super.actionPerformed(button);
    }

}
