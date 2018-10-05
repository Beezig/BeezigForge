package tk.roccodev.beezig.forge.gui.compass;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tk.roccodev.beezig.forge.config.compass.CompassConfigManager;
import tk.roccodev.beezig.forge.modules.compass.CompassManager;
import tk.roccodev.beezig.forge.modules.compass.render.CompassRenderListener;

import java.io.IOException;

public class CompassSettingsGui extends GuiScreen {

    private int lastX, lastY;
    private boolean dragging;

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 - 50, 120, 20,
                "Enabled: " + (CompassManager.enabled ? "Yes" : "No")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 25, 120, 20,
                "Reset Position"));
        this.buttonList.add(new GuiSlider(3, this.width / 2 - 60, this.height / 2,
                120, 20, "Dot Size: ", "",
                1d, 10d, CompassManager.size, false, true, slider -> {
            CompassManager.size = slider.getValueInt();
            CompassConfigManager.size.set(slider.getValueInt());
        }));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2 + 25, 120, 20,
                "Done"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(CompassManager.enabled) {
            CompassRenderListener.drawDummy(this.width);
        }
        if(dragging) {
            CompassRenderListener.offX += mouseX - lastX;
            CompassRenderListener.offY += mouseY - lastY;
            lastX = mouseX;
            lastY = mouseY;

        }
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                CompassManager.enabled = !CompassManager.enabled;
                CompassConfigManager.enabled.set(CompassManager.enabled);
                button.displayString = "Enabled: " + (CompassManager.enabled ? "Yes" : "No");
                break;
            case 1:
                CompassConfigManager.x.set(0);
                CompassConfigManager.y.set(0);
                CompassRenderListener.offX = 0;
                CompassRenderListener.offY = 0;
                break;
            case 2:
                onGuiClosed();
                Minecraft.getMinecraft().currentScreen = null;
                Minecraft.getMinecraft().setIngameFocus();
                break;
        }
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        CompassConfigManager.x.set(CompassRenderListener.offX);
        CompassConfigManager.y.set(CompassRenderListener.offY);
        CompassConfigManager.save();
        super.onGuiClosed();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= (this.width - 184) / 2 + CompassRenderListener.offX &&
                mouseY >= CompassRenderListener.offY && mouseX <= (this.width + 184) / 2 +
                CompassRenderListener.offX && mouseY <= CompassRenderListener.offY + 20)  {
            this.dragging = true;
            this.lastX = mouseX;
            this.lastY = mouseY;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

}
