package club.michaelshipley.client.module.modules.fight.regen.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.Regen;
import club.michaelshipley.client.module.modules.fight.regen.RegenMode;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class Guardian extends RegenMode {

	public Guardian(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
		    if (((Regen)this.getModule()).potion)
		    {
		      if ((ClientUtils.player().getActivePotionEffect(Potion.regeneration) != null) && (ClientUtils.player().getActivePotionEffect(Potion.regeneration).getDuration() > 0) && (ClientUtils.player().getHealth() <= ((Regen)this.getModule()).health * 2.0D) && ((ClientUtils.player().isCollidedVertically) || (((Regen)this.getModule()).isInsideBlock())) && (event.getState().equals(Event.State.POST))) {
		        ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, ClientUtils.mc().thePlayer.getCurrentEquippedItem(), 1.0f, 1.0f, 1.0f));
		        for (int i = 0; i < ((Regen)this.getModule()).potionPackets; i++)
		        {
		          ClientUtils.player().getActivePotionEffect(Potion.regeneration).deincrementDuration();
		          ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(ClientUtils.mc().thePlayer.rotationYaw, ClientUtils.mc().thePlayer.rotationPitch, ClientUtils.mc().thePlayer.onGround));
		        }
		      }
		    }
		    else if (event.getState() == Event.State.POST && ClientUtils.mc().thePlayer.getHealth() < ((Regen)this.getModule()).health * 2.0 && ClientUtils.mc().thePlayer.isCollidedVertically) {
		        ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, ClientUtils.mc().thePlayer.getCurrentEquippedItem(), 1.0f, 1.0f, 1.0f));
		        for (int i = 0; i < ((Regen)this.getModule()).packets; ++i) {
		            ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(ClientUtils.mc().thePlayer.rotationYaw, ClientUtils.mc().thePlayer.rotationPitch, ClientUtils.mc().thePlayer.onGround));
		        }
		    }
		}
		return false;
	}
	
}
