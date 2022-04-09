package club.michaelshipley.client.module.modules.fight.regen;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.modules.fight.killaura.KillAuraMode;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.client.option.types.BooleanOption;
import club.michaelshipley.event.events.UpdateEvent;

public class RegenMode extends BooleanOption
{
    public RegenMode(final String name, final boolean value, final Module module) {
        super(name, name, value, module, true);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof RegenMode) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof RegenMode && option != this) {
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
    
    public boolean disable() {
        return this.getValue();
    }
}
