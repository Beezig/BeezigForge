package eu.beezig.forge.modules.shuffle;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ShuffleForgeListener {
    public static final ShuffleVoteManager mgr = new ShuffleVoteManager();

    @SubscribeEvent
    public void onGuiOpen(GuiScreenEvent.DrawScreenEvent.Post event) {
        mgr.listener.onGuiOpen(event.gui);
        mgr.listener.findWinner(event.gui);
    }
}
