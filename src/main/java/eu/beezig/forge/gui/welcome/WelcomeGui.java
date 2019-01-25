package eu.beezig.forge.gui.welcome;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import eu.beezig.forge.gui.welcome.steps.AdvancedRecordsStep;
import eu.beezig.forge.gui.welcome.steps.AutovoteStep;
import eu.beezig.forge.gui.welcome.steps.BeezigStep;
import eu.beezig.forge.gui.welcome.steps.PointTagsStep;

public class WelcomeGui extends GuiScreen {

    private static final Class<? extends WelcomeGuiStep>[] steps = new Class[] {
            BeezigStep.class, AdvancedRecordsStep.class, AutovoteStep.class, PointTagsStep.class
    };

    private int currentStepIndex = 0;
    private WelcomeGuiStep currentStep = new BeezigStep(this);

    public void advanceStep(int add) {
        currentStepIndex += add;
        setCurrentStep();
    }

    public WelcomeGuiStep getNext(int add) {
        try {
            return steps[currentStepIndex + add].getConstructor(WelcomeGui.class).newInstance(this);
        } catch (Exception ignored) {}
        return null;
    }

    private void setCurrentStep() {
        try {
            currentStep = steps[currentStepIndex].getConstructor(WelcomeGui.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Minecraft.getMinecraft().displayGuiScreen(currentStep);
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        advanceStep(0);
    }

}
