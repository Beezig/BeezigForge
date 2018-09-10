package tk.roccodev.beezig.forge.listener.games.cai;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.EnumChatFormatting;
import tk.roccodev.beezig.forge.API;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TitleListener {

    public static TitleListener inst;

    public boolean onTitle(S45PacketTitle packetIn) {
        if(packetIn == null) return true;
        int type = packetIn.getType().ordinal();
        if(type != 1) return true;
        if(packetIn.getMessage() == null) return true;
        String msg = packetIn.getMessage().getFormattedText();
        if(!API.inst.getSettingValue("CAI_TITLE")) return true;

        boolean setting = API.inst.getSettingValue("CAI_TITLE_SHOWNAME");

        if(msg.equals("§r§eThe leader of the §r§bindians§r§e has been§r§4 CAUGHT§r§e!§r")) {
            String caiTeam = API.inst.getCAITeam();
            if(caiTeam.isEmpty()) return true;
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
            if(caiTeam.isEmpty()) return true;
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
            String name = null;
            if(setting) {
                Pattern p = Pattern.compile("(?:^|\\s)'([^']*?)'(?:$|\\s)", Pattern.MULTILINE);
                Matcher m = p.matcher(msg);
                if(m.find()) {
                    name = '(' + "§c" + EnumChatFormatting.getTextWithoutFormattingCodes(m.group().replace("'", "").replace(" ", "")) + "~" + ')' + " ";
                }
            }
            else {
                name = "";
            }
            if(msg.endsWith("has been§r§6 §r§6§lCAPTURED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.isEmpty()) return true;
                if(caiTeam.equals("Cowboys")) {
                    name = name.replace("~", "§c");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§cYour leader " + name + "has been §4§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    name = name.replace("~", "§a");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aThe enemy leader " + name +  "§ahas been §6§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            else if(msg.endsWith("has§r§5 §r§5§lESCAPED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.isEmpty()) return true;
                if(caiTeam.equals("Cowboys")) {
                    name = name.replace("~", "§a");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aYour leader " + name + "has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    name = name.replace("~", "§e");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§eThe enemy leader " + name + "has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            return false;
        }
        else if(msg.startsWith("§r§eIndians§r§b leader")) {
            String name = null;
            if(setting) {
                Pattern p = Pattern.compile("(?:^|\\s)'([^']*?)'(?:$|\\s)", Pattern.MULTILINE);
                Matcher m = p.matcher(msg);
                if(m.find()) {
                    name = '(' + "§e" + EnumChatFormatting.getTextWithoutFormattingCodes(m.group().replace("'", "").replace(" ", ""))  + "~" + ')' + " ";
                }
            }
            else {
                name = "";
            }
            if(msg.endsWith("has been§r§6 §r§6§lCAPTURED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.isEmpty()) return true;
                if(caiTeam.equals("Indians")) {
                    name = name.replace("~", "§c");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§cYour leader " + name + "has been §4§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    name = name.replace("~", "§a");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aThe enemy leader " + name +  "has been §6§lCAPTURED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
            }
            else if(msg.endsWith("has§r§5 §r§5§lESCAPED!§r")) {
                String caiTeam = API.inst.getCAITeam();
                if(caiTeam.isEmpty()) return true;
                if(caiTeam.equals("Indians")) {
                    name = name.replace("~", "§a");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§aYour leader " + name + "has §7§lESCAPED!",
                            packetIn.getFadeInTime(),
                            packetIn.getDisplayTime(),
                            packetIn.getFadeOutTime());
                }
                else {
                    name = name.replace("~", "§e");
                    Minecraft.getMinecraft().ingameGUI.displayTitle(null,
                            "§eThe enemy leader " + name + "has §7§lESCAPED!",
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
