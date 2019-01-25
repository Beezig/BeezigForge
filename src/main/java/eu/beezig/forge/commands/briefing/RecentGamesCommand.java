package eu.beezig.forge.commands.briefing;

import eu.beezig.forge.gui.briefing.BriefingGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class RecentGamesCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "recentgames";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/recentgames";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("recentgames", "games");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        BriefingGui briefingGui = new BriefingGui();
        briefingGui.show();
        briefingGui.setSelectedTab(4);
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
