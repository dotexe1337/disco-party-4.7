// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.utils;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils
{
	
 	public Minecraft mc;
    public float blockDamage;
    public float hitDelay;

    public boolean shouldHitBlock(final int x, final int y, final int z, final double dist) {
        final Block block = this.getBlock(x, y, z);
        final boolean isNotLiquid = !(block instanceof BlockLiquid);
        final boolean canSeeBlock = this.canBlockBeSeen(x, y, z);
        return isNotLiquid && canSeeBlock;
    }
    
    public void movePlayerToBlock(final double posX, final double posY, final double posZ, final float dist) {
        this.faceBlock(posX + 0.5, posY + 0.5, posZ + 0.5);
        this.moveForward();
        if (this.mc.thePlayer.onGround && this.mc.thePlayer.isCollidedHorizontally && !Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode()) && !this.mc.thePlayer.isInWater()) {
            this.mc.thePlayer.jump();
        }
        if (this.canReach(posX, posY, posZ, dist)) {
            this.stopMoving();
        }
    }
    
    public void placeBlock(final int posX, final int posY, final int posZ) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.getBlockPos(posX, posY, posZ), this.getEnumFacing(posX, posY, posZ).getIndex(), this.mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
    }
    
    public int getNextSlotInContainer(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isContainerEmpty(final Container container) {
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canReach(final double posX, final double posY, final double posZ, final float distance) {
        final double blockDistance = this.getDistance(posX, posY, posZ);
        return blockDistance < distance && blockDistance > -distance;
    }
    
    public TileEntityChest getBestEntity(final double range) {
        TileEntityChest tempEntity = null;
        double dist = range;
        for (final Object i : this.mc.theWorld.loadedEntityList) {
            final TileEntityChest entity = (TileEntityChest)i;
            if (this.getDistanceToEntity(entity) <= 6.0f) {
                final double curDist = this.getDistanceToEntity(entity);
                if (curDist > dist) {
                    continue;
                }
                dist = curDist;
                tempEntity = entity;
            }
        }
        return tempEntity;
    }
    
    public float getDistanceToEntity(final TileEntity tileEntity) {
        final float var2 = (float)(this.mc.thePlayer.posX - tileEntity.getPos().getX());
        final float var3 = (float)(this.mc.thePlayer.posY - tileEntity.getPos().getY());
        final float var4 = (float)(this.mc.thePlayer.posZ - tileEntity.getPos().getZ());
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    private Float[] getRotationsTileEntity(final TileEntity entity) {
        final double posX = entity.getPos().getX() - this.mc.thePlayer.posX;
        final double posZ = entity.getPos().getY() - this.mc.thePlayer.posZ;
        final double posY = entity.getPos().getZ() + 1 - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
        final double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(posY / helper)));
        if (posZ < 0.0 && posX < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(posZ / posX)));
        }
        else if (posZ < 0.0 && posX > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(posZ / posX)));
        }
        return new Float[] { newYaw, newPitch };
    }
    
    public void breakBlockLegit(final int posX, final int posY, final int posZ, final int delay) {
        ++this.hitDelay;
        this.mc.thePlayer.swingItem();
        if (this.blockDamage == 0.0f) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing(posX, posY, posZ)));
        }
        if (this.hitDelay >= delay) {
            this.blockDamage += this.getBlock(posX, posY, posZ).getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.theWorld, new BlockPos(posX, posY, posZ));
            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), new BlockPos(posX, posY, posZ), (int)(this.blockDamage * 10.0f) - 1);
            if (this.blockDamage >= (this.mc.playerController.isInCreativeMode() ? 0.0f : 1.0f)) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing(posX, posY, posZ)));
                this.mc.playerController.func_178888_a(this.getBlockPos(posX, posY, posZ), this.getEnumFacing(posX, posY, posZ));
                this.blockDamage = 0.0f;
                this.hitDelay = 0.0f;
            }
        }
    }
    
    public boolean canBlockBeSeen(final int posX, final int posY, final int posZ) {
        final Vec3 player = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight(), this.mc.thePlayer.posZ);
        final Vec3 block = new Vec3(posX + 0.5f, posY + 0.5f, posZ + 0.5f);
        return (this.mc.theWorld.rayTraceBlocks(player, block) != null) ? (this.mc.theWorld.rayTraceBlocks(player, block).field_178784_b != null) : null;
    }
    
    public void breakBlock(final double posX, final double posY, final double posZ) {
        this.mc.thePlayer.swingItem();
        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing((int)posX, (int)posY, (int)posZ)));
        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getBlockPos(posX, posY, posZ), this.getEnumFacing((int)posX, (int)posY, (int)posZ)));
    }
    
    public EnumFacing getEnumFacing(final float posX, final float posY, final float posZ) {
        return EnumFacing.func_176737_a(posX, posY, posZ);
    }
    
    public BlockPos getBlockPos(final double x, final double y, final double z) {
        final BlockPos pos = new BlockPos(x, y, z);
        return pos;
    }
    
    public void moveForward() {
        this.mc.gameSettings.keyBindForward.pressed = true;
    }
    
    public void moveLeft() {
        this.mc.gameSettings.keyBindLeft.pressed = true;
    }
    
    public void moveRight() {
        this.mc.gameSettings.keyBindRight.pressed = true;
    }
    
    public void moveBack() {
        this.mc.gameSettings.keyBindBack.pressed = true;
    }
    
    public void stopMoving() {
        this.mc.gameSettings.keyBindForward.pressed = false;
        this.mc.gameSettings.keyBindLeft.pressed = false;
        this.mc.gameSettings.keyBindRight.pressed = false;
        this.mc.gameSettings.keyBindBack.pressed = false;
    }
    
    public Block getBlock(double posX, double posY, double posZ) {
        posX = MathHelper.floor_double(posX);
        posY = MathHelper.floor_double(posY);
        posZ = MathHelper.floor_double(posZ);
        return this.mc.theWorld.getChunkFromBlockCoords(new BlockPos(posX, posY, posZ)).getBlock(new BlockPos(posX, posY, posZ));
    }
    
    public float getDistance(final double x, final double y, final double z) {
        final float xDiff = (float)(this.mc.thePlayer.posX - x);
        final float yDiff = (float)(this.mc.thePlayer.posY - y);
        final float zDiff = (float)(this.mc.thePlayer.posZ - z);
        return MathHelper.sqrt_float(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
    
    public float getDistanceToVec(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        final float xDiff = (float)(x - x2);
        final float yDiff = (float)(y - y2);
        final float zDiff = (float)(z - z2);
        return MathHelper.sqrt_float(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
    
    public static float[] faceBlock(final double posX, final double posY, final double posZ) {
        final double diffX = posX - Helper.mc().thePlayer.posX;
        final double diffZ = posZ - Helper.mc().thePlayer.posZ;
        final double diffY = posY - (Helper.mc().thePlayer.posY + Helper.mc().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
		float lyaw = Helper.mc().thePlayer.rotationYaw;
		float lpitch = Helper.mc().thePlayer.rotationPitch;
		
		return new float[]{lyaw += MathHelper.wrapAngleTo180_float(yaw - lyaw), lpitch += MathHelper.wrapAngleTo180_float(pitch - lpitch)};
        
    }
    
    public boolean isVisibleFOV(final TileEntity tileEntity, final EntityPlayerSP thePlayer, final int fov) {
        return ((Math.abs(this.getRotationsTileEntity(tileEntity)[0] - thePlayer.rotationYaw) % 360.0f > 180.0f) ? (360.0f - Math.abs(this.getRotationsTileEntity(tileEntity)[0] - thePlayer.rotationYaw) % 360.0f) : (Math.abs(this.getRotationsTileEntity(tileEntity)[0] - thePlayer.rotationYaw) % 360.0f)) <= fov;
    }
	
    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f - 0.5;
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getAverageRotations(final List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (final Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        posX /= targetList.size();
        posY /= targetList.size();
        posZ /= targetList.size();
        return new float[] { getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1] };
    }
    
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 0.6;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float getTrajAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }
    
    public static float getNewAngle(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
    
    public static float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }
    
    
}
