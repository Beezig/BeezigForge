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

    public static BlockPos testerSpawnPt;
    public static boolean customSpawnPt;
    private ItemStack lastCompass;

    @SubscribeEvent
    public void onRenderChunk(TickEvent.ClientTickEvent evt) {
        if(!ActiveGame.current().equals("TIMV")) return;
        try {
            if(evt.phase != TickEvent.Phase.END) return;
            if(Minecraft.getMinecraft().theWorld == null) return;

            World w = Minecraft.getMinecraft().theWorld;

            EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
            Minecraft mc = Minecraft.getMinecraft();
            if(pl == null) return;


            List<TileEntity> ents = w.loadedTileEntityList.stream()
                    .filter(tileEntity -> tileEntity.getBlockType() == Blocks.ender_chest)
                    .sorted((tileEntity, t1) -> pl.getDistanceSq(tileEntity.getPos())
                            < pl.getDistanceSq(t1.getPos()) ? 0 : 1).collect(Collectors.toList());
            TIMV.setEnderchests(ents.size());

            if(customSpawnPt) {
                if(ents.size() == 0) {
                    customSpawnPt = false;
                    pl.addChatComponentMessage(new ChatComponentText(Log.error + "No ender chests found. Changing back to tester mode."));
                    return;
                }
                TileEntity ent = ents.get(0);
                pl.worldObj.setSpawnPoint(ent.getPos());
                if(pl.getCurrentEquippedItem() == null || pl.getCurrentEquippedItem().getItem() != Items.compass) return;
                if(lastCompass != null)
                   mc.ingameGUI.setRecordPlaying("§3Nearest Ender Chest - §b" +
                           (int) Math.sqrt(pl.getDistanceSq(ent.getPos())) + "m", false);
            }
            else if(testerSpawnPt != null
            && (pl.getCurrentEquippedItem() != null && pl.getCurrentEquippedItem().getItem() == Items.compass))
                mc.ingameGUI.setRecordPlaying("§6Distance to Tester - §e" +
                        (int) Math.sqrt(pl.getDistanceSq(testerSpawnPt)) + "m", false);
        } catch(Exception ignored) {}
    }

    @SubscribeEvent
    public void onUseCompass(PlayerInteractEvent evt) {
        if(!ActiveGame.current().equals("TIMV")) return;
       if((evt.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
               || evt.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
               && evt.entityPlayer.getCurrentEquippedItem() != null
               && evt.entityPlayer.getCurrentEquippedItem().getItem() == Items.compass) {
           customSpawnPt = !customSpawnPt;
           if (!customSpawnPt) {
               evt.entityPlayer.worldObj.setSpawnPoint(testerSpawnPt);
               evt.entityPlayer.addChatComponentMessage(new ChatComponentText(Log.info + "Now pointing towards tester."));
           } else
               evt.entityPlayer.addChatComponentMessage(new ChatComponentText(Log.info + "Now pointing towards nearest enderchest."));

           lastCompass = evt.entityPlayer.getCurrentEquippedItem();

       }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent evt) {
        if(!ActiveGame.current().equals("TIMV")) return;
        if(evt.type == 2) {
            EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
            if(pl == null) return;
            if(pl.getCurrentEquippedItem() == null) return;
            if(pl.getCurrentEquippedItem().getItem() == Items.compass)  evt.setCanceled(true);
        }
    }

}
