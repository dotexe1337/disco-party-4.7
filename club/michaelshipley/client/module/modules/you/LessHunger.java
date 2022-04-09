package club.michaelshipley.client.module.modules.you;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.PacketSendEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

@Mod(displayName = "Less Hunger")
public class LessHunger extends Module {
	
    @EventTarget
    public void onSendPacket(final PacketSendEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            final Minecraft mc = ClientUtils.mc();
            if (Minecraft.thePlayer.fallDistance <= 2.0f) {
                final C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
                packet.field_149474_g = false;
            }
            setSuffix("Guardian");
        }
    }
	
}
