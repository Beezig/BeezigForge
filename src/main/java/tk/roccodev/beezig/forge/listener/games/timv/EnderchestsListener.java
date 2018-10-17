package tk.roccodev.beezig.forge.listener.games.timv;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tk.roccodev.beezig.forge.ActiveGame;
import tk.roccodev.beezig.forge.Log;
import tk.roccodev.beezig.forge.gamefields.TIMV;

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
                if(lastCompass != null)
                    lastCompass.setStackDisplayName("§3Nearest Ender Chest - §b" + (int)pl.getDistanceSq(ent.getPos()) + "m");
            }
            else if(lastCompass != null)
                lastCompass.setStackDisplayName("§3Tester Compass");
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

}
