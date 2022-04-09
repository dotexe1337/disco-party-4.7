// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.move.speed.Bypasshop;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.BoundingBoxEvent;
import club.michaelshipley.event.events.PushOutOfBlocksEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

@Mod
public class Phase extends Module
{
	public static double distance;
	
    public Phase() {
    	distance = 1.2;
        this.setKeybind(21);
    }
    
    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        if (event.getState().equals(Event.State.PRE) && this.isInsideBlock() && ClientUtils.player().isSneaking()) {
            final float yaw = Minecraft.thePlayer.rotationYaw;
            Minecraft.thePlayer.boundingBox.offsetAndUpdate(0.9 * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0, 0.9 * Math.sin(Math.toRadians(yaw + 90.0f)));
        }
    }
    
    @EventTarget
    private void onPushOutOfBlocks(PushOutOfBlocksEvent event) {
    	event.setCancelled(true);
    }
    
    @EventTarget
    private void onSetBoundingbox(final BoundingBoxEvent event) {
        if (event.getBoundingBox() != null && event.getBoundingBox().maxY > ClientUtils.player().boundingBox.minY && ClientUtils.player().isSneaking()) {
            event.setBoundingBox(null);
        }
    }
    
    public boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper.floor_double(ClientUtils.player().boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(ClientUtils.player().boundingBox.minY); y < MathHelper.floor_double(ClientUtils.player().boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; ++z) {
                    final Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(ClientUtils.world(), new BlockPos(x, y, z), ClientUtils.world().getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && ClientUtils.player().boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
