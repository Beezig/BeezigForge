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

import eu.beezig.forge.api.BeezigAPI;
import eu.beezig.forge.badge.BadgeRenderer;
import eu.beezig.forge.badge.BadgeService;
import eu.beezig.forge.modules.pointstag.PointsTag;
import eu.beezig.forge.modules.pointstag.PointsTagCache;
import eu.beezig.forge.modules.pointstag.PointsTagStatus;
import eu.beezig.forge.modules.pointstag.PointsTagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class PointsTagRenderListener {

    public static Function<UUID, Float> heightFunc;
    private TagRenderer tagRenderer = new TagRenderer();
    private static AtomicBoolean steveModeSet = new AtomicBoolean(false);

    public void doRender(EntityPlayer p, double x, double y, double z, float partialTicks, RenderPlayer renderer) {
        if(!BeezigAPI.isOnHive()) return;
        if(!PointsTagCache.enabled) return;
        if(!PointsTagCache.showTokens && BeezigAPI.getCurrentGame() == null) return;
        if(p.getName().isEmpty() || p.getName().contains("NPC-")) return;
        if(!PointsTagCache.self && p.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) return;
        if(!PointsTagUtils.shouldRender(p)) return;
        if (!steveModeSet.get()) {
            steveModeSet.set(true);
            Map<String, Object> overrides = BeezigAPI.getOverrides(Minecraft.getMinecraft().thePlayer.getUniqueID());
            if(overrides != null && overrides.containsKey("steve-mode.enabled") && ((boolean) overrides.get("steve-mode.enabled"))) {
                PointsTagStatus.enableSteveMode((String) overrides.get("steve-mode.text"));
            }
        }
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

        if(heightFunc != null) {
            offset += heightFunc.apply(p.getGameProfile().getId());
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
        UUID uuid = p.getUniqueID();
        int role = BeezigAPI.getUserRole(uuid);
        BadgeRenderer badge = BadgeService.getBadge(role);
        Map<String, Object> overrides;
        if ((overrides = BeezigAPI.getOverrides(uuid)) != null &&
            overrides.containsKey("custom-badge.url") && overrides.containsKey("custom-badge.priority") &&
                ((int) overrides.get("custom-badge.priority")) >= role) {
            badge = BadgeService.getBadge((String) overrides.get("custom-badge.url"), uuid);
        }
        tagRenderer.renderNameAndBadge(toRender, badge, new RenderData(p, x, y, z, partialTicks, renderer), offset);
    }

    static class RenderData {
        public EntityPlayer player;
        public RenderPlayer renderer;
        public double x, y, z;
        public float partialTicks;

        public RenderData(EntityPlayer player, double x, double y, double z, float partialTicks, RenderPlayer renderer) {
            this.player = player;
            this.x = x;
            this.y = y;
            this.z = z;
            this.partialTicks = partialTicks;
            this.renderer = renderer;
        }
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post evt) {
        doRender(evt.entityPlayer, evt.x, evt.y, evt.z, evt.partialRenderTick, evt.renderer);
    }
}
