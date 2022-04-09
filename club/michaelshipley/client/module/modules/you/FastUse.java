// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.you;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.fight.regen.modes.Old;
import club.michaelshipley.client.module.modules.you.fastuse.modes.Guardian;
import club.michaelshipley.client.module.modules.you.fastuse.modes.Normal;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
@Mod(displayName = "Fast Use")
public class FastUse extends Module
{
    public Normal normal;
    public Guardian guardian;
    
    public FastUse() {
        this.normal = new Normal("Normal", true, this);
        this.guardian = new Guardian("Guardian", false, this);
    }
    
    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.normal);
        OptionManager.getOptionList().add(this.guardian);
        this.updateSuffix();
        super.preInitialize();
    }
    
    private void updateSuffix() {
        if (this.normal.getValue()) {
            this.setSuffix("Normal");
        }
        else {
            this.setSuffix("Guardian");
        }
    }
    
    @Override
    public void enable() {
        super.enable();
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
    	this.normal.onUpdate(event);
    	this.guardian.onUpdate(event);
    	this.updateSuffix();
    }
    
    public boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(ClientUtils.player().boundingBox.minX); x < MathHelper.floor_double(ClientUtils.player().boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(ClientUtils.player().boundingBox.minY); y < MathHelper.floor_double(ClientUtils.player().boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(ClientUtils.player().boundingBox.minZ); z < MathHelper.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; ++z) {
                    final Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(ClientUtils.world(), new BlockPos(x, y, z), ClientUtils.world().getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && ClientUtils.player().boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
