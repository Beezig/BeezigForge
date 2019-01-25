package eu.beezig.forge.commands.briefing;

import eu.beezig.forge.gui.briefing.BriefingGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class NewsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "news";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/news";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        BriefingGui briefingGui = new BriefingGui();
        briefingGui.show();
        briefingGui.setSelectedTab(0);
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
