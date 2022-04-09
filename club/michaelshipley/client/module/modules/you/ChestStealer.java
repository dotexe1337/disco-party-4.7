// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.you;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiChest;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
@Mod(displayName = "Chest Stealer")
public class ChestStealer extends Module
{
	public ChestStealer() {
		this.setKeybind(Keyboard.KEY_B);
	}
	
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState().equals(Event.State.PRE) && ClientUtils.mc().currentScreen instanceof GuiChest) {
            final GuiChest guiChest = (GuiChest)ClientUtils.mc().currentScreen;
            boolean full = true;
            ItemStack[] mainInventory;
            for (int length = (mainInventory = ClientUtils.player().inventory.mainInventory).length, i = 0; i < length; ++i) {
                final ItemStack item = mainInventory[i];
                if (item == null) {
                    full = false;
                    break;
                }
            }
            if (!full) {
                for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                    final ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                    if (stack != null) {
                        ClientUtils.playerController().windowClick(guiChest.inventorySlots.windowId, index, 0, 1, ClientUtils.player());
                        break;
                    }
                }
            }
        }
    }
}
