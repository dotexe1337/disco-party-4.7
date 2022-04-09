package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

@Mod(displayName = "Air Jump")
public class AirJump extends Module {
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
        if (ClientUtils.movementInput().jump) {
            ClientUtils.player().motionY = 0.4;
        }
	}
	
	@Override
	public void disable() {
		super.disable();
		ClientUtils.mc().thePlayer.capabilities.isFlying = false;
		setSpeed(0.5D);
	}
	
    public static void setSpeed(double speed) {
        ClientUtils.mc();
        ClientUtils.mc().thePlayer.motionX = (double)(-MathHelper.sin(getDirection())) * speed;
        ClientUtils.mc();
        ClientUtils.mc().thePlayer.motionZ = (double)MathHelper.cos(getDirection()) * speed;
    }
    
    public static float getDirection() {
        ClientUtils.mc();
        float yaw = ClientUtils.mc().thePlayer.rotationYawHead;
        ClientUtils.mc();
        float forward = ClientUtils.mc().thePlayer.moveForward;
        ClientUtils.mc();
        float strafe = ClientUtils.mc().thePlayer.moveStrafing;
        yaw += (float)(forward < 0.0F?180:0);
        if(strafe < 0.0F) {
           yaw += (float)(forward < 0.0F?-45:(forward == 0.0F?90:45));
        }

        if(strafe > 0.0F) {
           yaw -= (float)(forward < 0.0F?-45:(forward == 0.0F?90:45));
        }

        return yaw * 0.017453292F;
     }
	
}
