package eu.beezig.forge.gui.welcome.steps;

import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;

public class AutovoteStep extends WelcomeGuiStep {

    public AutovoteStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Autovoting";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Autovoting", centerX, 60, 4.0);

        dCenterStr("With Beezig, you can §bautomatically vote for maps§r", centerX, 140, 2.0);
        dCenterStr("when you join a lobby.", centerX, 158, 2.0);

        dCenterStr("To add or remove maps, run §b/autovote§r.", centerX, 200, 2.0);
    }

}
