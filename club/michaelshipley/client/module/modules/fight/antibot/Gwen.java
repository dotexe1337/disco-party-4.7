package club.michaelshipley.client.module.modules.fight.antibot;

import java.util.ArrayList;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Gwen extends AntibotMode {

	  public static Minecraft mc = ClientUtils.mc();
	  public static ArrayList<String> groundTimes = new ArrayList();
	  public static int timeCap = 3;
	
	public Gwen(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
			for (Object e2 : mc.theWorld.loadedEntityList) {
		          if ((((Entity) e2) != mc.thePlayer))
		          {
		              if (((e2 instanceof EntityPlayer)) && (((Entity)e2).onGround) &&  
		                      (getTimeOnGround((EntityPlayer)e2) < timeCap)) {
		                      TimeOnGroundAdd((EntityPlayer)e2, 1.0D);
		                    }
		          }
			}
		}
		return true;
	}

	  public static boolean isGwenBot(EntityLivingBase e2)
	  {
	    return ((e2 instanceof EntityPlayer)) && (getTimeOnGround((EntityPlayer)e2) < timeCap);
	  }
	  
	  public static double getTimeOnGround(EntityPlayer p2)
	  {
	    String id2 = String.valueOf(p2.getEntityId());
	    for (String s : groundTimes) {
	      if (s.contains(id2))
	      {
	        String[] split = s.split(":");
	        return Double.parseDouble(split[1]);
	      }
	    }
	    return 0.0D;
	  }
	  
	  public static void TimeOnGroundAdd(EntityPlayer p2, double amount)
	  {
	    String id2 = String.valueOf(p2.getEntityId());
	    for (String s : groundTimes) {
	      if (s.contains(id2))
	      {
	        String[] split = s.split(":");
	        double foo = Double.parseDouble(split[1]) + amount;
	        groundTimes.remove(s);
	        groundTimes.add(String.valueOf(String.valueOf(id2)) + ":" + foo);
	        return;
	      }
	    }
	    groundTimes.add(String.valueOf(String.valueOf(id2)) + ":" + amount);
	  }	

}
