package tk.roccodev.beezig.forge.gui.pointstag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Mouse;
import tk.roccodev.beezig.forge.config.pointstag.TagConfigManager;
import tk.roccodev.beezig.forge.modules.pointstag.PointsTagCache;

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
        this.buttonList.add(new GuiButton(3011, this.width / 2 - 68, this.height / 2 - 30, 150, 20,
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
                .replace("{r}", "§f§lWatson").trim();
        this.drawCenteredString(this.fontRendererObj, "Preview: " + preview, this.width / 2, this.height / 2 + 10, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7Available variables:", this.width / 2, this.height / 2 + 50, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7{k} Prefix, e.g, 'Points'", this.width / 2, this.height / 2 + 60, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7{v} Value, e.g, '250,100'", this.width / 2, this.height / 2 + 70, 16777215);
        this.drawCenteredString(this.fontRendererObj, "§7{r} Rank, for supported modes, e.g, 'Forensic'", this.width / 2, this.height / 2 + 80, 16777215);
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
                TagConfigManager.save();
                mc.displayGuiScreen(new TagSettingsGui());
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
