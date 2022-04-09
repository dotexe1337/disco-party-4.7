// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.gui.click.ClickGui;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.utils.ClientUtils;
@Mod(displayName = "Load Config")
public class Reload extends Module
{
    @Override
    public void enable() {
        ClientUtils.mc().currentScreen = null;
        ModuleManager.load();
        OptionManager.load();
        FriendManager.load();
        ClickGui.getInstance().load();
    }
}
