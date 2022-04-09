// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.Event;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.network.play.client.C0APacketAnimation;
@Mod
public class Derp extends Module
{
    @Option.Op
    private boolean spinny;
    @Option.Op
    private boolean headless;
    @Option.Op(name = "Spin Increment", min = 1.0, max = 42.0, increment = 1.0)
    private double increment;
    private double serverYaw;
    
    public Derp() {
        this.increment = 25.0;
        this.spinny = true;
        this.headless = false;
        this.setKeybind(Keyboard.KEY_U);
    }
    
    @EventTarget(0)
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (this.spinny) {
                this.serverYaw += this.increment;
                event.setYaw((float)this.serverYaw);
                ClientUtils.player().renderYawOffset = (float) serverYaw;
            }
            if (this.headless) {
                event.setPitch(180.0f);
            }
            else if (!this.headless && !this.spinny) {
                event.setYaw((float)(Math.random() * 360.0));
                event.setPitch((float)(Math.random() * 360.0));
                ClientUtils.packet(new C0APacketAnimation());
            }
        }
    }
}
