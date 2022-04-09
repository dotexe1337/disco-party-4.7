package club.michaelshipley.client.module.modules.move.fly.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.fly.FlyMode;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class Gwen extends FlyMode {
	
	public Gwen(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            if(ClientUtils.player().moveForward == 0.0 && ClientUtils.player().moveStrafing == 0.0) {
            	return false;
            }
            if (ClientUtils.movementInput().jump) {
                ClientUtils.player().motionY = 1;
                if(ClientUtils.mc().thePlayer.ticksExisted % 10 == 0) {
                        ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.mc().thePlayer.posX, ClientUtils.mc().thePlayer.motionY, ClientUtils.mc().thePlayer.posZ, false));
                }
            }
            else if (ClientUtils.movementInput().sneak) {
                ClientUtils.player().motionY = -1;
                if(ClientUtils.mc().thePlayer.ticksExisted % 10 == 0) {
                        ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.mc().thePlayer.posX, ClientUtils.mc().thePlayer.motionY, ClientUtils.mc().thePlayer.posZ, false));
                }
            }
            else {
                ClientUtils.player().motionY = 0.0;
            }
            ClientUtils.mc().thePlayer.capabilities.isFlying = true;
            if(ClientUtils.mc().thePlayer.ticksExisted % 2 == 0) {
                setSpeed(0.7);
            }
            if(ClientUtils.mc().thePlayer.ticksExisted % 10 == 0) {
                 ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.mc().thePlayer.posX, ClientUtils.mc().thePlayer.motionY, ClientUtils.mc().thePlayer.posZ, false));
                 setSpeed(-0.5);
            }
        }
        return true;
    }
	
	@Override
	public boolean disable() {
		if(super.disable()) {
			ClientUtils.mc().thePlayer.capabilities.isFlying = false;
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
