// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.command.commands;

import java.util.Iterator;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.client.command.CommandManager;
import club.michaelshipley.utils.ClientUtils;

@Com(names = { "help" })
public class Help extends Command
{
    @Override
    public void runCommand(final String[] args) {
        for (final Command command : CommandManager.commandList) {
            if (command instanceof OptionCommand) {
                continue;
            }
            if (command.getHelp() == null) {
                continue;
            }
            ClientUtils.sendMessage(command.getHelp());
        }
        ClientUtils.sendMessage(OptionCommand.getHelpString());
    }
    
    @Override
    public String getHelp() {
        return "Help - help - Returns a list of commands.";
    }
}
