// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.utils;

public class StateManager
{
    private static boolean offsetLastPacketAura;
    
    public static boolean offsetLastPacketAura() {
        return StateManager.offsetLastPacketAura;
    }
    
    public static void setOffsetLastPacketAura(final boolean offsetLastPacketAura) {
        StateManager.offsetLastPacketAura = offsetLastPacketAura;
    }
}
