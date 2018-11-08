package tk.roccodev.beezig.forge.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import tk.roccodev.beezig.forge.gui.pointstag.TagSettingsGui;

import java.util.Arrays;
import java.util.List;

public class PointsTagCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "pointstag";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/pointstag";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("pointstag", "pointtags");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        new TagSettingsGui().show();

    }
}
