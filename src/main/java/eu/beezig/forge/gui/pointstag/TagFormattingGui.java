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

package eu.beezig.forge.gui.pointstag;

import eu.beezig.forge.config.pointstag.TagConfigManager;
import eu.beezig.forge.modules.pointstag.PointsTagCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class TagFormattingGui extends GuiScreen {

    private Minecraft mc;
    private GuiTextField input;

    public TagFormattingGui() {
        mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        Mouse.setGrabbed(false);
        input = new GuiTextField(2000, this.fontRendererObj, this.width / 2 - 68, this.height / 2 - 70,
                150, 20);
        input.setText(translateAlternateColorCodesReverse(PointsTagCache.formatting));
        this.buttonList.add(new GuiButton(3013, this.width / 2 - 68, this.height / 2 - 30, 150, 20,
                "Colored rank: " + (PointsTagCache.colorRank ? "Yes" : "No")));
        this.buttonList.add(new GuiButton(3012, this.width / 2 - 68, this.height / 2, 150, 20,
                "Colored: " + (PointsTagCache.colorAll ? "Yes" : "No")));
        this.buttonList.add(new GuiButton(3011, this.width / 2 - 68, this.height / 2 + 30, 150, 20,
                "Done"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        this.drawDefaultBackground();
        input.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Edit formatting", this.width / 2, 40, 16777215);
        String preview = translateAlternateColorCodes('&', input.getText())
                .replace("{k}", "Points")
                .replace("{v}", "201,530")
                .replace("{r}", PointsTagCache.colorRank ? "§f§lWatson" : "Watson").trim();
        if(!PointsTagCache.colorAll) preview = "§f" + EnumChatFormatting.getTextWithoutFormattingCodes(preview);
        this.drawCenteredString(this.fontRendererObj, "Preview: " + preview, this.width / 2, this.height / 2 + 60, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7Available variables:", this.width / 2, this.height / 2 + 90, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7{k} Prefix, e.g, 'Points'", this.width / 2, this.height / 2 + 100, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7{v} Value, e.g, '250,100'", this.width / 2, this.height / 2 + 110, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7{r} Rank, for supported modes, e.g, 'Forensic'", this.width / 2, this.height / 2 + 120, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        input.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 3011:
                String converted = translateAlternateColorCodes('&', input.getText());
                PointsTagCache.formatting = converted;
                TagConfigManager.formatting.set(converted);

                TagConfigManager.colorAll.set(PointsTagCache.colorAll);
                TagConfigManager.colorRank.set(PointsTagCache.colorRank);

                TagConfigManager.save();
                mc.displayGuiScreen(new TagSettingsGui());
                break;
            case 3012:
                PointsTagCache.colorAll = !PointsTagCache.colorAll;
                button.displayString = "Colored: " + (PointsTagCache.colorAll ? "Yes" : "No");
                break;
            case 3013:
                PointsTagCache.colorRank = !PointsTagCache.colorRank;
                button.displayString = "Colored rank: " + (PointsTagCache.colorRank ? "Yes" : "No");
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        input.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        input.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '\u00A7';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    private String translateAlternateColorCodesReverse(String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '§' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '&';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }


}
