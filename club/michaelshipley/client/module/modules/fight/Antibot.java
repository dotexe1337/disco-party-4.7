package club.michaelshipley.client.module.modules.fight;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.modules.fight.antibot.Gwen;
import club.michaelshipley.client.module.modules.fight.antibot.Test;
import club.michaelshipley.client.module.modules.fight.antibot.Watchdog;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;

@Mod
public class Antibot extends Module {
	
	Watchdog watchdog;
	Gwen gwen;
	Test test;
	
	
	public Antibot() {
		this.watchdog = new Watchdog("Watchdog", true, this);
		this.gwen = new Gwen("Gwen", false, this);
		this.test = new Test("Test", false, this);
	}
	
	@Override
	public void preInitialize() {
		OptionManager.getOptionList().add(watchdog);
		OptionManager.getOptionList().add(gwen);
		OptionManager.getOptionList().add(test);
		super.preInitialize();
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		this.watchdog.onUpdate(event);
		this.gwen.onUpdate(event);
		this.test.onUpdate(event);
	}
	
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
	
    private void updateSuffix() {
        if (this.watchdog.getValue()) {
            this.setSuffix("Watchdogs");
        }
        else if (this.gwen.getValue()) {
            this.setSuffix("Gwen");
        }
        else if (this.test.getValue()) {
            this.setSuffix("Test");
        }
    }
	
}
