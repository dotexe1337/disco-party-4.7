package club.michaelshipley.client.module.modules.move.speed;

import java.util.List;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.move.Speed;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.LiquidUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Strafe extends SpeedMode {

	public Strafe(String name, boolean value, Module module) {
		super(name, value, module);
	}
	
	private double moveSpeed;
	private double lastDist;
	public int stage;

	@Override
	public boolean enable() {
		if (super.enable()) {
			if (ClientUtils.player() != null) {
				this.moveSpeed = Speed.getBaseMoveSpeed();
			}
			this.lastDist = 0.0;
			stage = 1;
		}
		return true;
	}

	@Override
	public boolean onMove(final MoveEvent event) {
		if (super.onMove(event)) {
			if (ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f) {
				this.moveSpeed = Speed.getBaseMoveSpeed();
			}
			if (stage == 1 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
				this.moveSpeed = 0.1 + Speed.getBaseMoveSpeed() - 0.01;
			} else if (stage == 2 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
				event.setY(ClientUtils.player().motionY = 0.4);
				this.moveSpeed *= 1.52F;
			} else if (stage == 3) {
				final double difference = 0.66 * (this.lastDist - Speed.getBaseMoveSpeed());
				this.moveSpeed = this.lastDist - difference;
			} else {
				final List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
				if ((collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) && stage > 0) {
					if (0.8 * Speed.getBaseMoveSpeed() - 0.01 > this.moveSpeed) {
						stage = 0;
					} else {
						stage = ((ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) ? 1 : 0);
					}
				}
				this.moveSpeed = this.lastDist - this.lastDist / 159.0;
			}
			this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed());
			if (stage > 0) {
				ClientUtils.setMoveSpeed(event, this.moveSpeed);
			}
			if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
				++stage;
			}
		}
		return true;
	}
	
    private static boolean isMoving(Entity ent)
    {
      return (Minecraft.getMinecraft().thePlayer.moveForward != 0.0F) || (Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0F);
    }
    
    public float getSpeed()
    {
      return (float)Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
    }
    
    private boolean isInLiquid()
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

	@Override
	public boolean onUpdate(final UpdateEvent event) {
		if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
			final double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
			final double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
		return true;
	}

}
