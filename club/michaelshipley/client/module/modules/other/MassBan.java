package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.timer.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;

@Mod(displayName = "Mass Ban", shown = false)
public class MassBan extends Module {
	
	@Option.Op(min = 0, max = 500, increment = 10)
	public static double delay;
	@Option.Op
	public static boolean silent;
	Timer timer = new Timer();
	
	static {
		delay = 150;
	}
	
	public void enable() {
		banwave();
		super.enable();
	}
	
	  public void banwave()
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    for (Object o : mc.theWorld.playerEntities) {
	      if ((!(o instanceof EntityPlayerSP)) && ((o instanceof EntityLivingBase)))
	      {
	        EntityLivingBase ent = (EntityLivingBase)o;
	        if ((ent != null) && 
	          (ent.getName() != Minecraft.thePlayer.getName()) && (!FriendManager.isFriend(ent.getName())) && timer.hasReached((int)delay)) {
	          if(silent) {
	        	  Minecraft.thePlayer.sendChatMessage("/ban " + ent.getName() + " -s server rocked by Viper Client");
	          }else {
	        	  Minecraft.thePlayer.sendChatMessage("/ban " + ent.getName() + " server rocked by Viper Client");
	          }
	          timer.reset();
	        }
	      }
	    }
	  }
	
}
