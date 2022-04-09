package club.michaelshipley.client.module.modules.fight.regen.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.Regen;
import club.michaelshipley.client.module.modules.fight.regen.RegenMode;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class Old extends RegenMode {

	public Old(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
	        event.setCancelled(true);
	        if (((Regen)this.getModule()).potion) {
	            if (!ClientUtils.player().isUsingItem() && ClientUtils.player().isPotionActive(Potion.regeneration) && ClientUtils.player().getActivePotionEffect(Potion.regeneration).getDuration() > 0 && ClientUtils.player().getHealth() <= ((Regen)this.getModule()).health * 2.0 && (ClientUtils.player().isCollidedVertically || ((Regen)this.getModule()).isInsideBlock()) && event.getState().equals(Event.State.POST)) {
	                for (int i = 0; i < ((Regen)this.getModule()).potionPackets; ++i) {
	                    ClientUtils.player().getActivePotionEffect(Potion.regeneration).deincrementDuration();
	                    ClientUtils.packet(new C03PacketPlayer(true));
	                }
	            }
	        }
	        else if (ClientUtils.player().getHealth() <= ((Regen)this.getModule()).health * 2.0 && (ClientUtils.player().isCollidedVertically || ((Regen)this.getModule()).isInsideBlock()) && event.getState().equals(Event.State.POST)) {
	            for (int i = 0; i < ((Regen)this.getModule()).packets; ++i) {
	                ClientUtils.packet(new C03PacketPlayer(true));
	            }
	        }
		}
		return false;
	}
	
}
