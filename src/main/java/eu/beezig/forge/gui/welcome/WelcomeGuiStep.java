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

package eu.beezig.forge.gui.welcome;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public abstract class WelcomeGuiStep extends GuiScreen {

    protected WelcomeGui parent;

    public WelcomeGuiStep(WelcomeGui parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0 /* Next */:
                parent.advanceStep(1);
                break;
            case 1 /* Back */:
                parent.advanceStep(-1);
                break;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        WelcomeGuiStep next = parent.getNext(1);
        WelcomeGuiStep previous = parent.getNext(-1);

        buttonList.add(new GuiButton(0, this.width / 2 - 155 + 160, this.height - 38, 150, 20,
                next != null ? next.getName() + " >" : "Done"));

        if(previous != null)
            buttonList.add(new GuiButton(1, this.width / 2 - 155, this.height - 38, 150, 20,
                    "< " + previous.getName()));
    }

    protected void dCenterStr(String str, int x, int y, double scaleFactor) {
        if(scaleFactor != 1d) {
            GL11.glPushMatrix();
            GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            fr.drawString(str,
                    (float)(x / scaleFactor) - (fr.getStringWidth(str) / 2f), (float)(y / scaleFactor), 16777215, true);
            GL11.glPopMatrix();
        }
        else
            drawCenteredString(Minecraft.getMinecraft().fontRendererObj, str, x, y, 16777215);
    }

    protected void dCenterStr(String str, int x, int y) {
        dCenterStr(str, x, y, 1d);
    }

    public abstract String getName();
}
