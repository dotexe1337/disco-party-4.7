package club.michaelshipley.client.module.modules.move.speed;

import java.util.List;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

public class ColdNetwork extends SpeedMode {
	
    private double moveSpeed;
    private double lastDist;
    public static int stage;
	
	public ColdNetwork(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            ColdNetwork.stage = 1;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
			if(!ModuleManager.getModule("Fly").isEnabled()) {
		        if (ClientUtils.mc().thePlayer.moveForward != 0.0f || ClientUtils.mc().thePlayer.moveStrafing != 0.0f) {
		            if (ClientUtils.mc().thePlayer.onGround) {
		                Bypasshop.setSpeed(2);
		                ClientUtils.mc().thePlayer.motionY = 0.1;
		            }
		            else {
		                Bypasshop.setSpeed((float)Math.sqrt(ClientUtils.mc().thePlayer.motionX * ClientUtils.mc().thePlayer.motionX + ClientUtils.mc().thePlayer.motionZ * ClientUtils.mc().thePlayer.motionZ));
		            }
		        }
		        else {
		            ClientUtils.mc().thePlayer.motionX = 0.0;
		            ClientUtils.mc().thePlayer.motionZ = 0.0;
		        }
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
