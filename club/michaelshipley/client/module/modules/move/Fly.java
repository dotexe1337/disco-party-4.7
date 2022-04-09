// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.move.fly.modes.Creative;
import club.michaelshipley.client.module.modules.move.fly.modes.Custom;
import club.michaelshipley.client.module.modules.move.fly.modes.Guardian;
import club.michaelshipley.client.module.modules.move.fly.modes.Gwen;
import club.michaelshipley.client.module.modules.move.fly.modes.Watchdog;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
@Mod
public class Fly extends Module
{
	
	public Watchdog hypixel;
	public Gwen gwen;
	public Custom custom;
	public Creative creative;
	public Guardian guardian;
	
    @Option.Op(min = 0.0, max = 9.0, increment = 0.01)
	public static float speed;
    
    public Fly() {
    	custom = new Custom("Custom", true, this);
    	creative = new Creative("Creative", false, this);
    	hypixel = new Watchdog("Watchdog", false, this);
    	gwen = new Gwen("Gwen", false, this);
    	guardian = new Guardian("Guardian", false, this);
        this.speed = 0.8f;
        this.setKeybind(Keyboard.KEY_M);
    }
    
    @Override
    public void preInitialize() {
    	OptionManager.getOptionList().add(custom);
    	OptionManager.getOptionList().add(creative);
    	OptionManager.getOptionList().add(hypixel);
    	OptionManager.getOptionList().add(gwen);
    	OptionManager.getOptionList().add(guardian);
    	this.updateSuffix();
    	super.preInitialize();
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
    	hypixel.onUpdate(event);
    	gwen.onUpdate(event);
    	custom.onUpdate(event);
    	creative.onUpdate(event);
    	guardian.onUpdate(event);
    }
    
    @EventTarget
    public void onTick(final TickEvent event) {
    	this.updateSuffix();
    }
    
    @Override
    public void enable() {
    	guardian.enable();
    	super.enable();
    }
    
    @Override
    public void disable() {
    	creative.disable();
    	guardian.disable();
    	gwen.disable();
    	ClientUtils.player().capabilities.setFlySpeed(0.05f);
    	ClientUtils.player().capabilities.isFlying = false;
    	super.disable();
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if(this.custom.getValue() || this.creative.getValue()) {
        	ClientUtils.setMoveSpeed(event, speed / 2.5f);
        	ClientUtils.player().capabilities.setFlySpeed((float)speed / 2.5f);
        }
        guardian.onMove(event);
    }
    
    private void updateSuffix() {
        if (this.custom.getValue()) {
            this.setSuffix("Custom");
        }
        else if (this.creative.getValue()) {
            this.setSuffix("Creative");
        }
        else if (this.hypixel.getValue()) {
            this.setSuffix("Hypixel");
        }
        else if (this.gwen.getValue()) {
            this.setSuffix("Gwen");
        }
        else if (this.guardian.getValue()) {
            this.setSuffix("Guardian");
        }
    }
}
