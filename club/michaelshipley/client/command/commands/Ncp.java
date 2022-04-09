// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

@Com(names = { "ncp", "testncp" })
public class Ncp extends Command
{
    @Override
    public void runCommand(final String[] args) {
        if (args.length > 1) {
            ClientUtils.packet(new C01PacketChatMessage("/testncp input"));
            ClientUtils.packet(new C01PacketChatMessage("/testncp input " + args[1]));
        }
        else {
            ClientUtils.sendMessage(this.getHelp());
        }
    }
    
    @Override
    public String getHelp() {
        return "Ncp - ncp <testncp> (name) - Sets a player as a testncp target.";
    }
}
