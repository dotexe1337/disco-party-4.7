package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.Timer;
import net.minecraft.client.entity.EntityPlayerSP;

public class AACBhop extends SpeedMode {
	
    public AACBhop(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    private boolean wasinairaac;
    private int ticks = 0;
    private Timer time = new Timer();
    
    @Override
    public boolean enable() {
        this.ticks = 10;
        return true;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
    	if(super.onUpdate(event)) {
            if (!ClientUtils.mc().gameSettings.keyBindJump.pressed) {
           	 
                if (ClientUtils.mc().thePlayer.movementInput.moveStrafe != 0.0F) {
                    return true;
                }
                ClientUtils.mc().thePlayer.setSprinting(true);
                if (ClientUtils.mc().thePlayer.hurtTime != 0) {
                    return true;
                }

                setSpeed(Math
                        .sqrt(ClientUtils.mc().thePlayer.motionX * ClientUtils.mc().thePlayer.motionX + ClientUtils.mc().thePlayer.motionZ * ClientUtils.mc().thePlayer.motionZ)
                        * 1.0);

                if (ClientUtils.mc().thePlayer.onGround) {
                    ClientUtils.mc().thePlayer.jump();
                    ClientUtils.mc().thePlayer.motionY = 0.386;
                    ClientUtils.mc().thePlayer.motionX *= 1.008;
                    ClientUtils.mc().thePlayer.motionZ *= 1.008;
                } else {
                    ClientUtils.mc().thePlayer.jumpMovementFactor = 0.0263F;
                }
            } else {
                if (!ClientUtils.mc().thePlayer.onGround && time.delay(1L) && !wasinairaac) {
                    wasinairaac = true;
                    ClientUtils.mc().thePlayer.motionX *= -1;
                    ClientUtils.mc().thePlayer.motionZ *= -1;
                } else if (ClientUtils.mc().thePlayer.onGround) {
                    wasinairaac = false;
                }
            }
    	}
        return true;
    }
	
    
    public void setSpeed(double speed) {
        EntityPlayerSP player = ClientUtils.mc().thePlayer;
        double yaw = (double) player.rotationYaw;
        boolean isMoving = player.moveForward != 0.0F || player.moveStrafing != 0.0F;
        boolean isMovingForward = player.moveForward > 0.0F;
        boolean isMovingBackward = player.moveForward < 0.0F;
        boolean isMovingRight = player.moveStrafing > 0.0F;
        boolean isMovingLeft = player.moveStrafing < 0.0F;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0D;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0D;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45.0D;
            } else if (isMovingForward) {
                yaw -= 45.0D;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0D;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0D;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0D;
            } else if (isMovingBackward) {
                yaw -= 135.0D;
            }
 
            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
 
    }
}
