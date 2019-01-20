package tk.roccodev.beezig.forge.gui.briefing.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class TabGuiButton extends GuiButton {

    private Tab tab;

    public TabGuiButton(Tab tab, int buttonId, int x, int y, int width, int height, String buttonText) {
        super(buttonId, x, y, width, height, buttonText);
        this.tab = tab;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(tab.getRenderer().getSelected() != tab.getIndex()) return;
        super.drawButton(mc, mouseX, mouseY);
    }
}
