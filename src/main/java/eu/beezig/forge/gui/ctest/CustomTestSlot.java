package eu.beezig.forge.gui.ctest;

import eu.beezig.forge.API;
import eu.beezig.forge.gui.autovote.AutovoteMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSlot;

import java.util.ArrayList;
import java.util.List;

public class CustomTestSlot extends GuiSlot {

    List<String> savedLines = new ArrayList<>();

    private FontRenderer frObj;
    private CustomTestGui parent;
    private int selected = 0;

    CustomTestSlot(Minecraft mcIn, CustomTestGui parent) {
        super(mcIn, parent.width, parent.height, 32, parent.height - 65 + 4, 18);
        frObj = mc.fontRendererObj;
        this.parent = parent;
    }

    @Override
    protected int getSize() {
        return savedLines.size();
    }

    @Override
    protected int getContentHeight() {
        return getSize() * 18;
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        selected = slotIndex;
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return selected == slotIndex;
    }

    @Override
    protected void drawBackground() {
        parent.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        parent.drawCenteredString(frObj, savedLines.get(entryID), width / 2, p_180791_3_ + 1, 16777215);
    }

    int getSelected() {
        return selected;
    }

    void setData(List<String> data) {
        savedLines = data;
        selected = 0;
    }

    void save() {
        API.inst.setTIMVMessages(savedLines);
    }

    void remove() {
        savedLines.remove(selected);
    }

    void add(String in) {
        savedLines.add(in);
    }
}
