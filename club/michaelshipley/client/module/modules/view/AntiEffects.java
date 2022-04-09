// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

@Mod(displayName = "Anti-Effects", shown = false)
public class AntiEffects extends Module
{
    private static final int NAUSEA_ID = 9;
    @Option.Op
    private boolean nausea;
    @Option.Op
    private boolean blindness;
    
    public AntiEffects() {
        this.nausea = true;
        this.blindness = true;
    }
    
    @EventTarget
    private void onTick(final UpdateEvent event) {
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.nausea) {
            ClientUtils.player().removePotionEffect(NAUSEA_ID);
        }
        if (event.getState() == Event.State.POST && ClientUtils.player().isPotionActive(9) && this.blindness) {
            ClientUtils.player().removePotionEffect(15);
        }
    }
    
    public boolean isBlindness() {
        return this.blindness;
    }
}
