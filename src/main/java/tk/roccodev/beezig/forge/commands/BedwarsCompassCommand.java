package tk.roccodev.beezig.forge.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import tk.roccodev.beezig.forge.gui.compass.CompassSettingsGui;

public class BedwarsCompassCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "bcompass";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bcompass";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        new CompassSettingsGui().show();

    }

}
