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

package eu.beezig.forge.gui.settings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.function.Consumer;

public class GuiTextInput extends GuiScreen {
    private final GuiScreen lastScreen;
    private GuiTextField inputField;
    private final Consumer<String> callback;
    private String defaultText;
    private final String title;
    private GuiButton doneButton;

    public GuiTextInput(GuiScreen lastScreen, Consumer<String> callback, String title) {
        this.lastScreen = lastScreen;
        this.callback = callback;
        this.title = title;
    }

    public GuiTextInput(GuiScreen lastScreen, Consumer<String> callback, String defaultText, String title) {
        this(lastScreen, callback, title);
        this.defaultText = defaultText;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        inputField.drawTextBox();
        if(title != null) drawCenteredString(mc.fontRendererObj, title, width / 2, height / 6, 0xffffffff);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        inputField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true); // Allows holding keys to type multiple characters
        buttonList.add(doneButton = new GuiButton(1, width / 2 - 152, height / 6 + 140, 150, 20, I18n.format("gui.done")));
        buttonList.add(new GuiButton(2, width / 2 + 2, height / 6 + 140, 150, 20, I18n.format("gui.cancel")));
        inputField = new GuiTextField(3, mc.fontRendererObj, width / 2 - 150, height / 6 + 80, 300, 20);
        inputField.setMaxStringLength(100);
        if(defaultText != null) inputField.setText(defaultText);
        else doneButton.enabled = false;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        inputField.textboxKeyTyped(typedChar, keyCode);
        doneButton.enabled = !inputField.getText().isEmpty();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 2) {
            callback.accept(null);
            mc.displayGuiScreen(lastScreen);
        } else if(button.id == 1) {
            callback.accept(inputField.getText());
            mc.displayGuiScreen(lastScreen);
        }
    }
}
