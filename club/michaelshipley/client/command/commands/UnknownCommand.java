// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.utils.ClientUtils;

@Com(names = { "" })
public class UnknownCommand extends Command
{
    @Override
    public void runCommand(final String[] args) {
        ClientUtils.sendMessage("Unknown Command. Type \"help\" for a list of commands.");
    }
}
