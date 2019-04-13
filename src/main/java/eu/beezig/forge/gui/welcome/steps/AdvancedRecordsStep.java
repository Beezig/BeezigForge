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
import net.minecraft.client.gui.GuiButton;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;

import java.io.IOException;

public class AdvancedRecordsStep extends WelcomeGuiStep {

    private boolean setting;

    public AdvancedRecordsStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Advanced Records";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1001, width / 2 - 80, 200, 160, 20,
                "Enable Advanced Records: " + ((setting = API.inst.getSettingValue("ADVANCED_RECORDS")) ? "Yes" : "No")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Advanced Records", centerX, 60, 4.0);

        dCenterStr("With Beezig, you can get §bmore stats§r when checking someone's", centerX, 140, 2.0);
        dCenterStr(" using /stats or /records.", centerX, 158, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1001 /* Toggle */:
                setting = !setting;
                API.inst.setSetting("ADVANCED_RECORDS", setting);
                button.displayString = "Enable Advanced Records: " + (setting ? "Yes" : "No");
                break;
        }
        super.actionPerformed(button);
    }
}
