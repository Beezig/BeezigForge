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

package eu.beezig.forge;


import eu.beezig.forge.commands.BeezigForgeTestCommand;
import eu.beezig.forge.commands.DailyCommand;
import eu.beezig.forge.commands.PointsTagCommand;
import eu.beezig.forge.commands.briefing.MapsCommand;
import eu.beezig.forge.commands.briefing.NewsCommand;
import eu.beezig.forge.commands.briefing.RecentGamesCommand;
import eu.beezig.forge.commands.briefing.StaffChangesCommand;
import eu.beezig.forge.gui.briefing.BriefingGui;
import eu.beezig.forge.listener.games.cai.TitleListener;
import eu.beezig.forge.listener.games.timv.EnderchestsListener;
import eu.beezig.forge.modules.compass.render.CompassRenderListener;
import eu.beezig.forge.modules.pointstag.render.PointsTagRenderListener;
import eu.beezig.forge.modules.shuffle.ShuffleForgeListener;
import eu.beezig.forge.packet.BeezigNetHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.network.INetHandler;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;


@Mod(modid = BeezigForgeMod.MODID,
        name = BeezigForgeMod.NAME,
        version = BeezigForgeMod.VERSION,
        updateJSON = "https://rocco.dev/beezighosting/forge/versioning.json")
public class BeezigForgeMod {

    public static final String MODID = "BeezigForge";
    public static final String NAME = "Beezig Forge Expansion";
    public static final String VERSION = "7.0.1";


    private boolean handlerLoaded;
    public static boolean loaded;
    public static boolean versionUpdate;

    public static KeyBinding keybindBeezigGui;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EnderchestsListener());
        MinecraftForge.EVENT_BUS.register(new PointsTagRenderListener());
        MinecraftForge.EVENT_BUS.register(new CompassRenderListener());
        MinecraftForge.EVENT_BUS.register(new ShuffleForgeListener());

        ClientCommandHandler cch = ClientCommandHandler.instance;

        cch.registerCommand(new PointsTagCommand());
        cch.registerCommand(new BeezigForgeTestCommand());
        cch.registerCommand(new DailyCommand());

        cch.registerCommand(new MapsCommand());
        cch.registerCommand(new NewsCommand());
        cch.registerCommand(new RecentGamesCommand());
        cch.registerCommand(new StaffChangesCommand());

        TitleListener.inst = new TitleListener();

        keybindBeezigGui = new KeyBinding("Open Beezig GUI", Keyboard.KEY_B, "Beezig");
        ClientRegistry.registerKeyBinding(keybindBeezigGui);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent evt) {
        if(keybindBeezigGui.isPressed() && Minecraft.getMinecraft().currentScreen == null)
            new BriefingGui().show();
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent evt) {
        if(loaded && Minecraft.getMinecraft().theWorld != null && !handlerLoaded) {
            handlerLoaded = true;
            final INetHandler parent = Minecraft.getMinecraft().thePlayer.sendQueue.getNetworkManager().getNetHandler();
            if (!(parent instanceof BeezigNetHandler)) {
                Minecraft.getMinecraft().thePlayer.sendQueue.getNetworkManager().setNetHandler(new BeezigNetHandler((NetHandlerPlayClient)parent));
            }
        }
        if(versionUpdate && Minecraft.getMinecraft().thePlayer != null) {
            versionUpdate = false;
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ForgeMessage.info + "The version of your Beezig Forge Expansion isn't §3matching your Beezig one."));
            ChatComponentText link = new ChatComponentText(ForgeMessage.info + "§bClick here to update.");
            ChatStyle style = link.getChatStyle();
            style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://l.rocco.dev/beezigforge"));
            style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("§bClick to update.")));
            link.setChatStyle(style);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(link);
        }
    }





}
