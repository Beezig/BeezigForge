package tk.roccodev.beezig.forge.gui.settings;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import tk.roccodev.beezig.forge.API;

import java.util.List;

public class GuiBeezigSettings extends GuiConfig {

    public GuiBeezigSettings(GuiScreen parentScreen, List<IConfigElement> configElements) {
        super(parentScreen, configElements, "BeezigForge", false, false, "Beezig Config GUI");

    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if(button.id == 2000) {
            API.inst.saveConfigData(this.configElements.toArray());
        }
    }
}
