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

package eu.beezig.forge.commands;

import com.google.common.collect.Lists;
import eu.beezig.core.api.SettingInfo;
import eu.beezig.forge.gui.settings.speedrun.GuiSpeedrunSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class BeezigForgeTestCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "bftest";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bftest";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        SettingInfo color = new SettingInfo();
        color.key = "COLOR_TEST";
        color.name = "Color test";
        color.desc = "Color test desc";
        color.value = 0xFFADEAEA;
        new GuiSpeedrunSettings(Lists.newArrayList(color)).show();
    }
}
