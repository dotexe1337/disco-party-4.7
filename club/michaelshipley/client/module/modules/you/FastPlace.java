// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.you;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
@Mod(displayName = "Fast Place")
public class FastPlace extends Module
{
    @Option.Op(name = "Half Speed")
    private boolean halfSpeed;
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            ClientUtils.mc().rightClickDelayTimer = Math.min(ClientUtils.mc().rightClickDelayTimer, this.halfSpeed ? 2 : 1);
        }
    }
}
