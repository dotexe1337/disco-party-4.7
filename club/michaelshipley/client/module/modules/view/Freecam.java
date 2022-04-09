// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.BlockCullEvent;
import club.michaelshipley.event.events.BoundingBoxEvent;
import club.michaelshipley.event.events.InsideBlockRenderEvent;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.PushOutOfBlocksEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;

import java.util.UUID;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.entity.EntityOtherPlayerMP;
@Mod
public class Freecam extends Module
{
    @Option.Op(min = 0.1, max = 2.0, increment = 0.1)
    private double speed;
    private EntityOtherPlayerMP freecamEntity;
    
    public Freecam() {
        this.speed = 1.0;
        this.setKeybind(Keyboard.KEY_V);
    }
    
    @Override
    public void enable() {
        if (ClientUtils.player() == null) {
            return;
        }
        this.freecamEntity = new EntityOtherPlayerMP(ClientUtils.world(), new GameProfile(new UUID(69L, 96L), "Freecam"));
        this.freecamEntity.inventory = ClientUtils.player().inventory;
        this.freecamEntity.inventoryContainer = ClientUtils.player().inventoryContainer;
        this.freecamEntity.setPositionAndRotation(ClientUtils.x(), ClientUtils.y(), ClientUtils.z(), ClientUtils.yaw(), ClientUtils.pitch());
        this.freecamEntity.rotationYawHead = ClientUtils.player().rotationYawHead;
        ClientUtils.world().addEntityToWorld(this.freecamEntity.getEntityId(), this.freecamEntity);
        super.enable();
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (ClientUtils.movementInput().jump) {
            event.setY(ClientUtils.player().motionY = this.speed);
        }
        else if (ClientUtils.movementInput().sneak) {
            event.setY(ClientUtils.player().motionY = -this.speed);
        }
        else {
            event.setY(ClientUtils.player().motionY = 0.0);
        }
        ClientUtils.setMoveSpeed(event, this.speed);
    }
    
    @EventTarget
    private void onBoundingBox(final BoundingBoxEvent event) {
        event.setBoundingBox(null);
    }
    
    @EventTarget
    private void onPushOutOfBlocks(final PushOutOfBlocksEvent event) {
        event.setCancelled(true);
    }
    
    @EventTarget
    private void onInsideBlockRender(final InsideBlockRenderEvent event) {
        event.setCancelled(true);
    }
    
    @EventTarget
    private void onBlockCull(final BlockCullEvent event) {
        event.setCancelled(true);
    }
    
    @Override
    public void disable() {
        ClientUtils.player().setPositionAndRotation(this.freecamEntity.posX, this.freecamEntity.posY, this.freecamEntity.posZ, this.freecamEntity.rotationYaw, this.freecamEntity.rotationPitch);
        ClientUtils.world().removeEntityFromWorld(this.freecamEntity.getEntityId());
        ClientUtils.mc().renderGlobal.loadRenderers();
        super.disable();
    }
}
