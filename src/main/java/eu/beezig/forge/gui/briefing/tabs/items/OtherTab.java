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
        getButtonList().add(new TabGuiButton(this,43, windowWidth / 2 - 50, 148, 100, 20,
                "Click here"));
        getButtonList().add(new TabGuiButton(this,44, windowWidth / 2 - 50, 188, 100, 20,
                "Click here"));

    }

    @Override
    protected void drawTab(int mouseX, int mouseY) {
        super.drawTab(mouseX, mouseY);

        centered("Join our §bDiscord server§f!", windowWidth / 2, 0, Color.WHITE.getRGB());
        centered("Check out our §bwebsite§f!", windowWidth / 2, 40, Color.WHITE.getRGB());
        centered("Need help? Read the §bwiki§f!", windowWidth / 2, 80, Color.WHITE.getRGB());

    }

    @Override
    protected void onActionPerformed(GuiButton btn) {
        super.onActionPerformed(btn);
        switch (btn.id) {
            case 42 /* Join Discord */:
                browse("https://l.beezig.eu/discord");
                break;
            case 43 /* Website */:
                browse("https://beezig.eu");
                break;
            case 44 /* Wiki */:
                browse("https://beezig.eu/wiki");
                break;
        }
    }

    private void browse(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
