package club.michaelshipley.client.module.modules.you;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.Event.State;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@Mod
public class Sneak extends Module {
	
	public Sneak() {
		this.setKeybind(Keyboard.KEY_Z);
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if(event.getState() == State.PRE) {
			ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.mc().thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
		}
		if(event.getState() == State.POST) {
			ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.mc().thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
			ClientUtils.mc().thePlayer.movementInput.sneak = true;
			//ClientUtils.mc().thePlayer.setSneaking(true);
		}
	}
	
	@Override
	public void disable() {
		super.disable();
		ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(ClientUtils.mc().thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
		ClientUtils.mc().thePlayer.movementInput.sneak = false;
		//ClientUtils.mc().thePlayer.setSneaking(false);
	}
	
	@Override
	public void enable() {
		super.enable();
	}
	
}
