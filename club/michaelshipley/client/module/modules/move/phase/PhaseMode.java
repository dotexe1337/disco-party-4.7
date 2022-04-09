package club.michaelshipley.client.module.modules.move.phase;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.client.option.types.BooleanOption;
import club.michaelshipley.event.events.BoundingBoxEvent;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.PushOutOfBlocksEvent;
import club.michaelshipley.event.events.UpdateEvent;

public class PhaseMode extends BooleanOption
{
    public PhaseMode(final String name, final boolean value, final Module module) {
        super(name, name, value, module, value);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof PhaseMode) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof PhaseMode && option != this) {
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
    
    public boolean onMove(final MoveEvent event) {
        return this.getValue();
    }
    
    public boolean onSetBoundingbox(final BoundingBoxEvent event) {
        return this.getValue();
    }
    
    public boolean onPushOutOfBlocks(final PushOutOfBlocksEvent event) {
    	return this.getValue();
    }
}
