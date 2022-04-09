// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.fight;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.fight.antibot.Test;
import club.michaelshipley.client.module.modules.fight.killaura.Noob;
import club.michaelshipley.client.module.modules.fight.killaura.Single;
import club.michaelshipley.client.module.modules.fight.killaura.Switch;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.JumpEvent;
import club.michaelshipley.event.events.PacketReceiveEvent;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.StateManager;
import club.michaelshipley.utils.Timer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;;

@Mod(displayName = "Kill Aura")
public class KillAura extends Module
{
    private Switch switchMode;
    private Single single;
    private Noob noob;
    @Option.Op(name = "Minimum CPS", min = 0, max = 20, increment = 1)
    public double minCps;
    @Option.Op(name = "Maximum CPS", min = 0, max = 20, increment = 1)
    public double maxCps;
    @Option.Op(name = "Aim Speed")
    public double aimSpeed;
    @Option.Op(min = 0.1, max = 7.0, increment = 0.1)
    public float range;
    @Option.Op(name = "FOV", min = 0.0, max = 360.0, increment = 5.0)
    public double fov;
    @Option.Op(name = "Ticks Existed", min = 0.0, max = 25.0, increment = 1.0)
    public double ticksExisted;
    @Option.Op
    public boolean criticals;
    @Option.Op
    public boolean silent;
    @Option.Op
    public boolean smooth;
    @Option.Op
	public boolean players;
    @Option.Op
    public boolean monsters;
    @Option.Op
    public boolean animals;
    @Option.Op
    public boolean invisibles;
    @Option.Op
    public boolean teams;
    @Option.Op(name = "Auto Block")
    public boolean autoblock;
    @Option.Op(name = "No Swing")
    public boolean noSwing;
    private boolean jumpNextTick;
    private ItemStack predictedItem;
    private Timer pickupTimer;
    private Timer potTimer;
    
