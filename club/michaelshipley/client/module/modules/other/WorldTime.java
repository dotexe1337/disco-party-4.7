// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.PacketReceiveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
@Mod(displayName = "World Time")
public class WorldTime extends Module
{
    @Option.Op(min = 0.0, max = 24000.0, increment = 250.0)
    private double time;
    
    public WorldTime() {
        this.time = 9000.0;
    }
    
    @EventTarget
    private void onPacketRecieve(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.POST) {
            ClientUtils.world().setWorldTime((long)this.time);
        }
    }
}
