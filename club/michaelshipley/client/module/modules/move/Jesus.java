package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.BoundingBoxEvent;
import club.michaelshipley.event.events.PacketSendEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.LiquidUtils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

@Mod
public class Jesus
extends Module {
    public static boolean shouldOffsetPacket;

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == event.getState().PRE) {
            if (LiquidUtils.isInLiquid() && ClientUtils.mc().thePlayer.isInsideOfMaterial(Material.air) && !ClientUtils.mc().thePlayer.isSneaking()) {
                ClientUtils.mc().thePlayer.motionY = 0.085;
            }
            if (!LiquidUtils.isOnLiquid() || LiquidUtils.isInLiquid() || !this.shouldSetBoundingBox()) {
                shouldOffsetPacket = false;
            }
        }
    }

    @EventTarget
    private void onBoundingBox(BoundingBoxEvent event) {
        if (!LiquidUtils.isInLiquid() && event.getBlock() instanceof BlockLiquid && ClientUtils.world().getBlockState(event.getBlockPos()).getBlock() instanceof BlockLiquid && (Integer)ClientUtils.world().getBlockState(event.getBlockPos()).getValue((IProperty)BlockLiquid.LEVEL) == 0 && this.shouldSetBoundingBox() && (double)(event.getBlockPos().getY() + 1) <= ClientUtils.mc().thePlayer.boundingBox.minY) {
            event.setBoundingBox(new AxisAlignedBB((double)event.getBlockPos().getX(), (double)event.getBlockPos().getY(), (double)event.getBlockPos().getZ(), (double)(event.getBlockPos().getX() + 1), (double)(event.getBlockPos().getY() + 1), (double)(event.getBlockPos().getZ() + 1)));
        }
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        if (event.getState() == event.getState().PRE && event.getPacket() instanceof C03PacketPlayer && LiquidUtils.isOnLiquid()) {
            C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
            boolean bl = Jesus.shouldOffsetPacket = !shouldOffsetPacket;
            if (shouldOffsetPacket) {
                packet.y -= 1.0E-6;
            }
        }
    }

    private boolean shouldSetBoundingBox() {
        if (!ClientUtils.mc().thePlayer.isSneaking() && ClientUtils.mc().thePlayer.fallDistance < 4.0f) {
            return true;
        }
        return false;
    }

    public void onDisable() {
        shouldOffsetPacket = false;
        super.disable();
    }
}