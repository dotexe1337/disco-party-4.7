package club.michaelshipley.client.module.modules.fight.antibot;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.client.option.types.BooleanOption;
import club.michaelshipley.event.events.PacketReceiveEvent;
import club.michaelshipley.event.events.UpdateEvent;

public class AntibotMode extends BooleanOption
{
    public AntibotMode(final String name, final boolean value, final Module module) {
        super(name, name, value, module, true);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof AntibotMode) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof AntibotMode && option != this) {
                    ((BooleanOption)option).setValueHard(true);
                    break;
                }
            }
        }
        super.setValue(value);
    }
    
    public boolean enable() {
        return this.getValue();
    }
    
    public boolean onUpdate(final UpdateEvent event) {
        return this.getValue();
    }
    
    public boolean onPacket(final PacketReceiveEvent event) {
    	return this.getValue();
    }
    
    public boolean disable() {
        return this.getValue();
    }
}
