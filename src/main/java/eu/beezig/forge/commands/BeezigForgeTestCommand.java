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
import eu.beezig.forge.api.SettingInfo;
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
        setting.name = "Test Setting";
        setting.value = true;
        setting.desc = "Test Desc";
        settings.add(setting);
        List<SettingInfo> settings2 = new ArrayList<>();
        SettingInfo setting2 = new SettingInfo();
        setting2.name = "String Setting";
        setting2.value = "Text value";
        setting2.desc = "Test Desc String\nNew Line";
        settings2.add(setting2);
        GuiBeezigSettings gui = new GuiBeezigSettings(null, ImmutableMap.of("Test Category", settings, "Category 2", settings2));
        gui.show();
    }
}
