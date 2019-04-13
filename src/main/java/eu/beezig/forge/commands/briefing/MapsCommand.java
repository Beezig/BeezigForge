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

package eu.beezig.forge.commands.briefing;

import eu.beezig.forge.gui.briefing.BriefingGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class MapsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "newmaps";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/newmaps";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        BriefingGui briefingGui = new BriefingGui();
        briefingGui.show();
        briefingGui.setSelectedTab(3);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
