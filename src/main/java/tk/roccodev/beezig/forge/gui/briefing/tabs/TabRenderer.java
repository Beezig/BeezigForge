package tk.roccodev.beezig.forge.gui.briefing.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TabRenderer extends Gui {

    private static int colorDefault = new Color(56, 143, 145).darker().getRGB();
    private static int colorSelected = new Color(66, 210, 214).brighter().getRGB();

    private int width, height;
    private int selected;
    private TabRenderUtils render;

    public TabRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        this.render = TabRenderUtils.getInstance();

        for(Tab tab : Tabs.tabs) {
            tab.init();
        }
    }
    
    
    public void renderTabs(int mouseX, int mouseY) {

        int offsetIcon = -103;
        int offsetLine = -115;
        int offsetLineEnd = -64;

        for(int i = 0; i < Tabs.tabs.length; i++) {
            Tab tab = Tabs.tabs[i];
            this.drawHorizontalLine(this.getWidth() / 2 + offsetLine, this.getWidth() / 2 + offsetLineEnd, 70,
                    selected == i
                    ? colorSelected
                    : colorDefault);
            Minecraft.getMinecraft().getTextureManager().bindTexture(tab.getIcon());
            this.render.drawTexture(this.getWidth() / 2 + offsetIcon, 38.0, 256.0, 256.0, 25.0, 25.0);

            tab.setPosition(width / 2 + offsetIcon, 38, 25, 25);
            tab.setWindow(width);

            offsetIcon += 50;
            offsetLine += 50;
            offsetLineEnd += 50;
        }

        this.drawTitle(Tabs.tabs[selected].getTitle());
        Tabs.tabs[selected].drawTab(mouseX, mouseY);
    }

    private void drawTitle(String title) {
        TabRenderUtils.getInstance().drawCenteredString(title, width / 2, 80.0, 1.45);
    }

    private void switchSelected(int adding) {
        int newIndex = selected + adding;
        if(newIndex >= Tabs.tabs.length) newIndex = 0;
        if(newIndex < 0) newIndex = Tabs.tabs.length - 1;
        selected = newIndex;
    }

    public void onKeyTyped(int keyCode) {
        if(keyCode == Keyboard.KEY_RIGHT)
            switchSelected(1);
        else if(keyCode == Keyboard.KEY_LEFT)
            switchSelected(-1);
    }

    public void onMouseClick(int mouseX, int mouseY) {
        Tabs.tabs[selected].onMouseClick(mouseX, mouseY);

        Tab hovering = Tabs.getTabByBox(mouseX, mouseY);
        if(hovering != null) {
            selected = hovering.getIndex();
        }
    }

    public void onMouseInput() {
        Tabs.tabs[selected].handleMouse();
    }

    private int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
