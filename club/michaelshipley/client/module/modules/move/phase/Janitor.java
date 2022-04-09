package club.michaelshipley.client.module.modules.move.phase;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Phase;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.BoundingBoxEvent;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.PushOutOfBlocksEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;

public class Janitor extends PhaseMode
{
    
    public Janitor(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
    	if(super.onUpdate(event)) {
            if (event.getState().equals(Event.State.PRE) && ((Phase)this.getModule()).isInsideBlock() && ClientUtils.player().isSneaking()) {
                final float yaw = Minecraft.thePlayer.rotationYaw;
                Minecraft.thePlayer.boundingBox.offsetAndUpdate(0.9 * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0, 0.9 * Math.sin(Math.toRadians(yaw + 90.0f)));
            }
    	}
    	return true;
    }
    
    @Override
    public boolean onPushOutOfBlocks(PushOutOfBlocksEvent event) {
    	if(super.onPushOutOfBlocks(event)) {
    		event.setCancelled(true);
    	}
    	return true;
    }
    
    @Override
    public boolean onSetBoundingbox(final BoundingBoxEvent event) {
        if (super.onSetBoundingbox(event) && event.getBoundingBox() != null && event.getBoundingBox().maxY > ClientUtils.player().boundingBox.minY && ClientUtils.player().isSneaking()) {
            event.setCancelled(true);
        }
        return true;
    }
	
}
