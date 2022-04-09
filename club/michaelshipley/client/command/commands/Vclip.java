package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.utils.ClientUtils;

@Com(names = { "vclip", "vc" })
public class Vclip extends Command {

    @Override
    public void runCommand(final String[] args) {
    	ClientUtils.player().posY += Integer.parseInt(args[1]);
    }
    
    @Override
    public String getHelp() {
        return "Vclip - vclip <blocks> - Teleports the player the set amount on the Y axis";
    }
	
}
