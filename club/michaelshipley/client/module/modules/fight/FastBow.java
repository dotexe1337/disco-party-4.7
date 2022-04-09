package club.michaelshipley.client.module.modules.fight;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.fight.fastbow.modes.Packet;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.event.ClickEvent;

@Mod (displayName = "Fast Bow")
public class FastBow extends Module {
	
	@Option.Op
	public static boolean aimbot;
	
	private Packet packet;
	
    public FastBow() {
        packet = new Packet("Packet", true, this);
        aimbot = true;
    }
    
    @Override
    public void preInitialize() {
    	OptionManager.getOptionList().add(packet);
    	this.updateSuffix();
    	super.preInitialize();
    }
    
    @Override
    public void enable() {
        super.enable();
    }
    
    @Override
    public void disable() {
		 ClientUtils.mc().timer.timerSpeed = 1.0F;
		 ClientUtils.mc().rightClickDelayTimer = 4;
    	super.disable();
    }
    
    @EventTarget
    public void onTick(TickEvent event) {
    	this.updateSuffix();
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
    	
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
    	packet.onUpdate(event);
    }
    
    public void updateSuffix() {
    	if(packet.getValue()) {
    		this.setSuffix("Packet");
    	}
    }
	
}
