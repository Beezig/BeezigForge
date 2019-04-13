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

package eu.beezig.forge.modules.pointstag.render;

import eu.beezig.forge.ActiveGame;
import eu.beezig.forge.modules.pointstag.PointsTag;
import eu.beezig.forge.modules.pointstag.PointsTagCache;
import eu.beezig.forge.modules.pointstag.PointsTagStatus;
import eu.beezig.forge.modules.pointstag.PointsTagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Method;

public class PointsTagRenderListener {

    public static Method mGetHeight;

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post evt) {
        if(ActiveGame.current() == null || ActiveGame.current().isEmpty()) return;
        if(!PointsTagCache.enabled) return;
        EntityPlayer p = evt.entityPlayer;
        if(p.getName().isEmpty() || p.getName().contains("NPC-")) return;
        if(!PointsTagCache.self && p.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) return;
        if(!PointsTagUtils.shouldRender(p)) return;
        PointsTag tag = PointsTagCache.get(p.getUniqueID());
        if(tag == null) {
            tag = new PointsTag();
            PointsTagCache.putIfAbsent(p.getUniqueID(), tag);
        }
        PointsTagStatus status = tag.getStatus();
        if(status == PointsTagStatus.UNKNOWN) {
            tag.downloadData(p.getUniqueID().toString().replace("-", ""));
        }
        String key = tag.getKey();
        String value = status == PointsTagStatus.DONE ? tag.getValue() : status.getDisplay();
        double offset = 0.3;

        if(mGetHeight != null) {
            try {
                offset += (float) mGetHeight.invoke(null, p.getGameProfile().getId());
            }
            catch (Exception ignored) {}
        }

        Scoreboard scoreboard = p.getWorldScoreboard();
        ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);

        if (scoreObjective != null && p.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) < 10 * 10) {
            offset *= 2;
        }
        offset += PointsTagCache.offset;
        String toRender = PointsTagCache.formatting.replace("{k}", key).replace("{v}", value).replace("{r}",
                PointsTagCache.colorRank ? tag.getRank() : EnumChatFormatting.getTextWithoutFormattingCodes(tag.getRank())).trim();
        if(!PointsTagCache.colorAll) toRender = "§f" + EnumChatFormatting.getTextWithoutFormattingCodes(toRender);
        PointsTagUtils.render(evt.renderer, toRender, p, evt.x, evt.y + offset, evt.z);

    }

}
