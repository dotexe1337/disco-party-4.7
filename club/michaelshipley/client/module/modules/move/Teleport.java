package club.michaelshipley.client.module.modules.move;

import org.lwjgl.input.Mouse;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

@Mod()
public class Teleport extends Module
{
    @Option.Op(min = 1.0, max = 10.0, increment = 1.0, name = "Teleport Range")
    public double dist;
   
    public Teleport() {
        this.dist = 9.0;
    }
   
    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        if (Mouse.isButtonDown(1)) {
            if (getDirection().equalsIgnoreCase("SOUTH")) {
                ClientUtils.player().setPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ + this.dist);
            }
            if (getDirection().equalsIgnoreCase("WEST")) {
                ClientUtils.player().setPosition(ClientUtils.player().posX + -this.dist, ClientUtils.player().posY, ClientUtils.player().posZ);
            }
            if (getDirection().equalsIgnoreCase("EAST")) {
                ClientUtils.player().setPosition(ClientUtils.player().posX + this.dist, ClientUtils.player().posY, ClientUtils.player().posZ);
            }
            if (getDirection().equalsIgnoreCase("NORTH")) {
                ClientUtils.player().setPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ + -this.dist);
            }
            if (Minecraft.thePlayer.ticksExisted % 100 == 0) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ, false));
            }
        }
    }
   
    public static String getDirection() {
        return Minecraft.getMinecraft().func_175606_aa().func_174811_aO().name();
    }
}