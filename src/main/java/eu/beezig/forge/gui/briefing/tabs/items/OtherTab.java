package eu.beezig.forge.gui.briefing.tabs.items;

import eu.beezig.forge.gui.briefing.tabs.Tab;
import eu.beezig.forge.gui.briefing.tabs.TabGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.net.URI;


public class OtherTab extends Tab {


    public OtherTab() {
        super("Other", new ResourceLocation("beezigforge/gui/other.png"));
    }

    @Override
    protected void init(int windowWidth, int windowHeight) {
        super.init(windowWidth, windowHeight);

        getButtonList().add(new TabGuiButton(this,42, windowWidth / 2 - 50, 108, 100, 20,
                "Click here"));

    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        centered("Join our §bDiscord server§f!", windowWidth / 2, 0, Color.WHITE.getRGB());

    }

    @Override
    protected void onActionPerformed(GuiButton btn) {
        super.onActionPerformed(btn);
        switch (btn.id) {
            case 42 /* Join Discord */:
                try {
                    Desktop.getDesktop().browse(new URI("https://l.beezig.eu/discord"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
