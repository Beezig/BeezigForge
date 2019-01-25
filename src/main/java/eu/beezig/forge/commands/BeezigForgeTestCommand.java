package eu.beezig.forge.commands;

import eu.beezig.forge.gui.ctest.CustomTestGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import eu.beezig.forge.gui.welcome.WelcomeGui;

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
        new CustomTestGui().show();
    }
}
