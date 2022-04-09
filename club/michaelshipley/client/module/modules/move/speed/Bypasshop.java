package club.michaelshipley.client.module.modules.move.speed;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.move.Fly;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Bypasshop extends SpeedMode {
	
	public Bypasshop(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
    private double moveSpeed;
    private double lastDist;
    public static int stage;
	
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
        	if(!ModuleManager.getModule(Fly.class).isEnabled()) {
                setSpeed(getSpeed());
                if(canSpeed()){
                	if(ClientUtils.mc().thePlayer.isPotionActive(Potion.moveSpeed)) {
                		if(ClientUtils.mc().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                			setSpeed((getSpeed() + 0.257));
                		}
                		if(ClientUtils.mc().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1) {
                			setSpeed((getSpeed() + 0.267));
                		}
                		if(ClientUtils.mc().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 2) {
                			setSpeed((getSpeed() + 0.277));
                		}
                		if(ClientUtils.mc().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 3) {
                			setSpeed((getSpeed() + 0.287));
                		}
                	}else {
                		setSpeed((getSpeed() + 0.239));
                	}
                    event.setY(0.42);
                    ClientUtils.mc().thePlayer.motionY = 0.42;
                }
                if(!isMoving(ClientUtils.mc().thePlayer)) {
                	setSpeed(0);
                }
        	}
        }
		return true;
    }
    
    public static boolean canSpeed() {
        if(isMoving(ClientUtils.mc().thePlayer) && ClientUtils.mc().thePlayer.onGround) {
            return true;
        }
        return false;
    }
    
    public static void setSpeed(double speed) {
        ClientUtils.mc().thePlayer.motionX = (-MathHelper.sin(getDirection()) * speed);
        ClientUtils.mc().thePlayer.motionZ = (MathHelper.cos(getDirection()) * speed);
    }
    
    public static float getDirection() {
        float yaw = ClientUtils.mc().thePlayer.rotationYawHead;
        float forward = ClientUtils.mc().thePlayer.moveForward;
        float strafe = ClientUtils.mc().thePlayer.moveStrafing;
        yaw += (forward < 0.0F ? 180 : 0);
        if(strafe < 0.0F) {
            yaw += (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
            }
        if(strafe > 0.0F) {
            yaw -= (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }
        
        return yaw * 0.017453292F;
        }
    
    public static boolean isMoving(Entity ent)
    {
      return (Minecraft.getMinecraft().thePlayer.moveForward != 0.0F) || (Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0F);
    }
    
    public static float getSpeed()
    {
      return (float)Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
    }
    
    public static boolean isInLiquid()
    {
      if (Minecraft.getMinecraft().thePlayer == null) {
        return false;
      }
      boolean inLiquid = false;
      int y = (int)Minecraft.getMinecraft().thePlayer.boundingBox.minY;
      for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; x++) {
        for (int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; z++)
        {
          Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
          if ((block != null) && (!(block instanceof BlockAir)))
          {
            if (!(block instanceof BlockLiquid)) {
              return false;
            }
            inLiquid = true;
          }
        }
      }
      return inLiquid;
    }
}
