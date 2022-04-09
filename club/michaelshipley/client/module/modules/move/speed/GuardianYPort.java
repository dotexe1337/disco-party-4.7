package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.util.MathHelper;

public class GuardianYPort extends SpeedMode {

	public GuardianYPort(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
    @Override
    public boolean onUpdate(UpdateEvent event) {
        if(super.onUpdate(event)) {
        	if(ClientUtils.player().moveForward == 0.0F && ClientUtils.player().moveStrafing == 0.0F) {
        		return false;
        	}
            if ((ClientUtils.mc().thePlayer.movementInput.moveForward == 0.0F) && (ClientUtils.mc().thePlayer.movementInput.moveStrafe == 0.0F))
            {
              ClientUtils.mc().thePlayer.motionX = (ClientUtils.mc().thePlayer.motionZ = 0.0D);
            }
            if (ClientUtils.mc().thePlayer.onGround)
            {
              setSpeed(0.5299999713897705D);
              ClientUtils.mc().thePlayer.motionY = 0.2D;
              ClientUtils.mc().timer.timerSpeed = (ClientUtils.mc().thePlayer.ticksExisted % 2 == 0 ? 2.05F : 1.35F);
            }
            else
            {
              setSpeed(0.3499999940395355D);
              ClientUtils.mc().thePlayer.motionY = -1.0D;
            }
        }
        return true;
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
