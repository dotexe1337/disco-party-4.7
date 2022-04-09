package club.michaelshipley.client.module.modules.move.fly.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.fly.FlyMode;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Watchdog extends FlyMode {
	
	public Watchdog(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
		      ClientUtils.mc().thePlayer.motionY = 0.001D;
		      if ((ClientUtils.mc().thePlayer.ticksExisted % 2 == 0) && (!ClientUtils.mc().thePlayer.onGround)) {
		        ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.mc().thePlayer.posX, ClientUtils.mc().thePlayer.posY + 0.001D, ClientUtils.mc().thePlayer.posZ, ClientUtils.mc().thePlayer.onGround));
		      }
		}
		return true;
	}

}
