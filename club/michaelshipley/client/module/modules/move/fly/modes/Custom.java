package club.michaelshipley.client.module.modules.move.fly.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Fly;
import club.michaelshipley.client.module.modules.move.fly.FlyMode;
import club.michaelshipley.event.Event.State;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;

public class Custom extends FlyMode {

	public Custom(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event) && event.getState() == State.PRE) {
            if (ClientUtils.movementInput().jump && !ClientUtils.movementInput().sneak) {
                ClientUtils.player().motionY = ((Fly)this.getModule()).speed;
            }
            else if (ClientUtils.movementInput().sneak && !ClientUtils.movementInput().jump) {
                ClientUtils.player().motionY = -((Fly)this.getModule()).speed;
            }
            else {
                ClientUtils.player().motionY = 0.0;
            }
		}
		return true;
	}

}
