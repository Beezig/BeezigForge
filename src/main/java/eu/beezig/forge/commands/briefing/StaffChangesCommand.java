package eu.beezig.forge.commands.briefing;

import eu.beezig.forge.gui.briefing.BriefingGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class StaffChangesCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "staffchanges";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/staffchanges";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("staffchanges", "newstaff");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        BriefingGui briefingGui = new BriefingGui();
        briefingGui.show();
        briefingGui.setSelectedTab(2);
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
