// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.move.longjump.modes.Guardian;
import club.michaelshipley.client.module.modules.move.longjump.modes.Normal;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;
@Mod
public class LongJump extends Module
{
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    @Option.Op(increment = 1.0, min = 4.0, max = 24.0)
    private double boost;
    
    private Normal normal;
    private Guardian guardian;
    
    public LongJump() {
        this.boost = 4.0;
        normal = new Normal("Normal", true, this);
        guardian = new Guardian("Guardian", false, this);
    }
    
    @Override
    public void preInitialize() {
    	OptionManager.getOptionList().add(normal);
    	OptionManager.getOptionList().add(guardian);
    	this.updateSuffix();
    	super.preInitialize();
    }
    
    @Override
    public void enable() {
        LongJump.stage = 0;
        super.enable();
    }
    
    @EventTarget
    public void onTick(TickEvent event) {
    	updateSuffix();
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
    	normal.onMove(event);
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
    	normal.onUpdate(event);
    	guardian.onUpdate(event);
    }
    
    public void updateSuffix() {
    	if(normal.getValue()) {
    		this.setSuffix("Normal");
    	}
    	else if(guardian.getValue()) {
    		this.setSuffix("Guardian");
    	}
    }
}
