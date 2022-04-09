// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.utils.ClientUtils;
@Mod(displayName = "Fast Ladder")
public class FastLadder extends Module
{
    private static final double MAX_LADDER_SPEED = 0.287299999999994;
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        ClientUtils.mc().timer.timerSpeed = 1.0f;
        if (event.getY() > 0.0 && ClientUtils.player().isOnLadder()) {
            event.setY(0.287299999999994);
        }
    }
}
