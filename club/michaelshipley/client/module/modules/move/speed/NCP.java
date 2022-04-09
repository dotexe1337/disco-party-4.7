package club.michaelshipley.client.module.modules.move.speed;

import java.util.List;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.MathUtils;

public class NCP extends SpeedMode
{
    private double moveSpeed;
    private double lastDist;
    public static int stage;
   
    public NCP(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
   
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            NCP.stage = 1;
        }
        return true;
    }
   
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            if (ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.4, 3)) {
                event.setY(ClientUtils.player().motionY = 0.31);
            }
            else if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.71, 3)) {
                event.setY(ClientUtils.player().motionY = 0.04);
            }
            else if (MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.75, 3)) {
                event.setY(ClientUtils.player().motionY = -0.1);
            }
            List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, -0.56, 0.0));
            if (collidingList.size() > 0 && MathUtils.roundToPlace(ClientUtils.player().posY - (int)ClientUtils.player().posY, 3) == MathUtils.roundToPlace(0.55, 3)) {
                event.setY(ClientUtils.player().motionX = -0.14);
            }
            if (NCP.stage == 1 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                this.moveSpeed = 1.0 * Speed.getBaseMoveSpeed() - 0.01;
            }
            else if (NCP.stage == 2 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                event.setY(ClientUtils.player().motionY = 0.4);
                this.moveSpeed *= 2.0;
            }
            else if (NCP.stage == 3) {
                final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if ((collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) && NCP.stage > 0) {
                    if (1.35 * Speed.getBaseMoveSpeed() - 0.01 > this.moveSpeed) {
                        NCP.stage = 0;
                    }
                    else {
                        NCP.stage = ((ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) ? 1 : 0);
                    }
                }
                this.moveSpeed = this.lastDist - this.lastDist / 200.0;
            }
            if (NCP.stage > 8) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed());
            if (NCP.stage > 0) {
                ClientUtils.setMoveSpeed(event, this.moveSpeed);
            }
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                ++NCP.stage;
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