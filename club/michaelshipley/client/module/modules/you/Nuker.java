package club.michaelshipley.client.module.modules.you;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.Event.State;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.timer.Timer;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.Helper;
import club.michaelshipley.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

@Mod
public class Nuker extends Module {
	
	private int posX, posY, posZ;
	
	@Option.Op(min = 0, max = 50, increment = 1)
    public double radius;
    @Option.Op(min = 0, max = 50, increment = 1)
    public double height;
    private boolean isRunning;
    private Timer timer = new Timer();
    private Timer timer2 = new Timer();
    
    public Nuker() {
    	this.radius = 5;
    	this.height = 5;
    }
    
    @EventTarget
    public void onUpdate(UpdateEvent e) {
    	if(e.getState() == State.PRE) {
            this.isRunning = false;
            int radius = (int) this.radius;
            int height = (int) this.height;
            for(int y = height; y >= -height; --y) {
                for(int x = -radius; x < radius; ++x) {
                    for(int z = -radius; z < radius; ++z) {
                        this.posX = (int)Math.floor(ClientUtils.mc().thePlayer.posX) + x;
                        this.posY = (int)Math.floor(ClientUtils.mc().thePlayer.posY) + y;
                        this.posZ = (int)Math.floor(ClientUtils.mc().thePlayer.posZ) + z;
                        if(ClientUtils.mc().thePlayer.getDistanceSq(ClientUtils.mc().thePlayer.posX + (double)x, ClientUtils.mc().thePlayer.posY + (double)y, ClientUtils.mc().thePlayer.posZ + (double)z) <= 16.0D) {
                            Block block = Helper.blockUtils().getBlock(this.posX , this.posY, this.posZ);
                            boolean blockChecks = true;
                            Block selected = Helper.blockUtils().getBlock(ClientUtils.mc().objectMouseOver.func_178782_a());

                            blockChecks = blockChecks && Helper.blockUtils().canSeeBlock(this.posX + 0.5F, this.posY + 0.9f, this.posZ + 0.5F) && !(block instanceof BlockAir);
                            blockChecks = blockChecks && (block.getBlockHardness(ClientUtils.mc().theWorld, BlockPos.ORIGIN) != -1.0F || ClientUtils.mc().playerController.isInCreativeMode());
                            if(blockChecks) {
                                this.isRunning = true;
                                
                                float[] angles = RotationUtils.faceBlock(this.posX + 0.5F, this.posY + 0.9, this.posZ + 0.5F);
                                return;
                            }
                        }
                    }
                }
            }
    	}
    	if(e.getState() == State.POST) {
            Block block = Helper.blockUtils().getBlock(this.posX, this.posY, this.posZ);
            if(this.isRunning) {
                ClientUtils.mc().thePlayer.swingItem();
                ClientUtils.mc().playerController.func_180512_c(new BlockPos(this.posX , this.posY, this.posZ), Helper.blockUtils().getFacing(new BlockPos(this.posX, this.posY, this.posZ)));
                timer2.reset();
            }
    	}
    }
    
    @Override
    public void disable(){
        super.disable();
        isRunning = false;
        posX = posY = posZ = 0;
    }
	
}
