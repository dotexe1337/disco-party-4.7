package club.michaelshipley.client.module.modules.view;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.utils.ClientUtils;

@Mod(displayName = "Xray")
public class Xray extends Module {
	
    public static boolean enabled;
    public static final boolean[] blocks;
    private float oldGamma;
    
    static {
        blocks = new boolean[4096];
        add(14);
        add(15);
        add(16);
        add(56);
        add(73);
        add(129);
        add(153);
    }
	
    @Override
    public void onToggle() {
        super.onToggle();
        this.renderBlocks();
    }
    
    public void renderBlocks() {
        final int var0 = (int)ClientUtils.mc().thePlayer.posX;
        final int var = (int)ClientUtils.mc().thePlayer.posY;
        final int var2 = (int)ClientUtils.mc().thePlayer.posZ;
        ClientUtils.mc().renderGlobal.markBlockRangeForRenderUpdate(var0 - 400, var - 400, var2 - 400, var0 + 400, var + 400, var2 + 400);
    }
    
    @Override
    public void enable() {
        super.enable();
        Xray.enabled = true;
        this.oldGamma = ClientUtils.mc().gameSettings.gammaSetting;
        ClientUtils.mc().gameSettings.gammaSetting = 10.0f;
        ClientUtils.mc().gameSettings.ambientOcclusion = 0;
    }
    
    @Override
    public void disable() {
        super.disable();
        Xray.enabled = false;
        ClientUtils.mc().gameSettings.gammaSetting = this.oldGamma;
        ClientUtils.mc().gameSettings.ambientOcclusion = 1;
    }
    
    public static boolean contains(final int id) {
        return Xray.blocks[id];
    }
    
    public static void add(final int id) {
        Xray.blocks[id] = true;
    }
    
    public static void remove(final int id) {
        Xray.blocks[id] = false;
    }
    
    public static void clear() {
        for (int i = 0; i < Xray.blocks.length; ++i) {
            Xray.blocks[i] = false;
        }
    }
    
    public static boolean shouldRender(final int id) {
        return Xray.blocks[id];
    }
	
}
