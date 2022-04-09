// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.view.hud.PlainTheme;
import club.michaelshipley.client.module.modules.view.hud.tabgui.Tab;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.KeyPressEvent;
import club.michaelshipley.event.events.RenderTextEvent;
@Mod(enabled = true, shown = false)
public class Hud extends Module
{
    private PlainTheme plainTheme;
    
    @Option.Op
    public static boolean direction;
    @Option.Op
    public static boolean potions;
    @Option.Op
    public static boolean coords;
    
    public Hud() {
        this.plainTheme = new PlainTheme("Nodass Theme", true, this);
        this.direction = true;
        this.potions = true;
        this.coords = true;
    }
    
    @Override
    public void preInitialize() {
    	OptionManager.getOptionList().add(this.plainTheme);
        super.preInitialize();
    }
    
    @EventTarget
    private void onRenderText(final RenderTextEvent event) {
        this.plainTheme.onRender(event);
        //Tab.render();
    }
    
    @EventTarget
    public void onKeyPress(final KeyPressEvent event) {
    	//Tab.keyPress(event);
    }
}
