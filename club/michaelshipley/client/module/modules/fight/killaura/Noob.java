package club.michaelshipley.client.module.modules.fight.killaura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.AutoPot;
import club.michaelshipley.client.module.modules.fight.KillAura;
import club.michaelshipley.client.module.modules.move.NoSlowdown;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.client.module.modules.move.speed.Bhop;
import club.michaelshipley.client.module.modules.move.speed.Lowhop;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.AuraUtils;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RotationUtils;
import club.michaelshipley.utils.StateManager;
import club.michaelshipley.utils.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Noob extends KillAuraMode {
		
    private boolean setupTick;
    private EntityLivingBase target;
    private Timer timer = new Timer();
    private Timer timer2 = new Timer();
    private Timer timer3 = new Timer();
    private boolean secondAttack;
    private boolean swapTargetItTurnedIntoAMessyThingFUCK;
    public static boolean cancelNext;
    private UpdateEvent preUpdate;
    float oldYaw;
    float oldPitch;
    public int lookDelay;
    
    public Noob(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        if (super.enable()) {
            this.target = null;
        }
        return super.enable();
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event)) {
            switch (event.getState()) {
                case PRE: {
                    StateManager.setOffsetLastPacketAura(false);
                    final KillAura auraModule = (KillAura)this.getModule();
                    final NoSlowdown noSlowdownModule = (NoSlowdown)new NoSlowdown().getInstance();
                	if (auraModule.silent) {
                        this.oldYaw = ClientUtils.mc().thePlayer.rotationYaw;
                        this.oldPitch = ClientUtils.mc().thePlayer.rotationPitch;
                    }
                    if (this.target == null || !auraModule.isEntityValid(this.target)) {
                        this.target = (this.getTargets().isEmpty() ? null : this.getTargets().get(0));
                    }
                    if (this.target != null) {
                        if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null && ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                            ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getCurrentEquippedItem());
                        }
                        if(auraModule.smooth) {
                        	final EntityPlayerSP thePlayer = ClientUtils.mc().thePlayer;
                            thePlayer.rotationPitch += AuraUtils.getPitchChange(target) / auraModule.aimSpeed;
                            final EntityPlayerSP thePlayer2 = ClientUtils.mc().thePlayer;
                            thePlayer2.rotationYaw += AuraUtils.getYawChange(target) / auraModule.aimSpeed;
                        }else {
                        	final float[] rotations = RotationUtils.getRotations(this.target);
                            ClientUtils.player().rotationYaw = rotations[0];
                            ClientUtils.player().rotationPitch = rotations[1];
                        }
                    }
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
                    this.setupTick = !this.setupTick;
                    break;
                }
                case POST: {
                    final KillAura auraModule = (KillAura)this.getModule();
                    if (auraModule.silent) {
                    	ClientUtils.mc().thePlayer.rotationPitch = this.oldPitch;
                    	ClientUtils.mc().thePlayer.rotationYaw = this.oldYaw;
                    }
                    if(AutoPot.potting) {
                        final EntityLivingBase target = this.target;
                        ++target.auraTicks;
                    }
                    if (this.target == null) {
                        break;
                    }
                    if (this.target != null && auraModule.isEntityValid(this.target)) {
                        if (ClientUtils.player().isBlocking()) {
                            ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        }
                        Random random = new Random();
                        int cps = (int) (random.nextInt((int) (auraModule.maxCps - auraModule.minCps + 1)) + auraModule.minCps);
                        if(timer.delay(1000 / cps) && ClientUtils.player().ticksExisted%2 == 0) {
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
                        	this.attack(this.target, false);
                        	timer.reset();
                        }
                        if (ClientUtils.player().isBlocking()) {
                            ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
                            break;
                        }
                        this.target.auraTicks = 20;
                    }
                    break;
                }
            }
        }
        return true;
    }
    
    private void attack(final EntityLivingBase ent, final boolean crit) {
        final KillAura auraModule = (KillAura)this.getModule();
        if(!auraModule.noSwing) {
        	auraModule.swingItem();
        }
        final float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), ent.getCreatureAttribute());
        final boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        ClientUtils.packet(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            ClientUtils.player().onCriticalHit(ent);
        }
        if (sharpLevel > 0.0f) {
            ClientUtils.player().onEnchantmentCritical(ent);
        }
    }
    
    private void crit() {

    }
    
    protected void swap(final int slot, final int hotbarNum) {
        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.player());
    }
    
    private boolean bhopCheck() {
        if (new Speed().getInstance().isEnabled() && ((Speed)new Speed().getInstance()).bhop.getValue()) {
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                return false;
            }
            Lowhop.stage = -4;
            Bhop.stage = -4;
        }
        return true;
    }
    
    private EntityLivingBase getTarget() {
        final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        final KillAura auraModule = (KillAura)this.getModule();
        for (final Entity ent : ClientUtils.loadedEntityList()) {
            if (ent instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)ent;
                if (((entity.auraTicks > 11 || this.setupTick) && (entity.auraTicks > 10 || !this.setupTick) && entity.auraTicks > 0) || !((KillAura)this.getModule()).isEntityValid(entity)) {
                    continue;
                }
                targets.add(entity);
            }
        }
        Collections.sort(targets, new Comparator<EntityLivingBase>() {
            @Override
            public int compare(final EntityLivingBase o1, final EntityLivingBase o2) {
                return o1.auraTicks - o2.auraTicks;
            }
        });
        if (targets.isEmpty()) {
            return null;
        }
        return targets.get(0);
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
    
    private void lowerTicks() {
        for (final Entity ent : ClientUtils.loadedEntityList()) {
            if (ent instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase;
                final EntityLivingBase entity = entityLivingBase = (EntityLivingBase)ent;
                --entityLivingBase.auraTicks;
            }
        }
    }
    
    @Override
    public boolean disable() {
        Noob.cancelNext = false;
        return super.disable();
    }
	
}
