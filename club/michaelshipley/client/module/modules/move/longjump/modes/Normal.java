package club.michaelshipley.client.module.modules.move.longjump.modes;

import java.util.List;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.LongJump;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.client.module.modules.move.longjump.LongJumpMode;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

public class Normal extends LongJumpMode {
	
    public Normal(String name, boolean value, Module module) {
		super(name, value, module);
	}

	private double moveSpeed;
    private double lastDist;
    public static int stage;
    
    @Override
    public boolean onUpdate(UpdateEvent event) {
    	if(super.onUpdate(event)) {
            if (event.getState() == Event.State.PRE) {
                if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                    final double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
                    final double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
                    this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                }
                else {
                    event.setCancelled(true);
                }
            }
    	}
    	return true;
    }
    
    @Override
    public boolean onMove(MoveEvent event) {
    	if(super.onMove(event)) {
            final boolean setY = false;
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                if (LongJump.stage == 0) {
                    this.moveSpeed = 1.0 + Speed.getBaseMoveSpeed() - 0.05;
                }
                else if (LongJump.stage == 1) {
                    event.setY(ClientUtils.player().motionY = 0.42);
                    this.moveSpeed *= 2.13;
                }
                else if (LongJump.stage == 2) {
                    final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                }
                else {
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
                ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(Speed.getBaseMoveSpeed(), this.moveSpeed));
                final List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                final List collidingList2 = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, -0.4, 0.0));
                if (!ClientUtils.player().isCollidedVertically && (collidingList.size() > 0 || collidingList2.size() > 0)) {
                    event.setY(ClientUtils.player().motionY = -1.0E-4);
                }
                ++LongJump.stage;
            }
            else if (LongJump.stage > 0) {
                this.disable();
            }
    	}
    	return true;
    }
	
}
