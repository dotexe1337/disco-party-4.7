package club.michaelshipley.client.module.modules.fight.antibot;

import java.util.ArrayList;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Watchdog extends AntibotMode {

	  public static Minecraft mc = ClientUtils.mc();
	  public static ArrayList<Entity> bots = new ArrayList<Entity>();
	
	public Watchdog(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
			for (Object o : mc.theWorld.playerEntities) {
        		EntityPlayer entityPlayer = (EntityPlayer) o;
        		if(entityPlayer != null && entityPlayer != mc.thePlayer && !bots.contains(entityPlayer)){
        			if (entityPlayer.getDisplayName().getFormattedText().equalsIgnoreCase(entityPlayer.getName() + "\247r") && !mc.thePlayer.getDisplayName().getFormattedText().equalsIgnoreCase(mc.thePlayer.getName() + "\247r")) {
						ClientUtils.sendMessage("Antibot - " + entityPlayer.getName() + " §7has been removed");
						bots.add(entityPlayer);
						mc.theWorld.removeEntity(entityPlayer);
            		}
        		}
			}
		}
		return true;
	}
	
}
