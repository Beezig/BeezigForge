package tk.roccodev.beezig.forge.listener.games.timv;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tk.roccodev.beezig.forge.gamefields.TIMV;

public class EnderchestsListener {

    @SubscribeEvent
    public void onRenderChunk(TickEvent.ClientTickEvent evt) {
        try {
            if(evt.phase != TickEvent.Phase.END) return;
            if(Minecraft.getMinecraft().theWorld == null) return;
            World w = Minecraft.getMinecraft().theWorld;

            TIMV.setEnderchests(w.loadedTileEntityList.stream()
                    .filter(tileEntity -> tileEntity.getBlockType() == Blocks.ender_chest).count());
        } catch(Exception ignored) {}
    }

}
