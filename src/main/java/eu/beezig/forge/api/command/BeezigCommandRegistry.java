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

package eu.beezig.forge.api.command;

import com.mojang.authlib.GameProfile;
import eu.beezig.forge.ForgeMessage;
import eu.beezig.forge.tabcompletion.BeezigCommandExecutor;
import eu.beezig.forge.tabcompletion.TabCompletionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;

import java.lang.reflect.Method;
import java.util.List;

public class BeezigCommandRegistry {

    public static void register(Object obj) {
        try {
            Class cl = obj.getClass();
            String name = (String) cl.getMethod("getName").invoke(obj);
            String[] aliases = (String[]) cl.getMethod("getAliases").invoke(obj);
            Method exec = cl.getMethod("execute", String[].class);
            Method tabCompletion = tabCompletion(cl);

            for(String alias : aliases) {
                ClientCommandHandler.instance.registerCommand(new BeezigCommandExecutor() {
                    @Override
                    public String getCommandName() {
                        return alias.substring(1);
                    }

                    public String getInternalName() {
                        return name;
                    }

                    @Override
                    public String getCommandUsage(ICommandSender sender) {
                        return "";
                    }

                    @Override
                    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
                        try {
                            if(!(boolean)exec.invoke(obj,(Object) args)) {
                                Minecraft.getMinecraft().thePlayer
                                        .sendChatMessage(alias + " " + String.join(" ", args));
                            }

                        } catch (Exception e) {
                            sender.addChatMessage(new ChatComponentText(ForgeMessage.error + "An unexpected error occurred " +
                                    "§cwhile attempting to §cperform the command."));
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
                        if(!(sender instanceof EntityPlayer)) return super.addTabCompletionOptions(sender, args, pos);
                        try {
                            Object tabCompl = tabCompletion == null
                                    ? TabCompletionUtils.matching(args)
                                    : tabCompletion.invoke(obj, ((EntityPlayer)sender).getGameProfile(), args);
                            if(tabCompl instanceof List) {
                                return (List<String>) tabCompl;
                            }

                        } catch (Exception ignored) {}
                        return TabCompletionUtils.matching(args);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method tabCompletion(Class cl) {
        try {
            return cl.getMethod("addTabCompletionOptions",
                    GameProfile.class, String[].class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
