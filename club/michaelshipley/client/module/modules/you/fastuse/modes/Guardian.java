package club.michaelshipley.client.module.modules.you.fastuse.modes;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.you.fastuse.FastUseMode;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Guardian extends FastUseMode {
	
	public Guardian(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	@Override
	public boolean onUpdate(UpdateEvent event) {
		if(super.onUpdate(event)) {
			if (event.getState() == Event.State.PRE && ClientUtils.player().getItemInUseDuration() == 16 && !(ClientUtils.player().getItemInUse().getItem() instanceof ItemBow) && !(ClientUtils.player().getItemInUse().getItem() instanceof ItemSword)) {
	            ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, ClientUtils.mc().thePlayer.getCurrentEquippedItem(), 1.0f, 1.0f, 1.0f));
	            for (int i = 0; i < 50; ++i) {
	                ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(ClientUtils.mc().thePlayer.rotationYaw, ClientUtils.mc().thePlayer.rotationPitch, ClientUtils.mc().thePlayer.onGround));
	            }
	            ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	        }
		}
		return false;
	}

}
