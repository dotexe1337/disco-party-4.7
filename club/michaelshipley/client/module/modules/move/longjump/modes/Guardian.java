package club.michaelshipley.client.module.modules.move.longjump.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.move.longjump.LongJumpMode;
import club.michaelshipley.client.module.modules.move.speed.Bypasshop;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

public class Guardian extends LongJumpMode {
	
	public Guardian(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
			if(!ModuleManager.getModule("Fly").isEnabled()) {
		        if (ClientUtils.mc().thePlayer.moveForward != 0.0f || ClientUtils.mc().thePlayer.moveStrafing != 0.0f) {
		            if (ClientUtils.mc().thePlayer.onGround) {
		                Bypasshop.setSpeed(7.5);
		                ClientUtils.mc().thePlayer.motionY = 0.4255;
		            }
		            else {
		                Bypasshop.setSpeed((float)Math.sqrt(ClientUtils.mc().thePlayer.motionX * ClientUtils.mc().thePlayer.motionX + ClientUtils.mc().thePlayer.motionZ * ClientUtils.mc().thePlayer.motionZ));
		            }
		        }
		        else {
		            ClientUtils.mc().thePlayer.motionX = 0.0;
		            ClientUtils.mc().thePlayer.motionZ = 0.0;
		        }
			}
		}
		return true;
	}

}
