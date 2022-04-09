// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.ItemSlowEvent;
import club.michaelshipley.event.events.SoulSandSlowEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
@Mod(displayName = "No Slowdown")
public class NoSlowdown extends Module
{
    @Option.Op(name = "Soul Sand")
    private boolean soulSand;
    @Option.Op(name = "Item Use")
    private boolean itemUse;
    @Option.Op(name = "Vanilla")
    private boolean vanilla;
    public static boolean wasOnground;
    
    static {
        NoSlowdown.wasOnground = true;
    }
    
    @EventTarget
    private void onItemUse(final ItemSlowEvent event) {
        if(itemUse) {
        	event.setCancelled(true);
        }
    }
    
    @EventTarget
    private void onSoulSand(final SoulSandSlowEvent event) {
    	if(soulSand) {
    		event.setCancelled(true);
    	}
    }
    
    @EventTarget(4)
    private void onUpdate(final UpdateEvent event) {
        if (!this.vanilla && ClientUtils.player().isBlocking() && (ClientUtils.player().motionX != 0.0 || ClientUtils.player().motionZ != 0.0) && NoSlowdown.wasOnground) {
            if (event.getState() == Event.State.PRE) {
                ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
            else if (event.getState() == Event.State.POST) {
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
            }
        }
        if (!new Speed().getInstance().isEnabled() || !((Speed)new Speed().getInstance()).latest.getValue()) {
            NoSlowdown.wasOnground = ClientUtils.player().onGround;
        }
    }
}
