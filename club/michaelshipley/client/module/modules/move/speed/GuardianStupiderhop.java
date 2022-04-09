package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class GuardianStupiderhop extends SpeedMode {
	
	public GuardianStupiderhop(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
	        Minecraft mc = Minecraft.getMinecraft();
	        if (ClientUtils.mc().thePlayer.moveForward != 0.0f || ClientUtils.mc().thePlayer.moveStrafing != 0.0f) {
	            if (ClientUtils.mc().thePlayer.onGround) {
	                mc.thePlayer.motionY = 0.875;
	                Bypasshop.setSpeed(4.5);
		            mc.timer.timerSpeed = 1.0F;
		            mc.timer.updateTimer();
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
		return true;
	}
	
}
