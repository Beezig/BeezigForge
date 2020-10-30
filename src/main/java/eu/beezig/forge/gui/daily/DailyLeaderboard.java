package eu.beezig.forge.gui.daily;

import com.google.gson.annotations.SerializedName;
import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.api.BeezigAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DailyLeaderboard extends GuiListExtended {
    private Date resetTime;
    private final List<Profile> profiles = new ArrayList<>();
    private List<Extension> extensions;

    public DailyLeaderboard(List<Profile> profiles, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        boolean global = !profiles.isEmpty() && profiles.get(0).region != null;
        Header header = new Header();
        header.global = global;
        this.profiles.add(header);
        this.profiles.addAll(profiles);
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 35;
    }

    public void setResetTime(long resetTime) {
        this.resetTime = resetTime == -1 ? null : new Date(resetTime * 1000);
    }

    public String getFormattedResetTime() {
        if(resetTime == null) return null;
        long duration = resetTime.getTime() - System.currentTimeMillis();
        String dateFmt;
        if(duration >= 1000 * 60 * 60) dateFmt = "H'h' m'min' s's'";
        else if(duration >= 60) dateFmt = "m'min' s's'";
        else dateFmt = "s's'";
        return DurationFormatUtils.formatDuration(duration, dateFmt);
    }

    public void onGuiClose() {
        if(extensions != null && !extensions.isEmpty()) {
            BeezigAPI.sendDailyExtensions(extensions.stream().map(e -> new ImmutablePair<>(e.name, e.shortLink)).collect(Collectors.toList()));
        }
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

    static class Extension {
        private String name;
        @SerializedName("short_link")
        private String shortLink;
    }

    public static class Profile implements IGuiListEntry {

        protected static final int MAX_NAME_LEN = 16;

        private String uuid, name, region;
        private int place, points, most, roleColor = -1;
        private DailyLeaderboard.Role role;
        private String ptsStr;

        public Profile() {

        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {

        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            if(roleColor == -1) {
                roleColor = Integer.parseInt(role.color.substring(1), 16);
                ptsStr = ForgeMessage.formatNumber(points);
            }
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            int color = 0xff_ff_ff_ff;
            fr.drawString("#" + place, x - 50, y, color);
            if(region != null) fr.drawString(name + " §7(" + region.toUpperCase() + ")", x, y, roleColor);
            else fr.drawString(name, x, y, roleColor);
            int ptsPos = x + 40 + fr.getCharWidth('A') * (region != null ? MAX_NAME_LEN + 6 : MAX_NAME_LEN);
            fr.drawString(ptsStr, ptsPos, y, color);
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

    private static class Role {
        String color;
    }

    private static class Header extends Profile {
        private boolean global;

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            int color = 0xff_ff_ff_ff;
            fr.drawString("§l" + ForgeMessage.translate("gui.daily.place"), x - 50, y, color);
            fr.drawString("§l" + ForgeMessage.translate("gui.daily.name"), x, y, color);
            int ptsPos = x + 40 + fr.getCharWidth('A') * (global ? MAX_NAME_LEN + 6 : MAX_NAME_LEN);
            fr.drawString("§l" + ForgeMessage.translate("gui.daily.points"), ptsPos, y, color);
            fr.drawString("§l" + ForgeMessage.translate("gui.daily.most"), ptsPos + 30 + fr.getStringWidth("999,999"), y, color);
        }
    }
}
