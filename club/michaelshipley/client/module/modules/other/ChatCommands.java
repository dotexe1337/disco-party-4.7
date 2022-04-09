// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.command.Command;
import club.michaelshipley.client.command.CommandManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.PacketSendEvent;
import net.minecraft.network.play.client.C01PacketChatMessage;
@Mod(displayName = "Chat Commands")
public class ChatCommands extends Module
{
    @EventTarget
    private void onPacketSend(final PacketSendEvent event) {
        if (event.getPacket() instanceof C01PacketChatMessage) {
            final C01PacketChatMessage packet = (C01PacketChatMessage)event.getPacket();
            String message = packet.getMessage();
            if (message.startsWith(".")) {
                event.setCancelled(true);
                message = message.replace(".", "");
                final Command commandFromMessage = CommandManager.getCommandFromMessage(message);
                final String[] args = message.split(" ");
                commandFromMessage.runCommand(args);
            }
        }
    }
}
