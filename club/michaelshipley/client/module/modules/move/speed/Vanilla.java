// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.utils.ClientUtils;

public class Vanilla extends SpeedMode
{
    public Vanilla(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event) && ClientUtils.player().onGround) {
            ClientUtils.setMoveSpeed(event, ((Speed)this.getModule()).speed);
        }
        return true;
    }
}
