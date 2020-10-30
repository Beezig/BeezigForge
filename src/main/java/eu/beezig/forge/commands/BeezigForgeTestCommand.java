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

import com.google.common.collect.ImmutableMap;
import eu.beezig.core.api.SettingInfo;
import eu.beezig.forge.gui.settings.GuiBeezigSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

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
        List<SettingInfo> settings = new ArrayList<>();
        SettingInfo setting = new SettingInfo();
        setting.name = "You have accessed the secret menu :)";
        setting.value = 9_10_2020;
        setting.desc = "Post this on Discord to look cool";
        settings.add(setting);
        List<SettingInfo> settings2 = new ArrayList<>();
        SettingInfo setting2 = new SettingInfo();
        setting2.name = "Advanced Records: (This is a long string)";
        setting2.value = "Rocco was here";
        setting2.desc = "Don't tell Archer btw";
        settings2.add(setting2);
        GuiBeezigSettings gui = new GuiBeezigSettings(null, ImmutableMap.of("Congratulations", settings, "There's more", settings2));
        gui.show();
    }
}
