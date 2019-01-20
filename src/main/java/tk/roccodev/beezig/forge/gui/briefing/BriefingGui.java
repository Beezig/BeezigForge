package tk.roccodev.beezig.forge.gui.briefing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tk.roccodev.beezig.forge.gui.briefing.tabs.TabRenderer;

import java.io.IOException;
import java.util.List;

public class BriefingGui extends GuiScreen {

    private TabRenderer render;
    private Class labyTabs;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        render.renderTabs(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        try {
            labyTabs = Class.forName("net.labymod.gui.elements.Tabs");
            labyTabs.getMethod("initGuiScreen", List.class, GuiScreen.class)
                    .invoke(null, buttonList, this);
        }
        catch(Exception ignored) {}
        this.render = new TabRenderer(width, height, buttonList);
        super.initGui();

    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        render.onKeyTyped(keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        render.onMouseClick(mouseX, mouseY);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        render.onMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        try {
            if(labyTabs != null)
                labyTabs.getMethod("actionPerformedButton", GuiButton.class)
                        .invoke(null, button);
        }
        catch (Exception ignored) {}
        render.onActionPerformed(button);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
}
