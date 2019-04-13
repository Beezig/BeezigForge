/*
 * This file is part of BeezigForge.
 *
 * BeezigForge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigForge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigForge.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.gui.welcome;

import eu.beezig.forge.gui.welcome.steps.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WelcomeGui extends GuiScreen {

    private static final Class<? extends WelcomeGuiStep>[] steps = new Class[] {
            BeezigStep.class, AdvancedRecordsStep.class, AutovoteStep.class, CustomModulesStep.class,
            CustomCommandsStep.class, StaffFeaturesStep.class, PointTagsStep.class
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
