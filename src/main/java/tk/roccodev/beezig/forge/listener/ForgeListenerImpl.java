package tk.roccodev.beezig.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.config.IConfigElement;
import scala.actors.threadpool.Arrays;
import tk.roccodev.beezig.forge.API;
import tk.roccodev.beezig.forge.ActiveGame;
import tk.roccodev.beezig.forge.BeezigForgeMod;
import tk.roccodev.beezig.forge.api.command.BeezigCommandRegistry;
import tk.roccodev.beezig.forge.gamefields.TIMV;
import tk.roccodev.beezig.forge.gui.autovote.AutovoteGui;
import tk.roccodev.beezig.forge.gui.settings.GuiBeezigSettings;
import tk.roccodev.beezig.forge.settings.BeezigConfigElement;
import tk.roccodev.beezig.forge.tabcompletion.BeezigCommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class ForgeListenerImpl {

    public void onLoad(String s, String s1) {
        System.out.println("Found Beezig " + s + " on 5zig " + s1);
        BeezigForgeMod.loaded = true;

        if(!s.equals(BeezigForgeMod.VERSION))
           BeezigForgeMod.versionUpdate = true;


        TIMV.callInit();

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

    public void onDisplaySettingsGui(Object settings) {


        try {
            Object[] arr = (Object[]) settings.getClass().getField("array").get(settings);
            List<IConfigElement> elements = new ArrayList<>();
            for (Object o : arr) {
                String enumName = o.toString();
                String desc = (String) o.getClass().getMethod("getBriefDescription").invoke(o);
                String briefDesc = (String) o.getClass().getMethod("getBrieferDescription").invoke(o);
                boolean enabled = (boolean) o.getClass().getMethod("getValue").invoke(o);


                elements.add(new BeezigConfigElement(enumName, desc, enabled, briefDesc));
            }

            new GuiBeezigSettings(null, elements).show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void displayAutovoteGui() {
        new AutovoteGui(0).show();
    }

}
