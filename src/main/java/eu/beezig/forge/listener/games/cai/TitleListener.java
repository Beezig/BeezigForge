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

package eu.beezig.forge.listener.games.cai;

import eu.beezig.forge.API;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.EnumChatFormatting;

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
