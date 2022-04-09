package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.LiquidUtils;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class CosmicBhop extends SpeedMode {
	
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    
    public CosmicBhop(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            CosmicBhop.stage = 1;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
    	if(super.onMove(event)) {
        	//onMove
        	if(!LiquidUtils.isInLiquid()) {
        	            Timer.timerSpeed = 1.0F;
        	            ClientUtils.mc().thePlayer.isAirBorne = true;
        	            if(!ClientUtils.mc().gameSettings.keyBindForward.pressed && !ClientUtils.mc().gameSettings.keyBindLeft.pressed && !ClientUtils.mc().gameSettings.keyBindRight.pressed && !ClientUtils.mc().gameSettings.keyBindBack.pressed) {
        	               label87: {
        	                  if(ClientUtils.mc().theWorld.getCollidingBoundingBoxes(ClientUtils.mc().thePlayer, ClientUtils.mc().thePlayer.boundingBox.offset(0.0D, ClientUtils.mc().thePlayer.motionY, 0.0D)).size() <= 0) {
        	                     if(!ClientUtils.mc().thePlayer.isCollidedVertically) {
        	                        break label87;
        	                     }
        	                  }

        	                  this.stage = 1;
        	               }
        	            }

        	            label103: {
        	               if(this.stage == 1) {
        	                  label98: {
        	                     if(ClientUtils.mc().thePlayer.moveForward == 0.0F) {
        	                        if(ClientUtils.mc().thePlayer.moveStrafing == 0.0F) {
        	                           break label98;
        	                        }
        	                     }

        	                     this.moveSpeed = (double)0.1 * Speed.getBaseMoveSpeed() - 0.01D;
        	                     break label103;
        	                  }
        	               }

        	               if(this.stage == 2) {
        	                  if(ClientUtils.mc().theWorld.getCollidingBoundingBoxes(ClientUtils.mc().thePlayer, ClientUtils.mc().thePlayer.boundingBox.offset(0.0D, ClientUtils.mc().thePlayer.motionY, 0.0D)).size() < 1) {
        	                     return true;
        	                  }

        	                  ClientUtils.mc().thePlayer.motionY = 0.424D;
        	                  event.setY(0.424D);
        	                  if(ClientUtils.mc().theWorld.getCollidingBoundingBoxes(ClientUtils.mc().thePlayer, ClientUtils.mc().thePlayer.boundingBox.offset(ClientUtils.mc().thePlayer.motionX, ClientUtils.mc().thePlayer.motionY, ClientUtils.mc().thePlayer.motionZ)).size() > 0) {
        	                     return true;
        	                  }
        	                  this.moveSpeed *= (double) 1.52D;
        	               } else if(this.stage == 3) {
        	                  double movementInput = 0.66D * (this.lastDist - Speed.getBaseMoveSpeed());
        	                  this.moveSpeed = this.lastDist - movementInput;
        	               } else {
        	                  label71: {
        	                     if(ClientUtils.mc().theWorld.getCollidingBoundingBoxes(ClientUtils.mc().thePlayer, ClientUtils.mc().thePlayer.boundingBox.offset(0.0D, ClientUtils.mc().thePlayer.motionY, 0.0D)).size() <= 0) {
        	                        if(!ClientUtils.mc().thePlayer.isCollidedVertically) {
        	                           break label71;
        	                        }
        	                     }

        	                     this.stage = 1;
        	                  }

        	                  this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
        	               }
        	            }
        				if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
        					++stage;
        				}

        	            this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed());
        	            MovementInput movementInput1 = ClientUtils.mc().thePlayer.movementInput;
        	            float forward = movementInput1.moveForward;
        	            float strafe = movementInput1.moveStrafe;
        	            float yaw = ClientUtils.mc().thePlayer.rotationYaw;
        	            if(forward == 0.0F && strafe == 0.0F) {
        	               event.setX(0.8D);;
        	               event.setZ(0.8D);;
        	            } else if(forward != 0.0F) {
        	               if(strafe >= 1.0F) {
        	                  yaw += (float)(forward > 0.0F?-45:45);
        	                  strafe = 0.0F;
        	               } else if(strafe <= -1.0F) {
        	                  yaw += (float)(forward > 0.0F?45:-45);
        	                  strafe = 0.0F;
        	               }

        	               if(forward > 0.0F) {
        	                  forward = 1.0F;
        	               } else if(forward < 0.0F) {
        	                  forward = -1.0F;
        	               }
        	            }

        	            double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
        	            double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
        	            double motionX = (double)forward * this.moveSpeed * mx + (double)strafe * this.moveSpeed * mz;
        	            double motionZ = (double)forward * this.moveSpeed * mz - (double)strafe * this.moveSpeed * mx;
        	            event.setX(motionX);
        	            event.setZ(motionZ);
        	         }
    	}
        return false;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            final double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
            final double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        return true;
    }
	
}
