package club.michaelshipley.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class AuraUtils {
	
    public static float getPitchChange(final Entity entity) {
        final double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double deltaY = entity.posY - 2.2 + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity) - 2.5f;
    }
    
    public static float getYawChange(final Entity entity) {
        final double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity));
    }
	
}
