package eu.beezig.forge.gui.welcome.steps;

import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import eu.beezig.forge.gui.welcome.WelcomeGuiStep;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.io.File;
import java.io.IOException;

public class CustomModulesStep extends WelcomeGuiStep {

    public CustomModulesStep(WelcomeGui parent) {
        super(parent);
    }

    @Override
    public String getName() {
        return "Modules";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int centerX = width / 2;

        dCenterStr("§3Custom modules", centerX, 60, 4.0);

        dCenterStr("With Beezig, you can display §byour stats§f when", centerX, 140, 2.0);
        dCenterStr("you play.", centerX, 158, 2.0);

        dCenterStr("If you use §bLabyMod§f, you have to §benable them§f.", centerX, 200, 2.0);
    }
}
