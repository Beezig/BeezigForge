package eu.beezig.forge.modules.shuffle;

import eu.beezig.forge.api.BeezigAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuffleVoteListener {
    private static final Pattern TITLE_REGEX = Pattern.compile("Vote for an Option \\[(\\d+)]");
    private final ShuffleVoteManager mgr;

    public ShuffleVoteListener(ShuffleVoteManager mgr) {
        this.mgr = mgr;
    }

    public void onGuiOpen(GuiScreen gui) {
        if(!mgr.isWaiting()) return;
        if(gui instanceof GuiChest) {
            GuiChest container = (GuiChest) gui;
            ContainerChest chest = (ContainerChest) container.inventorySlots;
            IInventory inv = chest.getLowerChestInventory();
            Matcher m = TITLE_REGEX.matcher(inv.getDisplayName().getUnformattedText());
            ItemStack close = chest.getSlotFromInventory(chest.getLowerChestInventory(), 49).getStack();
            if(close == null) return;
            if(m.matches()) {
                String page = m.group(1);
                if(page.equals(mgr.getLastPage())) return;
                for(int i = 0; i <= 44; i += 9) {
                    ItemStack stack = chest.getSlotFromInventory(chest.getLowerChestInventory(), i).getStack();
                    if(stack == null) break;
                    String mode = BeezigAPI.normalizeMapName(EnumChatFormatting.getTextWithoutFormattingCodes(stack.getTooltip(Minecraft.getMinecraft().thePlayer, false).get(0)));
                    if(mgr.getFavorites().contains(mode)) {
                        mgr.setWaiting(false);
                        Minecraft.getMinecraft().playerController.windowClick(chest.windowId, i, 0, 0, Minecraft.getMinecraft().thePlayer);
                        return;
                    }
                }
                ItemStack nextPage = chest.getSlotFromInventory(chest.getLowerChestInventory(), 50).getStack();
                if(nextPage != null) {
                    mgr.setLastPage(page);
                    Minecraft.getMinecraft().playerController.windowClick(chest.windowId, 50, 0, 0, Minecraft.getMinecraft().thePlayer);
                    return;
                }
                mgr.reset();
                Minecraft.getMinecraft().thePlayer.closeScreen();
            }
        }
    }

    public void findWinner(GuiScreen gui) {
        if(!mgr.isInWinnerCheck() || mgr.getWinnerCheckPhase() == ShuffleVoteManager.WinnerCheckPhase.IDLE) return;
        if(gui instanceof GuiChest) {
            GuiChest container = (GuiChest) gui;
            ContainerChest chest = (ContainerChest) container.inventorySlots;
            IInventory inv = chest.getLowerChestInventory();
            Matcher m = TITLE_REGEX.matcher(inv.getDisplayName().getUnformattedText());
            if(m.matches()) {
                ItemStack close = chest.getSlotFromInventory(chest.getLowerChestInventory(), 49).getStack();
                if(close == null) return;
                String page = m.group(1);
                if(page.equals(mgr.getLastPage())) return;
                for(int i = 0; i <= 44; i += 9) {
                    ItemStack stack = chest.getSlotFromInventory(chest.getLowerChestInventory(), i).getStack();
                    if(stack == null) break;
                    List<String> tooltip = stack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
                    if(tooltip.stream().anyMatch(p -> p.endsWith("§a> Winning <"))) {
                        String mode = EnumChatFormatting.getTextWithoutFormattingCodes(tooltip.get(0));
                        mgr.getWinnerCallback().accept(mode);
                        mgr.reset();
                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        return;
                    }
                }
                int slot = mgr.getWinnerCheckPhase() == ShuffleVoteManager.WinnerCheckPhase.NEXT_PAGE ? 50 : 48;
                ItemStack nextPreviousPage = chest.getSlotFromInventory(chest.getLowerChestInventory(), slot).getStack();
                mgr.advanceWinnerCheck();
                if(nextPreviousPage != null) {
                    mgr.setLastPage(page);
                    Minecraft.getMinecraft().playerController.windowClick(chest.windowId, slot, 0, 0, Minecraft.getMinecraft().thePlayer);
                    return;
                }
                mgr.reset();
                Minecraft.getMinecraft().thePlayer.closeScreen();
            }
        }
    }
}
