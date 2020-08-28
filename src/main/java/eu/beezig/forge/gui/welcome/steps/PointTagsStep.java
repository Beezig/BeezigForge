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
import eu.beezig.forge.gui.welcome.WelcomeI18n;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.io.File;
import java.io.IOException;

public class PointTagsStep extends WelcomeGuiStep {
    private final String desc = WelcomeI18n.colorMessage(this, "desc", "§b");
    private final String footer = WelcomeI18n.colorMessage(this, "footer", "§b", "§b/pointtags§r");

    public PointTagsStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Point Tags";
    }

    @Override
    protected String getTranslationKey() {
        return "ptags";
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
        super.actionPerformed(button);
        if(button.id == 0) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            endTutorial();
        }
    }

    static void endTutorial() {
        new Thread(() -> {
            File f = new File(ConfigurationManager.configParent + "tut");
            if(!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
