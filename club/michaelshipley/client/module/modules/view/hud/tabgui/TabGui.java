// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view.hud.tabgui;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.client.option.types.BooleanOption;
import club.michaelshipley.event.events.KeyPressEvent;
import club.michaelshipley.event.events.RenderTextEvent;

public class TabGui extends BooleanOption
{
    public TabGui(final String name, final boolean value, final Module module) {
        super(name, name, value, module, true);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof TabGui) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof TabGui && option != this) {
                    ((BooleanOption)option).setValueHard(true);
                    break;
                }
            }
        }
        super.setValue(value);
    }
    
    public boolean setupSizes() {
        return this.getValue();
    }
    
    public boolean onRender(final RenderTextEvent event) {
        return this.getValue();
    }
    
    public boolean onKeypress(final KeyPressEvent event) {
        return this.getValue();
    }
}
