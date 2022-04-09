package club.michaelshipley.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class BlockUtils {
	
    public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
        return BlockUtils.getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }
    
    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return BlockUtils.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
    }

    public static Block getBlockAbovePlayer(EntityPlayer inPlayer, double height) {
        return BlockUtils.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + (double)inPlayer.height + height, inPlayer.posZ));
    }

    public static Block getBlock(int x, int y, int z) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }
	
}
