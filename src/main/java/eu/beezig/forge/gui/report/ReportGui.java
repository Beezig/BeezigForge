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

package eu.beezig.forge.gui.report;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

public class ReportGui extends GuiChest {

    private InventoryBasic upperInv;
    private String target;

    public ReportGui(String playerTarget) {
        this(new InventoryBasic("Report " + playerTarget, true, 9),
                Minecraft.getMinecraft().thePlayer);
        this.target = playerTarget;
    }

    private ReportGui(InventoryBasic inv, EntityPlayerSP thePlayer) {
        super(thePlayer.inventory, inv);
        this.upperInv = inv;

        initInventory();
    }

    private void initInventory() {
        ItemStack[] items = new ItemStack[] {
                buildItem(Items.iron_sword, "§6Combat Hacks"),
                buildItem(Items.feather, "§bMovement Hacks"),
                buildItem(Items.coal, "§fOther")
        };
        addItems(items);
    }

    private ItemStack buildItem(Item item, String name) {
        ItemStack stack = new ItemStack(item, 1);
        stack.setStackDisplayName(name);
        return stack;
    }

    private void addItems(ItemStack[] items) {
        upperInv.clear();
        for(int i = 0; i < items.length; i++) {
            upperInv.setInventorySlotContents(i, items[i]);
        }
    }

    public void show() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Slot slot = getSlotUnderMouse();
        if(slot == null) return;
        if(slot.slotNumber >= 9) return;
        if(!slot.getHasStack()) return;
        String reason = EnumChatFormatting.getTextWithoutFormattingCodes(slot.getStack().getDisplayName());
        if(reason == null) return;

        Minecraft.getMinecraft().thePlayer.sendChatMessage("/report " + target + " " + reason);
        Minecraft.getMinecraft().displayGuiScreen(null);
    }
}
