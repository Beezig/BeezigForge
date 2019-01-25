package eu.beezig.forge.gui.welcome.steps;

import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.io.File;
import java.io.IOException;

public class PointTagsStep extends WelcomeGuiStep {

    public PointTagsStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Point Tags";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Point Tags", centerX, 60, 4.0);

        dCenterStr("With Beezig, you can see §bpeople's points§r", centerX, 140, 2.0);
        dCenterStr("above their head.", centerX, 158, 2.0);

        dCenterStr("To configure it, run §b/pointtags§r.", centerX, 200, 2.0);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
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
