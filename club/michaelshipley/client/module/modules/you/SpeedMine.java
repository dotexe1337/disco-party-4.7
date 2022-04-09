package club.michaelshipley.client.module.modules.you;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Mod(displayName = "Speed Mine")
public class SpeedMine extends Module {
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
	      ClientUtils.mc().thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, Integer.MAX_VALUE, 0));
	      ClientUtils.mc().playerController.blockHitDelay = Integer.MIN_VALUE;
	}
	
	@Override
	public void disable() {
		super.disable();
	    if (ClientUtils.mc().thePlayer != null) {
	        ClientUtils.mc().thePlayer.removePotionEffect(Potion.digSpeed.id);
	    }
	}
	
}
