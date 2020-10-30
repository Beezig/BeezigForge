package eu.beezig.forge.modules.shuffle;

import net.minecraft.client.Minecraft;

import java.util.List;

public class ShuffleVoteManager {
    private boolean waiting;
    ShuffleVoteListener listener;
    private String lastPage;
    private List<String> favorites;

    public ShuffleVoteManager() {
        listener = new ShuffleVoteListener(this);
    }

    public void setWaiting(boolean waiting) {
        if(!waiting) lastPage = null;
        this.waiting = waiting;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void attemptVote(List<String> favorites) {
        this.favorites = favorites;
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.thePlayer.inventory.mainInventory[1] != null) {
            waiting = true;
            mc.thePlayer.inventory.currentItem = 1;
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
        }
    }
}
