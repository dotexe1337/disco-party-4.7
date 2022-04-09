package club.michaelshipley.client.module.modules.move.speed;

import java.util.List;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

public class Stupidhop extends SpeedMode {

	public Stupidhop(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    
    @Override
    public boolean enable() {
    	if(super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            this.stage = 1;
    	}
    	return true;
    }
	
	@Override
	public boolean onMove(MoveEvent event) {
		if(super.onMove(event)) {
            if (ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            if (Stupiderhop.stage == 1 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
            	this.moveSpeed = 3 + Speed.getBaseMoveSpeed() - 0.01;
            }
            else if (Stupiderhop.stage == 2 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                event.setY(ClientUtils.player().motionY = 0.4);
                //this.moveSpeed *= 2.149;
            }
            else if (Stupiderhop.stage == 3) {
                final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                //this.moveSpeed = this.lastDist - difference;
            }
            else {
                final List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if ((collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) && Stupiderhop.stage > 0) {
                    if (1.35 * Speed.getBaseMoveSpeed() - 0.01 > this.moveSpeed) {
                        Stupiderhop.stage = 0;
                    }
                    else {
                        Stupiderhop.stage = ((ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) ? 1 : 0);
                    }
                }
                //this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed());
            if (Stupiderhop.stage > 0) {
                ClientUtils.setMoveSpeed(event, this.moveSpeed);
            }
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                ++Stupiderhop.stage;
            }
		}
		return true;
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
