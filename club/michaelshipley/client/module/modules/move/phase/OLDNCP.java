package club.michaelshipley.client.module.modules.move.phase;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Phase;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.BoundingBoxEvent;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.Timer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;

public class OLDNCP extends PhaseMode
{
    
    public OLDNCP(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        return super.enable();
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
    	final Phase phaseModule = (Phase)this.getModule();
    	if(phaseModule.isInsideBlock()){
    	event.setY(ClientUtils.player().motionY = 0.0);
    	ClientUtils.setMoveSpeed(event, 0.2);
    	}
        return true;
    }  
    
    @Override
    public boolean onSetBoundingbox(final BoundingBoxEvent event){
    	final Phase phaseModule = (Phase)this.getModule();
    	if (phaseModule.isInsideBlock()) {
    		event.setBoundingBox(null);
    	}
    	return true;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState().equals(Event.State.POST)) {
            double multiplier = 0.3;
            final double mx = Math.cos(Math.toRadians(ClientUtils.player().rotationYaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(ClientUtils.player().rotationYaw + 90.0f));
            final double x = ClientUtils.player().movementInput.moveForward * multiplier * mx + ClientUtils.player().movementInput.moveStrafe * multiplier * mz;
            final double z = ClientUtils.player().movementInput.moveForward * multiplier * mz - ClientUtils.player().movementInput.moveStrafe * multiplier * mx;
        	final Phase phaseModule = (Phase)this.getModule();
            if (ClientUtils.player().isCollidedHorizontally && !ClientUtils.player().isOnLadder() && !phaseModule.isInsideBlock()) {
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX + x, ClientUtils.player().posY, ClientUtils.player().posZ + z, false));
                for (int i = 1; i < 10; ++i) {
                    ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, 8.988465674311579E307, ClientUtils.player().posZ, false));
                }
                ClientUtils.player().setPosition(ClientUtils.player().posX + x, ClientUtils.player().posY, ClientUtils.player().posZ + z);
            }
        }
        return true;
    }   
}
