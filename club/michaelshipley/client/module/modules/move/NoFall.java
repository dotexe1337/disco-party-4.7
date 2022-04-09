// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
@Mod(displayName = "No Fall")
public class NoFall extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
    	if(ClientUtils.mc().thePlayer.fallDistance > 3) {
    		ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    	}
    }
}
