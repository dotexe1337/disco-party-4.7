package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class Mineverge extends SpeedMode {
	
	public Mineverge(String name, boolean value, Module module) {
		super(name, value, module);
	}

	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
            if(ClientUtils.player().moveForward == 0.0 && ClientUtils.player().moveStrafing == 0.0) {
            	return false;
            }
	        /*if(ClientUtils.mc().thePlayer.ticksExisted % 2 == 0) {
	            setSpeed(0.25);
	        }*/
	        /*if(ClientUtils.mc().thePlayer.ticksExisted % 10 == 0) {
	        	setSpeed(-0.25);
	        }*/
            Timer.timerSpeed = 4.0F;
		}
		return true;
	}
	
	@Override
	public boolean disable() {
		if(super.disable()) {
			setSpeed(0.5);
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
