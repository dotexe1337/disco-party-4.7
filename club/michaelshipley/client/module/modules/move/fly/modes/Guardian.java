package club.michaelshipley.client.module.modules.move.fly.modes;

import java.util.HashMap;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Fly;
import club.michaelshipley.client.module.modules.move.fly.FlyMode;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.timer.Timer;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class Guardian extends FlyMode {
	
	public Guardian(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	Timer timer = new Timer();
	Timer timer2 = new Timer();

    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            if (event.getState() == Event.State.PRE) {
                final int xd = 0;
                if (ClientUtils.mc().gameSettings.keyBindJump.pressed) {
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionY = 3;
                }
                if (ClientUtils.mc().gameSettings.keyBindSneak.pressed) {
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionY = -3;
                }
                if (!ClientUtils.mc().gameSettings.keyBindJump.pressed && !ClientUtils.mc().gameSettings.keyBindSneak.pressed && isMoving(ClientUtils.player())) {
                	ClientUtils.mc().timer.timerSpeed = 2.0F;
                }
            }
	        
        }
		return true;
        }
    
    public static boolean isMoving(final Entity ent) {
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.moveForward == 0.0f) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.moveStrafing == 0.0f) {
                return false;
            }
        }
        return true;
    }
    
    public static void setSpeed(final double speed) {
        ClientUtils.mc();
        Minecraft.thePlayer.motionX = -MathHelper.sin(getDirection()) * speed;
        ClientUtils.mc();
        Minecraft.thePlayer.motionZ = MathHelper.cos(getDirection()) * speed;
    }
    
    public static float getDirection() {
        ClientUtils.mc();
        float yaw = Minecraft.thePlayer.rotationYawHead;
        ClientUtils.mc();
        final float forward = Minecraft.thePlayer.moveForward;
        ClientUtils.mc();
        final float strafe = Minecraft.thePlayer.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);
        if (strafe < 0.0f) {
            yaw += ((forward < 0.0f) ? -45 : ((forward == 0.0f) ? 90 : 45));
        }
        if (strafe > 0.0f) {
            yaw -= ((forward < 0.0f) ? -45 : ((forward == 0.0f) ? 90 : 45));
        }
        return yaw * 0.017453292f;
    }
    
    int stage = 1;

    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            //ClientUtils.setMoveSpeed(event, 0.2);
        	
	    }
        return true;
    }
    
    @Override
    public boolean disable() {
    	if(super.disable()) {
    		net.minecraft.util.Timer.timerSpeed = 1.0f;
    	}
    	return false;
    }

}