    public KillAura() {
        this.switchMode = new Switch("Switch", true, this);
        this.single = new Single("Single", false, this);
        this.noob = new Noob("Noob", false, this);
        this.minCps = 6;
        this.maxCps = 13;
        this.range = 4.2f;
        this.fov = 360.0;
        this.ticksExisted = 10.0;
        this.players = true;
        silent = true;
        smooth = true;
        aimSpeed = 4.0;
        this.pickupTimer = new Timer();
        this.potTimer = new Timer();
    }
    
    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.switchMode);
        OptionManager.getOptionList().add(this.single);
        OptionManager.getOptionList().add(this.noob);
        this.updateSuffix();
        super.preInitialize();
    }
    
    @Override
    public void enable() {
        this.single.enable();
        this.switchMode.enable();
        this.noob.enable();
        super.enable();
    }
    
    private void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S0DPacketCollectItem) {
            final S0DPacketCollectItem packet = (S0DPacketCollectItem)event.getPacket();
            final Entity item = ClientUtils.world().getEntityByID(packet.func_149354_c());
            if (item instanceof EntityItem) {
                final EntityItem itemEntity = (EntityItem)item;
                this.predictedItem = itemEntity.getEntityItem();
                this.pickupTimer.reset();
            }
        }
        if (event.getPacket() instanceof S2FPacketSetSlot) {
            final S2FPacketSetSlot packet2 = (S2FPacketSetSlot)event.getPacket();
            if (!this.potTimer.delay(6.0f) && packet2.func_149173_d() == -1 && packet2.func_149175_c() == -1) {
                this.potTimer.setDifference(7L);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(6L);
                        }
                        catch (InterruptedException ex) {}
                        ClientUtils.sendMessage("Post - " + System.currentTimeMillis());
                        ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
                        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, -999, 0, 5, ClientUtils.player());
                        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, 36 + ClientUtils.player().inventory.currentItem, 1, 5, ClientUtils.player());
                        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, -999, 2, 5, ClientUtils.player());
                        if (ClientUtils.player().inventory.getItemStack() != null) {
                            ClientUtils.sendMessage("Fake Placingxd");
                            ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, 36 + ClientUtils.player().inventory.currentItem, 0, 0, ClientUtils.player());
                        }
                    }
                }).start();
            }
            if (!this.pickupTimer.delay(10.0f) && this.predictedItem != null && ItemStack.areItemStackTagsEqual(this.predictedItem, packet2.func_149174_e()) && packet2.func_149173_d() == 36) {
                event.setCancelled(true);
                this.predictedItem = null;
            }
            else {
                this.predictedItem = null;
            }
        }
        if (event.getPacket() instanceof S30PacketWindowItems && this.potTimer.delay(400.0f)) {
            final S30PacketWindowItems packet3 = (S30PacketWindowItems)event.getPacket();
            ClientUtils.sendMessage("Pre - " + System.currentTimeMillis());
            this.potTimer.reset();
        }
        this.potTimer.delay(10.0f);
    }
    
    @EventTarget
    private void onJump(final JumpEvent event) {
        if (StateManager.offsetLastPacketAura()) {
            event.setCancelled(this.jumpNextTick = true);
        }
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        this.switchMode.onUpdate(event);
        this.single.onUpdate(event);
        this.noob.onUpdate(event);
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
    
    private void updateSuffix() {
        if (this.switchMode.getValue()) {
            this.setSuffix("Switch");
        }
        else if(this.single.getValue()) {
            this.setSuffix("Single");
        }
        else if(this.noob.getValue()) {
        	this.setSuffix("Noob");
        }
    }
    
    public boolean isEntityValid(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive() || entityLiving.getDistanceToEntity(ClientUtils.player()) > (ClientUtils.player().canEntityBeSeen(entityLiving) ? this.range : 3.0)) {
                return false;
            }
            if (entityLiving.ticksExisted < this.ticksExisted) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                final ItemStack boots = entityPlayer.getEquipmentInSlot(1);
                final ItemStack legs = entityPlayer.getEquipmentInSlot(2);
                final ItemStack chest = entityPlayer.getEquipmentInSlot(3);
                final ItemStack helm = entityPlayer.getEquipmentInSlot(4);
                boolean fuckedUpArmorOrder = false;
                if (teams && entity != null) {
                    final String name = entity.getDisplayName().getFormattedText();
                    final StringBuilder append = new StringBuilder().append("\247");
                    if (name.startsWith(append.append(ClientUtils.mc().thePlayer.getDisplayName().getFormattedText().charAt(1)).toString())) {
                        return false;
                    }
                }
                if(Test.entities.contains(entityLiving) || FriendManager.isFriend(entityLiving.getName())) {
                	return false;
                }
                if (boots != null && boots.getUnlocalizedName().contains("helmet")) {
                    fuckedUpArmorOrder = true;
                }
                if (legs != null && legs.getUnlocalizedName().contains("chestplate")) {
                    fuckedUpArmorOrder = true;
                }
                if (chest != null && chest.getUnlocalizedName().contains("leggings")) {
                    fuckedUpArmorOrder = true;
                }
                if (helm != null && helm.getUnlocalizedName().contains("boots")) {
                    fuckedUpArmorOrder = true;
                }
                return !fuckedUpArmorOrder;
            }
            else {
                if (this.monsters && (entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast || entityLiving instanceof EntityDragon || entityLiving instanceof EntityWither || entityLiving instanceof EntitySlime || (entityLiving instanceof EntityWolf && ((EntityWolf)entityLiving).getOwner() != ClientUtils.player()))) {
                    return true;
                }
                if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid)) {
                    return true;
                }
                if (invisibles && entity.isInvisibleToPlayer(ClientUtils.mc().thePlayer)) {
                	return true;
                }
            }
        }
        return false;
    }
    
    public boolean isEntityValidType(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive()) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
            	if(FriendManager.isFriend(entityLiving.getName())) {
            		return false;
            	}
            	if(Test.entities.contains(entityLiving)) {
            		return false;
            	}
                final EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                final ItemStack boots = entityPlayer.getEquipmentInSlot(1);
                final ItemStack legs = entityPlayer.getEquipmentInSlot(2);
                final ItemStack chest = entityPlayer.getEquipmentInSlot(3);
                final ItemStack helm = entityPlayer.getEquipmentInSlot(4);
                boolean fuckedUpArmorOrder = false;
                if (boots != null && boots.getUnlocalizedName().contains("helmet")) {
                    fuckedUpArmorOrder = true;
                }
                if (legs != null && legs.getUnlocalizedName().contains("chestplate")) {
                    fuckedUpArmorOrder = true;
                }
                if (chest != null && chest.getUnlocalizedName().contains("leggings")) {
                    fuckedUpArmorOrder = true;
                }
                if (helm != null && helm.getUnlocalizedName().contains("boots")) {
                    fuckedUpArmorOrder = true;
                }
                return !fuckedUpArmorOrder;
            }
            else {
                if (this.monsters && (entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast || entityLiving instanceof EntityDragon || entityLiving instanceof EntityWither || entityLiving instanceof EntitySlime || (entityLiving instanceof EntityWolf && ((EntityWolf)entityLiving).getOwner() != ClientUtils.player()))) {
                    return true;
                }
                if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasArmor(final EntityPlayer player) {
        final ItemStack boots = player.inventory.armorInventory[0];
        final ItemStack pants = player.inventory.armorInventory[1];
        final ItemStack chest = player.inventory.armorInventory[2];
        final ItemStack head = player.inventory.armorInventory[3];
        return boots != null || pants != null || chest != null || head != null;
    }
    
    public void attack(final EntityLivingBase entity) {
        this.attack(entity, false);
    }
    
    public void attack(final EntityLivingBase entity, final boolean crit) {
    	if(!noSwing) {
    		ClientUtils.player().swingItem();
    	}
        final float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        ClientUtils.packet(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }
    
    public void pseudoAttack(final EntityLivingBase entity, final boolean crit) {
        this.swingItem();
        final float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        if (crit || vanillaCrit) {
            ClientUtils.player().onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            ClientUtils.player().onEnchantmentCritical(entity);
        }
    }
    
    public void swingItem() {
    	ClientUtils.player().swingItem();
    }
    
    @Override
    public void disable() {
        StateManager.setOffsetLastPacketAura(false);
        super.disable();
    }
}
