package tk.roccodev.beezig.forge;


import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.network.INetHandler;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import tk.roccodev.beezig.forge.api.BeezigAPIImpl;
import tk.roccodev.beezig.forge.init.ClassFinder;
import tk.roccodev.beezig.forge.listener.CAITitleListener;
import tk.roccodev.beezig.forge.packet.BeezigNetHandler;


@Mod(modid = BeezigForgeMod.MODID,
        name = BeezigForgeMod.NAME,
        version = BeezigForgeMod.VERSION,
        updateJSON = "https://roccodev.pw/beezighosting/forge/versioning.json")
public class BeezigForgeMod {

    public static final String MODID = "BeezigForge";
    public static final String NAME = "Beezig Forge Expansion";
    public static final String VERSION = "4.9.0";


    private boolean handlerLoaded;
    public static boolean loaded;
    public static boolean versionUpdate;



    @Mod.EventHandler
    public void onInit(FMLInitializationEvent evt) {

        MinecraftForge.EVENT_BUS.register(this);
        CAITitleListener.inst = new CAITitleListener();
    }

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent evt) {

        try {
            ClassFinder.init();

            Class api = ClassFinder.findClass("tk.roccodev.beezig.api.BeezigAPI");

            API.privInst = api.getMethod("get")
                    .invoke(null);
            API.inst = BeezigAPIImpl.fromObject(API.privInst);

            api.getMethod("registerListener", Object.class)
                    .invoke(API.privInst,
                    Class.forName("tk.roccodev.beezig.forge.listener.ForgeListenerImpl", true, this.getClass().getClassLoader())
                            .newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }




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
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(Log.info + "The version of your Beezig Forge Expansion isn't §3matching your Beezig one."));
            ChatComponentText link = new ChatComponentText(Log.info + "§bClick here to update.");
            ChatStyle style = link.getChatStyle();
            style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://l.roccodev.pw/beezigforge"));
            style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("§bClick to update.")));
            link.setChatStyle(style);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(link);
        }
    }





}
