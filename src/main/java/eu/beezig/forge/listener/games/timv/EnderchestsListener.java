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

package eu.beezig.forge.listener.games.timv;

import eu.beezig.forge.gamefields.TIMV;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import eu.beezig.forge.ActiveGame;
import eu.beezig.forge.Log;

import java.util.List;
import java.util.stream.Collectors;

public class EnderchestsListener {

    @SubscribeEvent
    public void onRenderChunk(TickEvent.ClientTickEvent evt) {
        if(!ActiveGame.current().equals("TIMV")) return;
        try {
            if(evt.phase != TickEvent.Phase.END) return;
            if(Minecraft.getMinecraft().theWorld == null) return;

            World w = Minecraft.getMinecraft().theWorld;

            EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
            if(pl == null) return;
            List<TileEntity> ents = w.loadedTileEntityList.stream()
                    .filter(tileEntity -> tileEntity.getBlockType() == Blocks.ender_chest)
                    .sorted((tileEntity, t1) -> pl.getDistanceSq(tileEntity.getPos())
                            < pl.getDistanceSq(t1.getPos()) ? 0 : 1).collect(Collectors.toList());
            TIMV.setEnderchests(ents.size());
        } catch(Exception ignored) {}
    }
}
