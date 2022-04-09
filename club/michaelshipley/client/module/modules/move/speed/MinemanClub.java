package club.michaelshipley.client.module.modules.move.speed;

import java.util.List;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.Event.State;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.LiquidUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class MinemanClub extends SpeedMode {
	
	public MinemanClub(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
    @Override
    public boolean enable() {
    	
        return true;
    }
    
    @Override
    public boolean onUpdate(UpdateEvent event) {
        if(super.onUpdate(event)) {
            if(event.getState() == State.PRE) {
                if(ClientUtils.mc().thePlayer.movementInput.moveForward == 0.0F && ClientUtils.mc().thePlayer.movementInput.moveStrafe == 0.0F) {
                    EntityPlayerSP var21 = ClientUtils.mc().thePlayer;
                    EntityPlayerSP var22 = ClientUtils.mc().thePlayer;
                    double var23 = 0.0D;
                    var21.motionX = 0.0D;
                    var22.motionZ = 0.0D;
                }
                if ((ClientUtils.mc().thePlayer.onGround || ClientUtils.mc().thePlayer.isCollidedVertically) && ClientUtils.mc().thePlayer.fallDistance == 0) {
                    Bypasshop.setSpeed(0.2);
                    ClientUtils.mc().thePlayer.motionY = 0.1D;
                    Timer var1000 = ClientUtils.mc().timer;
                    Timer.timerSpeed = ClientUtils.mc().thePlayer.ticksExisted % 2 == 0?2.05F:1.35F;
                    return true;
                }
            }
        }
        return true;
    }
	
}
