// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.fight.killaura;

import java.util.Random;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.KillAura;
import club.michaelshipley.client.module.modules.fight.antibot.Test;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.Event.State;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.timer.Timer;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.World;

public class Single extends KillAuraMode
{
    private EntityLivingBase target;
    private Timer timer;
    private Timer t;
   
    public Single(final String name, final boolean value, final Module module) {
        super(name, value, module);
        this.t = new Timer();
        this.timer = new Timer();
    }
   
    public boolean enable() {
        this.target = null;
        return super.enable();
    }
   
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event)) {
                final KillAura auraModule = (KillAura)this.getModule();
                if (ClientUtils.player().isEntityAlive()) {
                    this.loadEntity();
                    if (this.target != null) {
                        final double x = this.target.posX - ClientUtils.player().posX;
                        final double z = this.target.posZ - ClientUtils.player().posZ;
                        final double h = ClientUtils.y() + ClientUtils.player().getEyeHeight() - (this.target.posY + this.target.getEyeHeight());
                        final double h2 = Math.sqrt(x * x + z * z);
                        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
                        final float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
                        final double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
                        final double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
                        final double dist = Math.sqrt(xDist * xDist + yDist * yDist);
                        if (dist > auraModule.fov) {
                            this.target = null;
                        }
                    }
                    if (ClientUtils.mc().objectMouseOver.entityHit != null) {
                        final Entity hit = ClientUtils.mc().objectMouseOver.entityHit;
                        if (auraModule.isEntityValidType(hit)) {
                            this.target = (EntityLivingBase)hit;
                        }
                    }
                }
                if (this.target != null && auraModule.autoblock) {
                    final PlayerControllerMP playerController = ClientUtils.playerController();
                    Minecraft.getMinecraft();
                    final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
                    Minecraft.getMinecraft();
                    final WorldClient theWorld = Minecraft.getMinecraft().theWorld;
                    Minecraft.getMinecraft();
                    playerController.sendUseItem((EntityPlayer)thePlayer, (World)theWorld, Minecraft.getMinecraft().thePlayer.getHeldItem());
                }
                Random random = new Random();
                int cps = (int) (random.nextInt((int) (auraModule.maxCps - auraModule.minCps + 1)) + auraModule.minCps);
                if (this.target != null && this.t.hasReached((1000 / cps))) {
                	if(auraModule.criticals) {
                        if (ClientUtils.mc().thePlayer.isCollidedVertically && ClientUtils.mc().thePlayer.onGround) {
                            if (ClientUtils.mc().thePlayer.ticksExisted % 2 == 0 && event.getY() == ClientUtils.mc().thePlayer.posY && ClientUtils.mc().thePlayer.isSwingInProgress && ClientUtils.player().fallDistance == 0.0) {
                                event.setY(event.getY() + 0.063);
                            }
                            if (event.getY() == ClientUtils.mc().thePlayer.posY && event.isOnground()) {
                            	event.setGround(false);
                            }
                	}
                	}
                    auraModule.attack(this.target);
                    this.t.reset();
                }
        }
        return true;
    }
   
    private void crit() {
        ClientUtils.packet((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.0624, ClientUtils.z(), true));
        ClientUtils.packet((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y(), ClientUtils.z(), false));
        ClientUtils.packet((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.11E-4, ClientUtils.z(), false));
        ClientUtils.packet((Packet)new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y(), ClientUtils.z(), false));
    }
   
    private void loadEntity() {
        final KillAura auraModule = (KillAura)this.getModule();
        EntityLivingBase loadEntity = null;
        double distance = 2.147483647E9;
        for (final Entity e : ClientUtils.loadedEntityList()) {
            if (e instanceof EntityLivingBase) {
                if (!auraModule.isEntityValid(e)) {
                    continue;
                }
                final double x = e.posX - ClientUtils.player().posX;
                final double z = e.posZ - ClientUtils.player().posZ;
                final double h = ClientUtils.player().posY + ClientUtils.player().getEyeHeight() - (e.posY + e.getEyeHeight());
                final double h2 = Math.sqrt(x * x + z * z);
                final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
                final float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
                final double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
                final double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
                final double angleDistance = Math.sqrt(xDist * xDist + yDist * yDist);
                if (angleDistance > auraModule.fov) {
                    continue;
                }
                if (ClientUtils.player().getDistanceToEntity(e) >= distance) {
                    continue;
                }
                loadEntity = (EntityLivingBase)e;
                distance = ClientUtils.player().getDistanceToEntity(e);
            }
        }
        this.target = loadEntity;
    }
}