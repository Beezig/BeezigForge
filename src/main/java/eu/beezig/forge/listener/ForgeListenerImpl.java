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

package eu.beezig.forge.listener;

import eu.beezig.forge.API;
import eu.beezig.forge.ActiveGame;
import eu.beezig.forge.BeezigForgeMod;
import eu.beezig.forge.api.command.BeezigCommandRegistry;
import eu.beezig.forge.config.ConfigurationManager;
import eu.beezig.forge.gamefields.TIMV;
import eu.beezig.forge.gui.autovote.AutovoteGui;
import eu.beezig.forge.gui.ctest.CustomTestGui;
import eu.beezig.forge.gui.report.ReportGui;
import eu.beezig.forge.gui.welcome.WelcomeGui;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.io.File;

public class ForgeListenerImpl {

    public void onLoad(String s, String s1) {
        System.out.println("Found Beezig " + s + " on 5zig " + s1);
        BeezigForgeMod.loaded = true;

        if(!s.equals(BeezigForgeMod.VERSION))
           BeezigForgeMod.versionUpdate = true;


        TIMV.callInit();

        if(!new File(ConfigurationManager.configParent + "tut").exists()) {
            new WelcomeGui().show();
        }
    }

    public void setActiveGame(String game) {
        ActiveGame.set(game);
    }

    public void registerCommand(Object commandExecutor) {
        BeezigCommandRegistry.register(commandExecutor);
    }

    public void displayFriendJoin(String player) {
        API.inst.sendTutorial("forge_fj");
        ChatComponentText txt = new ChatComponentText("§a§8▍ §eFriends§8 ▏§a ✚ " + player);
        ChatStyle style = txt.getChatStyle();
        style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party "
                                + EnumChatFormatting.getTextWithoutFormattingCodes(player)));
        style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ChatComponentText("§bInvite " + player + "§b to your party.")));

        if(Minecraft.getMinecraft().thePlayer != null)
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(txt);
    }

    public void displayAutovoteGui() {
        new AutovoteGui(0).show();
    }

    public void displayTIMVTestGui() {
        new CustomTestGui().show();
    }

    public void displayReportGui(String player) {
        new ReportGui(player).show();
    }

}
