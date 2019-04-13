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

public class StaffFeaturesStep extends WelcomeGuiStep {

    private boolean reports, chat;

    public StaffFeaturesStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Staff features";
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1004, width / 2 - 80, 240, 160, 20,
                "Receive reports: " + ((reports = API.inst.getSettingValue("MOD_REPORT_NOTIFICATION")) ? "Yes" : "No")));
        buttonList.add(new GuiButton(1005, width / 2 - 80, 270, 160, 20,
                "Quick staff chat: " + ((chat = API.inst.getSettingValue("STAFF_CHAT")) ? "Yes" : "No")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§cStaff features", centerX, 60, 4.0);

        dCenterStr("If you are a §cstaff member§f, you can benefit from awesome features.", centerX, 140, 2.0);
        dCenterStr("- You can §creceive reports in-game§f from Beezig users.", centerX, 200, 1.5);
        dCenterStr("- You can quickly talk in §cstaff chat§f by putting §c~§f before the message.", centerX, 200 + (int)(9 * 1.5), 1.5);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1004 /* Toggle reports */:
                reports = !reports;
                API.inst.setSetting("MOD_REPORT_NOTIFICATION", reports);
                button.displayString = "Receive reports: " + (reports ? "Yes" : "No");
                break;
            case 1005 /* Toggle chat */:
                chat = !chat;
                API.inst.setSetting("STAFF_CHAT", chat);
                button.displayString = "Quick staff chat: " + (chat ? "Yes" : "No");
                break;
        }
        super.actionPerformed(button);
    }
}
