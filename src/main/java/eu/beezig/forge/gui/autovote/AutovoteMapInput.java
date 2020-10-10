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

package eu.beezig.forge.gui.autovote;

import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.api.BeezigAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class AutovoteMapInput extends GuiScreen {

    private GuiTextField input;
    private AutovoteGui parent;
    private final AutovoteMode mode;

    AutovoteMapInput(AutovoteGui parent, AutovoteMode mode) {
        this.parent = parent;
        this.mode = mode;
    }

    @Override
    public void initGui() {
        super.initGui();
        input = new GuiTextField(1, fontRendererObj,width / 2 - 150, height / 6 + 80, 300, 20);
        buttonList.add(new GuiButton(2, width / 2 - 152, height / 6 + 140, 150, 20, I18n.format("gui.done")));
        buttonList.add(new GuiButton(3, width / 2 + 2, height / 6 + 140, 150, 20, I18n.format("gui.cancel")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        input.drawTextBox();
        drawCenteredString(fontRendererObj, ForgeMessage.translate(mode == AutovoteMode.SHU ? "gui.autovote.input.shu" : "gui.autovote.input")
                        + " " + ForgeMessage.translate("gui.autovote.input.hint"),
                width / 2, height / 6, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        input.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        input.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 2 /* Done */:
                String text = input.getText().toUpperCase().replace(" ", "_");
                Minecraft.getMinecraft().displayGuiScreen(parent);
                if(!text.isEmpty())
                    parent.getList().add(BeezigAPI.normalizeMapName(text));
                break;
            case 3 /* Cancel */:
                Minecraft.getMinecraft().displayGuiScreen(parent);
                break;
        }
        super.actionPerformed(button);
    }
}
