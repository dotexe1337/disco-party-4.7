// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option.Op;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.Render3DEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
@Mod(displayName = "Storage Esp", shown = false)
public class StorageEsp extends Module
{
	public static ClientColor cc;
    @Op
    public boolean chest;
    @Op
    public boolean dispenser;
    @Op
    public boolean enderChest;
    @Op
    public boolean filled;
    
    public StorageEsp() {
        this.chest = true;
    }
    
    @EventTarget
    private void onRender(final Render3DEvent event) {
        GlStateManager.pushMatrix();
        for (final Object o : ClientUtils.world().loadedTileEntityList) {
        	final TileEntity ent = (TileEntity)o;
            if (!(ent instanceof TileEntityChest) && !(ent instanceof TileEntityDispenser) && !(ent instanceof TileEntityEnderChest)) {
                continue;
            }
            if (ent instanceof TileEntityChest && !this.chest) {
                continue;
            }
            if (ent instanceof TileEntityDispenser && !this.dispenser) {
                continue;
            }
            if (ent instanceof TileEntityEnderChest && !this.enderChest) {
                continue;
            }
            this.drawEsp(ent, event.getPartialTicks());
        }
        GlStateManager.popMatrix();
    }
    
    private void drawEsp(final TileEntity ent, final float pTicks) {
        final double x1 = ent.getPos().getX() - RenderManager.renderPosX;
        final double y1 = ent.getPos().getY() - RenderManager.renderPosY;
        final double z1 = ent.getPos().getZ() - RenderManager.renderPosZ;
        final float[] color = this.getColor(ent);
        AxisAlignedBB box = new AxisAlignedBB(x1, y1, z1, x1 + 1.0, y1 + 1.0, z1 + 1.0);
        if (ent instanceof TileEntityChest) {
            final TileEntityChest chest = TileEntityChest.class.cast(ent);
            if (chest.adjacentChestZPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 1.9375);
            }
            else if (chest.adjacentChestXPos != null) {
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 1.9375, y1 + 0.875, z1 + 0.9375);
            }
            else {
                if (chest.adjacentChestZPos != null || chest.adjacentChestXPos != null || chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null) {
                    return;
                }
                box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
            }
        }
        else if (ent instanceof TileEntityEnderChest) {
            box = new AxisAlignedBB(x1 + 0.0625, y1, z1 + 0.0625, x1 + 0.9375, y1 + 0.875, z1 + 0.9375);
        }
        if(this.filled) 
        	RenderUtils.filledBox(box, new java.awt.Color((int)cc.red, (int)cc.green, (int)cc.blue).getRGB() & 0x19FFFFFF, true);
        else {
        RenderGlobal.drawOutlinedBoundingBox(box, new java.awt.Color((int)cc.red, (int)cc.green, (int)cc.blue).getRGB());
        }
        
        
        
    }
    
    private float[] getColor(final TileEntity ent) {
        if (ent instanceof TileEntityChest) {
            return new float[] { 0.0f, 0.9f, 0.9f };
        }
        if (ent instanceof TileEntityDispenser) {
            return new float[] { 0.5f, 0.5f, 0.5f };
        }
        if (ent instanceof TileEntityEnderChest) {
            return new float[] { 0.3f, 0.0f, 0.3f };
        }
        return new float[] { 1.0f, 1.0f, 1.0f };
    }		
}
