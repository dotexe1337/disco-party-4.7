package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.move.speed.Bypasshop;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@Mod(displayName = "SUPERFASTMEME")
public class SUPERFASTMEME extends Module
{
    @Option.Op(min = 3.0, max = 9.0, increment = 0.1, name = "Air Speed")
    public double aspeed;
    @Option.Op(min = 3.0, max = 9.0, increment = 0.1, name = "Ground Speed")
    public double gspeed;
    @Option.Op(min = 0.0, max = 0.455, increment = 0.001, name = "Up Motion")
    public double up;
    @Option.Op(min = 0.0, max = 2.0, increment = 0.01, name = "-Down Motion")
    public double down;
    @Option.Op(min = 0.1, max = 10.0, increment = 0.1, name = "Timer")
    public double timerspeed;
    @Option.Op(name = "Timer")
    public boolean timer;
    @Option.Op(name = "Down Motion")
    public boolean negy;
    @Option.Op(name = "Air Speed")
    public boolean air;
   
    public SUPERFASTMEME() {
        this.aspeed = 3.0;
        this.gspeed = 5.0;
        this.up = 0.455;
        this.down = 2.0;
        this.timerspeed = 1.0;
        this.negy = false;
        this.timer = false;
        this.air = false;
    }
   
    @Override
    public void enable() {
        super.enable();
    }
   
    @Override
    public void disable() {
        ClientUtils.mc().timer.timerSpeed = 1.0f;
        super.disable();
    }
   
    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        if (Bypasshop.isMoving(ClientUtils.player())) {
            if (this.timer) {
                ClientUtils.mc().timer.timerSpeed = ((Minecraft.thePlayer.ticksExisted % 2 == 0) ? ((float)this.timerspeed) : 1.35f);
            }
            if (Minecraft.thePlayer.onGround) {
                Bypasshop.setSpeed(this.gspeed);
                Minecraft.thePlayer.motionY = this.up;
                if (this.timer) {
                    ClientUtils.mc().timer.timerSpeed = ((Minecraft.thePlayer.ticksExisted % 2 == 0) ? ((float)this.timerspeed) : 1.35f);
                }
            }
            else if (Minecraft.thePlayer.isAirBorne) {
                final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                ClientUtils.mc();
                final double motionX = Minecraft.thePlayer.motionX;
                ClientUtils.mc();
                final double n = motionX * Minecraft.thePlayer.motionX;
                ClientUtils.mc();
                final double motionZ = Minecraft.thePlayer.motionZ;
                ClientUtils.mc();
                Bypasshop.setSpeed((float)Math.sqrt(n + motionZ * Minecraft.thePlayer.motionZ));
                if (this.air) {
                	Bypasshop.setSpeed(this.aspeed);
                }
                if (this.negy) {
                    Minecraft.thePlayer.motionY = -this.down;
                }
            }
            else {
                final EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
                ClientUtils.mc();
                final double motionX2 = Minecraft.thePlayer.motionX;
                ClientUtils.mc();
                final double n2 = motionX2 * Minecraft.thePlayer.motionX;
                ClientUtils.mc();
                final double motionZ2 = Minecraft.thePlayer.motionZ;
                ClientUtils.mc();
                Bypasshop.setSpeed((float)Math.sqrt(n2 + motionZ2 * Minecraft.thePlayer.motionZ));
                if (this.negy) {
                    Minecraft.thePlayer.motionY = -this.down;
                }
            }
        }
        else {
            ClientUtils.mc();
            Minecraft.thePlayer.motionX = 0.0;
            ClientUtils.mc();
            Minecraft.thePlayer.motionZ = 0.0;
        }
    }
}