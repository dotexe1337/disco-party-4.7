package club.michaelshipley.client.module.modules.move;

import java.util.HashMap;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.move.speed.Bypasshop;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@Mod(displayName = "MICHAELS100PERCENTCUSTOMCODEDFLY")
public class MICHAELS100PERCENTCUSTOMFLY extends Module
{
    @Option.Op(min = 0.25, max = 10.0, increment = 0.01, name = "XZ Speed")
    public double speed;
    @Option.Op(min = 1.0, max = 10.0, increment = 1.0, name = "Motion Y Speed")
    public double motionyspeed;
    @Option.Op(min = 0.1, max = 10.0, increment = 0.1, name = "Timer")
    public double timerspeed;
    @Option.Op(name = "Timer")
    public boolean timer;
    @Option.Op(name = "LongJump Fly")
    public boolean longJump;
   
    public MICHAELS100PERCENTCUSTOMFLY() {
        this.speed = 0.25;
        this.motionyspeed = 1.0;
        this.timer = false;
        this.timerspeed = 1.0;
        this.longJump = false;
    }
   
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.longJump) {
                final int xd = 0;
                if (ClientUtils.mc().gameSettings.keyBindJump.pressed) {
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionY = 1.5;
                }
                if (ClientUtils.mc().gameSettings.keyBindSneak.pressed) {
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionY = -1.5;
                }
                Label_0194: {
                    if (isMoving(ClientUtils.player()) && !ClientUtils.mc().gameSettings.keyBindJump.pressed && !ClientUtils.mc().gameSettings.keyBindSneak.pressed) {
                        ClientUtils.mc();
                        if (Minecraft.thePlayer.motionY > -0.41) {
                            ClientUtils.mc();
                            if (!Minecraft.thePlayer.onGround) {
                                break Label_0194;
                            }
                        }
                        setSpeed(this.speed);
                        ClientUtils.mc();
                        Minecraft.thePlayer.motionY = -0.6;
                        event.isOnground();
                        if (this.timer) {
                            ClientUtils.mc().timer.timerSpeed = (float) this.timerspeed;
                        }
                        final HashMap hashMap = new HashMap();
                    }
                }
                if (!ClientUtils.mc().gameSettings.keyBindJump.pressed && !ClientUtils.mc().gameSettings.keyBindSneak.pressed) {
                    ClientUtils.mc();
                    if (Minecraft.thePlayer.motionY > -0.42) {
                        ClientUtils.mc();
                        if (!Minecraft.thePlayer.onGround) {
                            return;
                        }
                    }
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionY = 0.455;
                }
            }
            else {
                if (ClientUtils.movementInput().jump) {
                    ClientUtils.player().motionY = this.motionyspeed;
                }
                else if (ClientUtils.movementInput().sneak) {
                    ClientUtils.player().motionY = -this.motionyspeed;
                }
                else {
                    ClientUtils.player().motionY = 0.0;
                }
                if (this.timer) {
                    ClientUtils.mc().timer.timerSpeed = (float) ((ClientUtils.mc().thePlayer.ticksExisted % 2 == 0) ? this.timerspeed : 2.0);
                }
                if (Bypasshop.isMoving(ClientUtils.player())) {
                    Bypasshop.setSpeed(this.speed);
                }
                else {
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionX = 0.0;
                    ClientUtils.mc();
                    Minecraft.thePlayer.motionZ = 0.0;
                }
            }
        }
    }
   
    public static boolean isMoving(final Entity ent) {
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.moveForward == 0.0f) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.moveStrafing == 0.0f) {
                return false;
            }
        }
        return true;
    }
   
    public static void setSpeed(final double speed) {
        ClientUtils.mc();
        Minecraft.thePlayer.motionX = -MathHelper.sin(getDirection()) * speed;
        ClientUtils.mc();
        Minecraft.thePlayer.motionZ = MathHelper.cos(getDirection()) * speed;
    }
   
    public static float getDirection() {
        ClientUtils.mc();
        float yaw = Minecraft.thePlayer.rotationYawHead;
        ClientUtils.mc();
        final float forward = Minecraft.thePlayer.moveForward;
        ClientUtils.mc();
        final float strafe = Minecraft.thePlayer.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);
        if (strafe < 0.0f) {
            yaw += ((forward < 0.0f) ? -45 : ((forward == 0.0f) ? 90 : 45));
        }
        if (strafe > 0.0f) {
            yaw -= ((forward < 0.0f) ? -45 : ((forward == 0.0f) ? 90 : 45));
        }
        return yaw * 0.017453292f;
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
}