package club.michaelshipley.client.module.modules.view.hud.tabgui;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.Client;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.view.ClientColor;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.types.BooleanOption;
import club.michaelshipley.client.option.types.NumberOption;
import club.michaelshipley.event.events.KeyPressEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.FontManager;
import club.michaelshipley.utils.RenderUtils;

/**
 * @author Godly Hoodman
 **/
public class Tab {

	private static final int MIN_HEX = -23614;
    private static final int MAX_HEX = -3394561;
    private static final int MAX_FADE = 20;
    private static int currentColor;
    private static int fadeState;
    private static boolean goingUp;
	public static int categoryWidth;
	public static int categoryHeight;
	public static int modWidth;
	public static int modHeight;
	public static int optionWidth;
	public static int optionHeight;
	public static int valueWidth;
	public static int valueHeight;
	

	public static int categoryTab;
	public static int modTab;
	public static int optionTab;
	public static int valueTab;

	public static int categoryY;
	public static int categoryTargetY;
	public static int modY;
	public static int modTargetY;
	public static int optionY;
	public static int optionTargetY;
	public static int valueY;
	public static int valueTargetY;

	private static int offset, gap = 3, top;

	public static Section section;
	private static long animateTime;
	public static ClientColor cc;
	private static boolean animateFast, valEditing, binding;

	// Godly Hoodman created this//rules: dont remove this comment or the author comment

