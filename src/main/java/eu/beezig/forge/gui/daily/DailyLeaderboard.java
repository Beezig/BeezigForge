package eu.beezig.forge.gui.daily;

import eu.beezig.forge.ForgeMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;

public class DailyLeaderboard extends GuiListExtended {

    List<Profile> profiles = new ArrayList<>();

    public DailyLeaderboard(List<Profile> profiles, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.profiles.add(new Header());
        this.profiles.addAll(profiles);
    }

    @Override
    protected void drawContainerBackground(Tessellator tessellator) {

    }

    @Override
    public IGuiListEntry getListEntry(int index) {
        return profiles.get(index);
    }

    @Override
    protected int getSize() {
        return profiles.size();
    }

    public static class Profile implements IGuiListEntry {

        protected static final int MAX_NAME_LEN = 16;

        private String uuid, name;
        private int place, points, most, role;
        private String roleColor;

        public Profile() {

        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {

        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            if(roleColor == null) {
                if(role == 50) roleColor = "§6";
                else if(role == 100) roleColor = "§b";
                else roleColor = "§f";
            }
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            int color = 0xff_ff_ff_ff;
            String pts = ForgeMessage.formatNumber(points);
            fr.drawString("#" + place, x - 50, y, color);
            fr.drawString(roleColor + name, x, y, color);
            int ptsPos = x + 40 + fr.getCharWidth('A') * MAX_NAME_LEN;
            fr.drawString(pts, ptsPos, y, color);
            fr.drawString(ForgeMessage.formatNumber(most), ptsPos + 30 + fr.getStringWidth("999,999"), y, color);
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

        }
    }

    private static class Header extends Profile {

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            int color = 0xff_ff_ff_ff;
            fr.drawString("§lPlace", x - 50, y, color);
            fr.drawString("§lName", x, y, color);
            int ptsPos = x + 40 + fr.getCharWidth('A') * MAX_NAME_LEN;
            fr.drawString("§lPoints", ptsPos, y, color);
            fr.drawString("§lMost Points", ptsPos + 30 + fr.getStringWidth("999,999"), y, color);
        }
    }
}
