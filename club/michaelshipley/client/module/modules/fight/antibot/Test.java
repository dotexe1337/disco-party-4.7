package club.michaelshipley.client.module.modules.fight.antibot;

import java.util.ArrayList;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class Test extends AntibotMode {
	
	public Test(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
			for(Object o: ClientUtils.world().loadedEntityList) {
				Entity e = (Entity) o;
				if(e instanceof EntityPlayer) {
					EntityPlayer elb = (EntityPlayer) e;
					if(elb.isInvisible() && !elb.isPotionActive(Potion.invisibility)) {
						entities.add(e);
					}
				}
			}
		}
		return true;
	}
	
}
