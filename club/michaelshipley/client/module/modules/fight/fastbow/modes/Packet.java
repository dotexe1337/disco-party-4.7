package club.michaelshipley.client.module.modules.fight.fastbow.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.fastbow.FastBowMode;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.Timer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Packet extends FastBowMode {

	public Packet(String name, boolean value, Module module) {
		super(name, value, module);
	}

	  public boolean isShooting;
	  Timer t = new Timer();
    
    @Override
    public boolean onUpdate(UpdateEvent event) {
    	if(super.onUpdate(event)) {
    		if (ClientUtils.mc().thePlayer.inventory.getCurrentItem()!=null && ClientUtils.mc().thePlayer.onGround&& !(ClientUtils.mc().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood)) {
    			if (ClientUtils.mc().gameSettings.keyBindUseItem.pressed) {
    				
    			int PrevSlot = ClientUtils.mc().thePlayer.inventory.currentItem;
                ClientUtils.mc().thePlayer.inventory.currentItem = GetBowSlot();
                
                ClientUtils.mc().rightClickDelayTimer = 3;
                
                ClientUtils.mc().thePlayer.setItemInUse(ClientUtils.mc().thePlayer.getCurrentEquippedItem(), 1);
                
                try {
    	            if (ClientUtils.mc().thePlayer.isUsingItem()
    	                    && ClientUtils.mc().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow) {
    	            	EntityLivingBase en;
    	            	
    	    			
    	                ClientUtils.mc().timer.timerSpeed = 0.1f;
    	                
    	                ClientUtils.mc().playerController.sendUseItem(ClientUtils.mc().thePlayer,
    	                        ClientUtils.mc().theWorld,
    	                        ClientUtils.mc().thePlayer.getCurrentEquippedItem());
    	                for (int i = 0; i < 20; ++i) {
    	                    ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
    	                            ClientUtils.mc().thePlayer.rotationYaw, ClientUtils.mc().thePlayer.rotationPitch, ClientUtils.mc().thePlayer.onGround));
    	                }
    	                ClientUtils.mc().playerController
    	                        .onStoppedUsingItem(ClientUtils.mc().thePlayer);
    	                ClientUtils.mc().timer.timerSpeed = 1f;
    	                
    	            }
                
    	        } catch (Exception e3) {
    	            e3.printStackTrace();
    	        
                }
                ClientUtils.mc().thePlayer.inventory.currentItem = PrevSlot;


    		
    			}
    }
    	}
    	return true;
    }
    
	private int GetBowSlot() {
		for (int i = 0; i < 36; i++) {
			if (ClientUtils.mc().thePlayer.inventory.mainInventory[i] != null) {
				ItemStack is = ClientUtils.mc().thePlayer.inventory.mainInventory[i];
				Item item = is.getItem();
				if (Item.getIdFromItem(item) == 261) {
					return i;
				}
			}
		}
		return -1;
	}
}
