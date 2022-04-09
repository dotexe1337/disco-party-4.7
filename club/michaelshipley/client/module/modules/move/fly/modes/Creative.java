package club.michaelshipley.client.module.modules.move.fly.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.fly.FlyMode;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Creative extends FlyMode {

	public Creative(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
			ClientUtils.player().capabilities.isFlying = true;
		}
		return true;
	}
	
	@Override
	public boolean disable() {
		if(super.disable()) {
			ClientUtils.player().capabilities.isFlying = false;
		}
		return true;
	}
	
}
