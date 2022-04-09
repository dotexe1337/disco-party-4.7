// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import club.michaelshipley.client.gui.click.ClickGui;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.gui.GuiScreen;
@Mod(displayName = "Click Gui", keybind = 54, shown = false)
public class Gui extends Module
{
    @Option.Op(name = "Dark TabGui")
    private boolean darkTheme;
    
    @Override
    public void enable() {
        ClientUtils.mc().displayGuiScreen(ClickGui.getInstance());
    }
    
    public boolean isDarkTheme() {
        return this.darkTheme;
    }
}
