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

import eu.beezig.forge.API;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public class AutovoteStep extends WelcomeGuiStep {

    private boolean enabled, random;

    public AutovoteStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Autovoting";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1002, width / 2 - 80, 240, 160, 20,
                "Enable Autovoting: " + ((enabled = API.inst.getSettingValue("AUTOVOTE")) ? "Yes" : "No")));
        buttonList.add(new GuiButton(1003, width / 2 - 80, 270, 160, 20,
                "Vote for Random: " + ((random = API.inst.getSettingValue("AUTOVOTE_RANDOM")) ? "Yes" : "No")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Autovoting", centerX, 60, 4.0);

        dCenterStr("With Beezig, you can §bautomatically vote for maps§r", centerX, 140, 2.0);
        dCenterStr("when you join a lobby.", centerX, 158, 2.0);

        dCenterStr("To add or remove maps, run §b/autovote§r.", centerX, 200, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1002 /* Toggle */:
                enabled = !enabled;
                API.inst.setSetting("AUTOVOTE", enabled);
                button.displayString = "Enable Autovoting: " + (enabled ? "Yes" : "No");
                break;
            case 1003 /* Vote for random */:
                random = !random;
                API.inst.setSetting("AUTOVOTE_RANDOM", random);
                button.displayString = "Vote for Random: " + (random ? "Yes" : "No");
                break;
        }
        super.actionPerformed(button);
    }

}
