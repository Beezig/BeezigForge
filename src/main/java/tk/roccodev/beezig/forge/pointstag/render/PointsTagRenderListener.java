package tk.roccodev.beezig.forge.pointstag.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tk.roccodev.beezig.forge.ActiveGame;
import tk.roccodev.beezig.forge.pointstag.PointsTag;
import tk.roccodev.beezig.forge.pointstag.PointsTagCache;
import tk.roccodev.beezig.forge.pointstag.PointsTagStatus;
import tk.roccodev.beezig.forge.pointstag.PointsTagUtils;

public class PointsTagRenderListener {

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre evt) {
        if(!PointsTagCache.enabled) return;
        EntityPlayer p = evt.entityPlayer;
        if(!PointsTagCache.self && p.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) return;
        if(!PointsTagUtils.shouldRender(p)) return;
        if(ActiveGame.current() == null || ActiveGame.current().isEmpty()) return;
        PointsTag tag = PointsTagCache.get(p.getUniqueID());
        if(tag == null) {
            tag = new PointsTag();
            PointsTagCache.putIfAbsent(p.getUniqueID(), tag);
        }
        PointsTagStatus status = tag.getStatus();
        if(status == PointsTagStatus.UNKNOWN) {
            tag.downloadData(p.getUniqueID().toString().replace("-", ""));
        }
        String prefix = "§3" + tag.getPrefix() + ": §b";
        String suffix = status == PointsTagStatus.DONE ? tag.getSuffix() : status.getDisplay();
        double offset = 0.3;
        Scoreboard scoreboard = p.getWorldScoreboard();
        ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);

        if (scoreObjective != null && p.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) < 10 * 10) {
            offset *= 2;
        }
        offset += PointsTagCache.offset;
        PointsTagUtils.render(evt.renderer, prefix + suffix, p, evt.x, evt.y + offset, evt.z);

    }

}