	public static void init() {
		offset = 30;//this is the extra width added to the sections of the tabgui, aka the size from left to right
		top = 24;//this is the top of the tabgui
		gap = 2;//this is the gap between the sections when they are opened
		Tab.section = Section.TYPES;
		Tab.categoryTab = Tab.modTab = Tab.optionTab = 0;
		Tab.categoryY = Tab.categoryTargetY = top;
		Tab.modY = Tab.modTargetY = top;
		Tab.optionY = Tab.optionTargetY = top;
		Tab.valueY = Tab.valueTargetY = top;
		int highestWidth = 20;
		Module.Category[] values;
		for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
			final Module.Category category = values[i];
			final String name = String.valueOf(String.valueOf(Character.toLowerCase(category.name().charAt(0))))
					+ category.name().substring(1);
			final int stringWidth = ClientUtils.mc().fontRendererObj.getStringWidth(name);
			highestWidth = Math.max(stringWidth, highestWidth);
		}
		Tab.categoryWidth = FontManager.watermark.getStringWidth(Client.clientName);
		Tab.categoryHeight = (Module.Category.values().length + 2) * 14;
	}

	public static void render() {
		animateBars();
		RenderUtils.rectangle(3.0, top, Tab.categoryWidth, Tab.categoryHeight - 4, 0X96000000);
		RenderUtils.rectangle(4, Tab.categoryY + 1, Tab.categoryWidth - 1, Tab.categoryY + 13, new java.awt.Color((int)cc.red, (int)cc.green, (int)cc.blue).getRGB());
		
		int yPos = top;
		for (int i = 0; i < Module.Category.values().length; ++i) {
			final Module.Category category = Module.Category.values()[i];
			final String name = String
					.valueOf(String.valueOf(Character.toLowerCase(category.name().toLowerCase().charAt(0))))
					+ category.name().toLowerCase().substring(1);
			 FontManager.text.drawStringWithShadow(name.toLowerCase(), 2 + (categoryWidth / 18), yPos + 3, i == categoryTab ? -1 : -1);
			 FontManager.text.drawStringWithShadow("+", categoryWidth - 12, yPos + 3, i == categoryTab ? -1 : -1);
			yPos += 14;
		}
		if (Tab.section == Section.MODULES || Tab.section == Section.OPTIONS
				|| Tab.section == Section.VALUE) {
			int modMaxWidth = Tab.categoryWidth + gap + Tab.modWidth;
			RenderUtils.rectangle(Tab.categoryWidth + gap, top, modMaxWidth, modHeight - 4, 0X96000000);
			RenderUtils.rectangle(Tab.categoryWidth + gap + 1, Tab.modY + 1, modMaxWidth - 1,
					Tab.modY + 13, new java.awt.Color((int)cc.red, (int)cc.green, (int)cc.blue).getRGB());
			yPos = top + 1;
			for (int i = 0; i < ModuleManager
					.getModsInCategory(Module.Category.values()[Tab.categoryTab]).size(); ++i) {
				final Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(i);
				 FontManager.text.drawStringWithShadow(mod.getDisplayName().toLowerCase(), categoryWidth + gap + (modWidth / 20), yPos + 1,
						(i == modTab ? (mod.isEnabled() ? 16777215 : 0xffa8a8a8) : mod.isEnabled() ? -1 : 0XFF7a7777));
				 if(mod.getOptions().size() > 0) {
					 FontManager.text.drawStringWithShadow("+", categoryWidth + gap + modWidth - 10, yPos + 1, i == modTab ? -1 : -1);
				 }
 				
				yPos += 14;
			}
		}
		if (Tab.section == Section.OPTIONS || Tab.section == Section.VALUE) {
			int modMaxWidth = Tab.categoryWidth + gap + Tab.modWidth;
			int valMaxWidth = modMaxWidth + gap + Tab.optionWidth + 6;
			RenderUtils.rectangle(modMaxWidth + gap, top, valMaxWidth, optionHeight - 4, 0X96000000);
			RenderUtils.rectangle(modMaxWidth + gap + 1, Tab.optionY + 1, valMaxWidth - 1, Tab.optionY + 13,
					new java.awt.Color((int)cc.red, (int)cc.green, (int)cc.blue).getRGB());
			Module mod = ModuleManager.getModsInCategory(Module.Category.values()[Tab.categoryTab])
					.get(Tab.modTab);
			Option op = mod.getOptions().get(Tab.optionTab);
			yPos = top + 1;
			for (Option property : mod.getOptions()) {
				if (property instanceof BooleanOption) {
					 FontManager.text.drawStringWithShadow(property.getDisplayName().toLowerCase(),
							modMaxWidth + gap + (optionWidth / 12), yPos + 1,
							(property == op ? ((Boolean) property.getValue() ? 16777215 : 0xffa8a8a8) : (Boolean) property.getValue() ? 0xffa8a8a8 : 0xff5b5b5b));
				} else if (property instanceof NumberOption) {
					 FontManager.text.drawStringWithShadow(property.getDisplayName().toLowerCase(),
							modMaxWidth + gap + (optionWidth / 12), yPos + 1, property == op ? -1 : -1);
					 FontManager.text.drawStringWithShadow("+", modMaxWidth + gap + optionWidth - 4, yPos + 1, property == op ? -1 : -1);
				}
				yPos += 14;
			}
		}
		if (Tab.section == Section.VALUE) {
			int modMaxWidth = Tab.categoryWidth + gap + Tab.modWidth;
			int valMaxWidth = modMaxWidth + gap + Tab.optionWidth + 6;
			int subMaxWidth = valMaxWidth + gap + Tab.valueWidth;
			RenderUtils.rectangle(valMaxWidth + gap, optionY, subMaxWidth, optionY + 15, -436207616);
			RenderUtils.rectangle(valMaxWidth + gap + 1, Tab.optionY + 1, subMaxWidth - 1, Tab.optionY + 14,
					new java.awt.Color((int)cc.red, (int)cc.green, (int)cc.blue).getRGB());
			Module mod = ModuleManager.getModsInCategory(Module.Category.values()[Tab.categoryTab])
					.get(Tab.modTab);
			Option op = mod.getOptions().get(Tab.optionTab);
			 FontManager.text.drawStringWithShadow(String.valueOf(op.getValue()),
					valMaxWidth + gap + 4, optionY + 3, 16777215);
		}
	}

	public static void keyPress(KeyPressEvent e) {
		final int key = e.getKey();
		if (Tab.section == Section.TYPES) {
			switch (key) {
			case Keyboard.KEY_RIGHT: {
				int highestWidth = 0;
				for (final Module module : ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab])) {
					final String name = module.getId();
					final int stringWidth = ClientUtils.mc().fontRendererObj.getStringWidth(name);
					highestWidth = Math.max(stringWidth, highestWidth);
				}
				Tab.modWidth = highestWidth + offset;
				Tab.modHeight = (ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).size() + 2) * 14;
				Tab.modTargetY = (Tab.modY = top);
				Tab.modTab = 0;
				Tab.section = Section.MODULES;
				break;
			}
			case Keyboard.KEY_RETURN: {
				int highestWidth = 0;
				for (final Module module : ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab])) {
					final String name = module.getId();
					final int stringWidth = ClientUtils.mc().fontRendererObj.getStringWidth(name);
					highestWidth = Math.max(stringWidth, highestWidth);
				}
				Tab.modWidth = highestWidth + offset;
				Tab.modHeight = (ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).size() + 2) * 14;
				Tab.modTargetY = (Tab.modY = top);
				Tab.modTab = 0;
				Tab.section = Section.MODULES;
				break;
			}
			case Keyboard.KEY_DOWN: {
				animateFast = true;
				++Tab.categoryTab;
				Tab.categoryTargetY += 14;
				if (Tab.categoryTab > Module.Category.values().length - 1) {
					Tab.categoryTargetY = top;
					Tab.categoryTab = 0;
					break;
				}
				animateFast = false;
				break;
			}
			case Keyboard.KEY_UP: {
				animateFast = true;
				--Tab.categoryTab;
				Tab.categoryTargetY -= 14;
				if (Tab.categoryTab < 0) {
					Tab.categoryTargetY = top + (Module.Category.values().length - 1) * 14;
					Tab.categoryTab = Module.Category.values().length - 1;
					break;
				}
				animateFast = false;
				break;
			}
			}
		} else if (Tab.section == Section.MODULES) {
			switch (key) {
			case Keyboard.KEY_LEFT: {
				Tab.section = Section.TYPES;
				break;
			}
			case Keyboard.KEY_RIGHT: {
				final Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
				mod.toggle();
				break;
			}
			case Keyboard.KEY_DOWN: {
				animateFast = true;
				++Tab.modTab;
				Tab.modTargetY += 14;
				if (Tab.modTab > ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).size() - 1) {
					Tab.modTargetY = top;
					Tab.modTab = 0;
					break;
				}
				animateFast = false;
				break;
			}
			case Keyboard.KEY_UP: {
				animateFast = true;
				--Tab.modTab;
				Tab.modTargetY -= 14;
				if (Tab.modTab < 0) {
					Tab.modTargetY = top + (ModuleManager
							.getModsInCategory(Module.Category.values()[Tab.categoryTab]).size() - 1) * 14;
					Tab.modTab = ModuleManager
							.getModsInCategory(Module.Category.values()[Tab.categoryTab]).size() - 1;
					break;
				}
				animateFast = false;
				break;
			}
			case Keyboard.KEY_RETURN: {
				final Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
				int highestWidth = 0;
				for (final Option op : mod.getOptions()) {
					final String name = op.getDisplayName();
					final int stringWidth = ClientUtils.mc().fontRendererObj.getStringWidth(name);
					highestWidth = Math.max(stringWidth, highestWidth);
				}
				Tab.optionWidth = highestWidth + offset;
				Tab.optionHeight = (mod.getOptions().size() + 2) * 14;
				Tab.optionTargetY = (Tab.optionY = top);
				Tab.optionTab = 0;
				if (mod.getOptions().size() > 0) {
					Tab.section = Section.OPTIONS;
				}
				break;
			}
			}
		} else if (Tab.section == Section.OPTIONS) {
			switch (key) {
			case Keyboard.KEY_LEFT: {
				Tab.section = Section.MODULES;
				break;
			}
			case Keyboard.KEY_RIGHT: {
				final Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
				final Option op = mod.getOptions().get(Tab.optionTab);
				if (op instanceof BooleanOption) {
					op.setValue(!(Boolean) op.getValue());
					break;
				}
				if (op instanceof NumberOption) {
					int highestWidth = 0;
					final String name = String.valueOf(op.getValue());
					final int stringWidth = ClientUtils.mc().fontRendererObj.getStringWidth(name);
					highestWidth = Math.max(stringWidth, highestWidth);
					Tab.valueWidth = highestWidth + offset;
					Tab.valueHeight = 14;
					Tab.valueTargetY = (Tab.valueY = optionY);
					Tab.valueTab = 0;
					Tab.section = Section.VALUE;
				}
				break;
			}
			case Keyboard.KEY_DOWN: {
				animateFast = true;
				++Tab.optionTab;
				Tab.optionTargetY += 14;
				final Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
				if (Tab.optionTab > mod.getOptions().size() - 1) {
					Tab.optionTargetY = top;
					Tab.optionTab = 0;
					break;
				}
				animateFast = false;
				break;
			}
			case Keyboard.KEY_UP: {
				animateFast = true;
				--Tab.optionTab;
				Tab.optionTargetY -= 14;
				if (Tab.optionTab < 0) {
					final Module mod = ModuleManager
							.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
					Tab.optionTargetY = top + (mod.getOptions().size() - 1) * 14;
					Tab.optionTab = mod.getOptions().size() - 1;
					break;
				}
				animateFast = false;
				break;
			}
			}

		} else if (Tab.section == Section.VALUE) {
			switch (key) {
			case Keyboard.KEY_LEFT: {
				Tab.section = Section.OPTIONS;
				break;
			}
			case Keyboard.KEY_DOWN: {
				Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
				Option op = mod.getOptions().get(Tab.optionTab);
				NumberOption value = (NumberOption) op;
				((NumberOption) op).deincrement();
				break;
			}
			case Keyboard.KEY_UP: {
				Module mod = ModuleManager
						.getModsInCategory(Module.Category.values()[Tab.categoryTab]).get(Tab.modTab);
				Option op = mod.getOptions().get(Tab.optionTab);
				NumberOption value = (NumberOption) op;
				((NumberOption) op).increment();
			}
				break;
			}
		}
	}

	private static void animateBars() {
		final long timeDifference = System.nanoTime() / 1000000L - Tab.animateTime;
		Tab.animateTime = System.nanoTime() / 1000000L;
		int increment = animateFast ? 80 : 30;
		increment = Math.max(1, Math.round(increment * timeDifference / 100L));
		if (Tab.categoryY < Tab.categoryTargetY) {
			Tab.categoryY += increment;
			if (Tab.categoryY >= Tab.categoryTargetY) {
				Tab.categoryY = Tab.categoryTargetY;
			}
		} else if (Tab.categoryY > Tab.categoryTargetY) {
			Tab.categoryY -= increment;
			if (Tab.categoryY <= Tab.categoryTargetY) {
				Tab.categoryY = Tab.categoryTargetY;
			}
		}
		if (Tab.modY < Tab.modTargetY) {
			Tab.modY += increment;
			if (Tab.modY >= Tab.modTargetY) {
				Tab.modY = Tab.modTargetY;
			}
		} else if (Tab.modY > Tab.modTargetY) {
			Tab.modY -= increment;
			if (Tab.modY <= Tab.modTargetY) {
				Tab.modY = Tab.modTargetY;
			}
		}
		if (Tab.optionY < Tab.optionTargetY) {
			Tab.optionY += increment;
			if (Tab.optionY >= Tab.optionTargetY) {
				Tab.optionY = Tab.optionTargetY;
			}
		} else if (Tab.optionY > Tab.optionTargetY) {
			Tab.optionY -= increment;
			if (Tab.optionY <= Tab.optionTargetY) {
				Tab.optionY = Tab.optionTargetY;
			}
		}
		if (Tab.valueY < Tab.valueTargetY) {
			Tab.valueY += increment;
			if (Tab.valueY >= Tab.valueTargetY) {
				Tab.valueY = Tab.valueTargetY;
			}
		} else if (Tab.valueY > Tab.valueTargetY) {
			Tab.valueY -= increment;
			if (Tab.valueY <= Tab.valueTargetY) {
				Tab.valueY = Tab.valueTargetY;
			}
		}
	}

	private enum Section {
		TYPES, MODULES, OPTIONS, VALUE;
	}
	public static void updateFade() {
        if (Tab.fadeState >= 20 || Tab.fadeState <= 0) {
            Tab.goingUp = !Tab.goingUp;
        }
        if (Tab.goingUp) {
            ++Tab.fadeState;
        }
        else {
            --Tab.fadeState;
        }
        final double ratio = Tab.fadeState / 20.0;
        Tab.currentColor = getFadeHex(-23614, -3394561, ratio);
    }
    
    private static int getFadeHex(final int hex1, final int hex2, final double ratio) {
        int r = hex1 >> 16;
        int g = hex1 >> 8 & 0xFF;
        int b = hex1 & 0xFF;
        r += (int)(((hex2 >> 16) - r) * ratio);
        g += (int)(((hex2 >> 8 & 0xFF) - g) * ratio);
        b += (int)(((hex2 & 0xFF) - b) * ratio);
        return r << 16 | g << 8 | b;

}
}
