package eu.beezig.forge.gui.welcome.steps;

import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;

import java.io.File;
import java.io.IOException;

public class BeezigStep extends WelcomeGuiStep {


    public BeezigStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Beezig";
    }

    @Override
    public void initGui() {
        super.initGui();
       buttonList.add(new GuiButton(1001, width / 2 - 155, height - 38, 150, 20,
                "Skip tutorial"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Beezig", centerX, height / 2 - 100, 10.0);

        dCenterStr("§bHive plugin for 5zig and LabyMod", centerX, height / 2, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 1001 /* Skip */:
                Minecraft.getMinecraft().displayGuiScreen(null);
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
                break;
        }
        super.actionPerformed(button);
    }
}
