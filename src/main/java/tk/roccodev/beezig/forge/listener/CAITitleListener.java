package tk.roccodev.beezig.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S45PacketTitle;
import tk.roccodev.beezig.forge.API;


public class CAITitleListener {

    public static CAITitleListener inst;

    public boolean onTitle(S45PacketTitle packetIn) {
        if(packetIn == null) return true;
        int type = packetIn.getType().ordinal();
        if(type != 1) return true;
        if(packetIn.getMessage() == null) return true;
        String msg = packetIn.getMessage().getFormattedText();

        if(msg.equals("§r§eThe leader of the §r§bindians§r§e has been§r§4 CAUGHT§r§e!§r")) {
            String caiTeam = API.inst.getCAITeam();
            if(caiTeam.equals("Indians")) {
                Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                        "§cYour leader has been §e§lCAUGHT!",
                        packetIn.getFadeInTime(),
                        packetIn.getDisplayTime(),
                        packetIn.getFadeOutTime());
            }
            else {
                Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                        "§aThe enemy leader has been §e§lCAUGHT!",
                        packetIn.getFadeInTime(),
                        packetIn.getDisplayTime(),
                        packetIn.getFadeOutTime());
            }
            return false;
        }
        else if(msg.equals("§r§eThe leader of the §r§bcowboys§r§e has been§r§4 CAUGHT§r§e!§r")) {
            String caiTeam = API.inst.getCAITeam();
            if(caiTeam.equals("Cowboys")) {
                Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                        "§cYour leader has been §e§lCAUGHT!",
                        packetIn.getFadeInTime(),
                        packetIn.getDisplayTime(),
                        packetIn.getFadeOutTime());
            }
            else {
                Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                        "§aThe enemy leader has been §e§lCAUGHT!",
                        packetIn.getFadeInTime(),
                        packetIn.getDisplayTime(),
                        packetIn.getFadeOutTime());
            }
            return false;
        }
        else if(msg.startsWith("§r§cCowboys§r§b leader")) {
            if(msg.endsWith("has been§r§6 §r§6§lCAPTURED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.equals("Cowboys")) {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§cYour leader has been §4§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aThe enemy leader has been §6§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            else if(msg.endsWith("has§r§5 §r§5§lESCAPED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.equals("Cowboys")) {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aYour leader has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§eThe enemy leader has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            return false;
        }
        else if(msg.startsWith("§r§eIndians§r§b leader")) {
            if(msg.endsWith("has been§r§6 §r§6§lCAPTURED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.equals("Indians")) {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§cYour leader has been §4§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aThe enemy leader has been §6§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            else if(msg.endsWith("has§r§5 §r§5§lESCAPED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.equals("Indians")) {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aYour leader has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§eThe enemy leader has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            return false;
        }


        return true;

    }



}
