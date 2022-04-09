// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.Render2DEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.gui.GuiChat;
@Mod(displayName = "Screen Walk")
public class ScreenWalk extends Module
{
    @EventTarget
    private void onRender(final Render2DEvent event) {
        if (ClientUtils.mc().currentScreen != null && !(ClientUtils.mc().currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                ClientUtils.pitch(ClientUtils.pitch() - 2.0f);
            }
            if (Keyboard.isKeyDown(208)) {
                ClientUtils.pitch(ClientUtils.pitch() + 2.0f);
            }
            if (Keyboard.isKeyDown(203)) {
                ClientUtils.yaw(ClientUtils.yaw() - 3.0f);
            }
            if (Keyboard.isKeyDown(205)) {
                ClientUtils.yaw(ClientUtils.yaw() + 3.0f);
            }
        }
    }
}
