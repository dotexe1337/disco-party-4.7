// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option;
@Mod(displayName = "Name Protect", shown = false)
public class NameProtect extends Module
{
    @Option.Op
    private boolean colored;
    
    public NameProtect() {
        this.colored = true;
    }
    
    public boolean getColored() {
        return this.colored;
    }
}
