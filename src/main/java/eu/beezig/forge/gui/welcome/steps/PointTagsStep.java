package eu.beezig.forge.gui.welcome.steps;

import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;

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
}
