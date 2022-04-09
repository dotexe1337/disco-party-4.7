package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

@Mod
public class Timer extends Module {
	
	@Option.Op(min = 1, max = 10, increment = 1)
	public float speed;
	
	public Timer() {
		this.speed = 2.0f;
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent e) {
		ClientUtils.mc().timer.timerSpeed = speed;
	}
	
	@Override
	public void disable() {
		ClientUtils.mc().timer.timerSpeed = 1.0F;
		super.disable();
	}
	
	
}
