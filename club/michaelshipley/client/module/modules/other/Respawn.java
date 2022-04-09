// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
@Mod
public class Respawn extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.POST && !ClientUtils.player().isEntityAlive()) {
            ClientUtils.player().respawnPlayer();
        }
    }
}
