package club.michaelshipley.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InventoryUtils {
	
	public static boolean isPotion(final ItemStack stack, final Potion potion, final boolean splash) {
        if (stack == null) {
            return false;
        }
        if (!(stack.getItem() instanceof ItemPotion)) {
            return false;
        }
        final ItemPotion potionItem = (ItemPotion)stack.getItem();
        if (splash && !ItemPotion.isSplash(stack.getItemDamage())) {
            return false;
        }
        if (potionItem.getEffects(stack) == null) {
            return potion == null;
        }
        if (potion == null) {
            return false;
        }
        for (final Object o : potionItem.getEffects(stack)) {
            final PotionEffect effect = (PotionEffect)o;
            if (effect.getPotionID() == potion.getId()) {
                return true;
            }
        }
        return false;
    }
	public static boolean hotbarHasPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                return true;
            }
        }
        return false;
    }
	
	public static void useFirstPotionSilent(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                final int oldItem = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()));
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }
	
    public static int countPotion(final Potion effect, final boolean splash) {
        int counter = 0;
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }
	
    public static int countInInventory(EntityPlayer player, Item item, int md) {
        int count = 0;
        for (int i = 0; i < player.inventory.mainInventory.length; i++)
            if ((player.inventory.mainInventory[i] != null) && item.equals(player.inventory.mainInventory[i].getItem()) && ((md == -1) || (player.inventory.mainInventory[i].getMetadata() == md)))
                count += player.inventory.mainInventory[i].stackSize;
        return count;
    }
    public static boolean hotbarIsFull() {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(index);
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    public static void getPotion(final Potion effect, final boolean splash) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && isPotion(stack, effect, splash)) {
            	Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId, index, 1, 2, Minecraft.getMinecraft().thePlayer);
                break;
            }
        }
    }
	
}
