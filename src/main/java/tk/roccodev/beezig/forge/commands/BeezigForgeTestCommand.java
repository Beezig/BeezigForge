package tk.roccodev.beezig.forge.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import tk.roccodev.beezig.forge.gui.autovote.AutovoteGui;

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
        new AutovoteGui(0).show();
    }
}
