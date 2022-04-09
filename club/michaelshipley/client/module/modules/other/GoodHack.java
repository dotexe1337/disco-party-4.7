package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C0APacketAnimation;

@Mod(displayName = "Good Hack")
public class GoodHack extends Module {
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(!ClientUtils.player().isSwingInProgress) {
			float yaw = (float) (Math.random() * 360.0);
	        event.setYaw(yaw);
	        ClientUtils.player().renderYawOffset = yaw;
	        event.setPitch(180.0f);
		}
	}
	
}
