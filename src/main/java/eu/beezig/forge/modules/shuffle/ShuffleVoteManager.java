package eu.beezig.forge.modules.shuffle;

import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.function.Consumer;

public class ShuffleVoteManager {
    private boolean waiting, winnerCheck;
    ShuffleVoteListener listener;
    private String lastPage;
    private List<String> favorites;
    private Consumer<String> winnerCallback;
    private WinnerCheckPhase winnerCheckPhase = WinnerCheckPhase.IDLE;

    public ShuffleVoteManager() {
        listener = new ShuffleVoteListener(this);
    }

    public void setWaiting(boolean waiting) {
        if(!waiting) lastPage = null;
        this.waiting = waiting;
    }

    public boolean isInWinnerCheck() {
        return winnerCheck;
    }

    /**
     * Opens the GUI so it can later be checked for the winning mode via checkWinner.
     */
    public void prepareCheckWinner() {
        Minecraft mc = Minecraft.getMinecraft();
        winnerCheckPhase = WinnerCheckPhase.WAITING;
        if(mc.thePlayer.inventory.mainInventory[1] != null) {
            mc.thePlayer.inventory.currentItem = 1;
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
        }
    }

    public void checkWinner(Consumer<String> callback) {
        this.winnerCallback = callback;
        this.winnerCheck = true;
        this.winnerCheckPhase = WinnerCheckPhase.NEXT_PAGE;
        listener.findWinner(Minecraft.getMinecraft().currentScreen);
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

    public Consumer<String> getWinnerCallback() {
        return winnerCallback;
    }

    void reset() {
        waiting = false;
        winnerCheck = false;
        winnerCallback = null;
        lastPage = null;
        winnerCheckPhase = WinnerCheckPhase.IDLE;
    }

    public WinnerCheckPhase getWinnerCheckPhase() {
        return winnerCheckPhase;
    }

    void advanceWinnerCheck() {
        if(winnerCheckPhase.ordinal() + 1 >= WinnerCheckPhase.values().length) return;
        winnerCheckPhase = WinnerCheckPhase.values()[winnerCheckPhase.ordinal() + 1];
    }

    enum WinnerCheckPhase {
        IDLE,
        WAITING,
        NEXT_PAGE,
        PREVIOUS_PAGE
    }
}
