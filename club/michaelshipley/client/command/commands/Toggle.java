// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.utils.ClientUtils;

@Com(names = { "toggle", "t", "tog" })
public class Toggle extends Command
{
    @Override
    public void runCommand(final String[] args) {
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        }
        final Module module = ModuleManager.getModule(modName);
        if (module.getId().equalsIgnoreCase("null")) {
            ClientUtils.sendMessage("Invalid Module.");
            return;
        }
        module.toggle();
        ClientUtils.sendMessage(String.valueOf(module.getDisplayName()) + " is now " + (module.isEnabled() ? "enabled" : "disabled"));
        ModuleManager.save();
    }
    
    @Override
    public String getHelp() {
        return "Toggle - toggle <t, tog> (module) - Toggles a module on or off";
    }
}
