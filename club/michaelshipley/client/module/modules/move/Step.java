// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.move.speed.Lowhop;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.StepEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
@Mod
public class Step extends Module
{
    @Option.Op(min = 1.0, max = 10.0, increment = 1.0)
    private double height;
    @Option.Op
    public boolean packet;
    private int stepStage;
    public static boolean stepping;
    
    public Step() {
        this.height = 1.0;
    }
    
    @EventTarget
    private void onStep(final StepEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.height > 1.0) {
                ClientUtils.mc().timer.timerSpeed = 1.0f;
                event.setStepHeight(this.height);
            }
            else if (!ClientUtils.movementInput().jump && ClientUtils.player().isCollidedVertically) {
                event.setStepHeight(1.5);
                event.setActive(true);
            }
        }
        else if (event.getState() == Event.State.POST && this.packet) {
            if (event.getRealHeight() == 1.5) {
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.42, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.75, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.0, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.16, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.25, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.2, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.3, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.2, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.4, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.mc().timer.timerSpeed = 0.1f;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(400L);
                        }
                        catch (InterruptedException ex) {}
                        ClientUtils.mc().timer.timerSpeed = 1.0f;
                    }
                }).start();
                Lowhop.stage = -4;
            }
            else {
                final double realHeight = event.getRealHeight();
                final double height1 = realHeight * 0.42;
                final double height2 = realHeight * 0.75;
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + height1, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + height2, ClientUtils.z(), ClientUtils.player().onGround));
                ClientUtils.mc().timer.timerSpeed = 0.2f;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException ex) {}
                        ClientUtils.mc().timer.timerSpeed = 1.0f;
                    }
                }).start();
            }
        }
    }
}
