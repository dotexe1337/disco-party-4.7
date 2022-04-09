// 
// Decompiled by Procyon v0.5.30
// 
package club.michaelshipley.client.module.modules.fight.killaura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.KillAura;
import club.michaelshipley.client.module.modules.fight.antibot.Test;
import club.michaelshipley.client.module.modules.move.NoSlowdown;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RotationUtils;
import club.michaelshipley.utils.StateManager;
import club.michaelshipley.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Switch extends KillAuraMode {
    public boolean setupTick;
    public boolean switchingTargets;
    public List<EntityLivingBase> targets;
    public int index;
    public Timer timer;
    public static Timer potTimer;
    public Timer t;
 
    static {
        Switch.potTimer = new Timer();
    }
 
       public Switch(String name, boolean value, Module module) {
          super(name, value, module);
          this.t = new Timer();
            this.targets = new ArrayList<EntityLivingBase>();
            this.timer = new Timer();
       }
 
       public boolean onUpdate(UpdateEvent event) {
           if(super.onUpdate(event)) {
               final KillAura auraModule = (KillAura)this.getModule();
                 switch (event.getState()) {
                     case PRE: {
                         StateManager.setOffsetLastPacketAura(false);
                         final NoSlowdown noSlowdownModule = (NoSlowdown)new NoSlowdown().getInstance();
                         if (this.timer.delay(300.0f)) {
                             this.targets = this.getTargets();
                         }
                         if (this.index >= this.targets.size()) {
                             this.index = 0;
                         }
                         if (this.targets.size() > 0) {
                             final EntityLivingBase target = this.targets.get(this.index);
                             if (target != null) {
                                 if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null && ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                                     ClientUtils.player().setItemInUse(ClientUtils.player().getHeldItem(), ClientUtils.player().getHeldItem().getMaxItemUseDuration());
                                 }
                                 final float[] rotations = RotationUtils.getRotations(target);
                                 event.setYaw(rotations[0]);
                                 event.setPitch(rotations[1]);
                             }
                             if (this.setupTick) {
                                 if (this.timer.delay(300.0f)) {
                                     this.incrementIndex();
                                     this.switchingTargets = true;
                                     this.timer.reset();
                                 }
                             }
                             else {
                            	 
                             }
                         }
                         this.setupTick = !this.setupTick;
                         break;
                     }
                     case POST: {
                         final KillAura auraModulexd = (KillAura)this.getModule();
                         if (this.setupTick && this.targets.size() > 0 && this.targets.get(this.index) != null && this.targets.size() > 0) {
                             final EntityLivingBase target = this.targets.get(this.index);
                             if (ClientUtils.player().isBlocking()) {
                                 ClientUtils.mc().getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                             }
                             for (int i = 0; i < 1; ++i) {
                                 if (Switch.potTimer.delay(500.0f)) {
                                     auraModule.attack(target);
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
                                 }
                             }
                             break;
                         }
                         break;
                     }
                 default:
                     break;
                 }
           }
  
           return true;
        }
 
       private boolean bhopCheck() {
            if (new Speed().getInstance().isEnabled()) {
                if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                    return false;
                }
            }
            return true;
        }
       
        private void incrementIndex() {
            ++this.index;
            if (this.index >= this.targets.size()) {
                this.index = 0;
            }
        }
       
        private List<EntityLivingBase> getTargets() {
            final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
            for (final Entity entity : ClientUtils.loadedEntityList()) {
                if (((KillAura)this.getModule()).isEntityValid(entity)) {
                    targets.add((EntityLivingBase)entity);
                }
            }
            targets.sort(new Comparator<EntityLivingBase>() {
                @Override
                public int compare(final EntityLivingBase target1, final EntityLivingBase target2) {
                    return Math.round(target2.getHealth() - target1.getHealth());
                }
            });
            return targets;
        }
    }