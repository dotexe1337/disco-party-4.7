// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

@Com(names = { "kick", "k" })
public class Kick extends Command
{
    @Override
    public void runCommand(final String[] args) {
        ClientUtils.packet(new C03PacketPlayer.C05PacketPlayerLook(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
    }
    
    @Override
    public String getHelp() {
        return "Kick - kick <k> - Kicks you from most servers.";
    }
}
