// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventManager;
import club.michaelshipley.utils.ClientUtils;

public class Module
{
    private String id;
    private String displayName;
    public boolean enabled;
    private Category category;
    private int keybind;
    private String suffix;
    private boolean shown;
    public static int animation;
    
    public void setProperties(final String id, final String displayName, final Category type, final int keybind, final String suffix, final boolean shown) {
        this.id = id;
        this.displayName = displayName;
        this.category = type;
        this.keybind = keybind;
        this.suffix = suffix;
        this.shown = shown;
    }
    
    public void preInitialize() {
    }
    
    public void toggle() {
    	this.onToggle();
        if (this.enabled) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public void enable() {
        this.enabled = true;
        EventManager.register(this);
    }
    
    public void disable() {
        this.enabled = false;
        EventManager.unregister(this);
    }
    
    public void onToggle() {
    	
    }
    
    public List<Option> getOptions() {
        final List<Option> optionList = new ArrayList<Option>();
        for (final Option option : OptionManager.getOptionList()) {
            if (option.getModule().equals(this)) {
                optionList.add(option);
            }
        }
        return optionList;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public boolean drawDisplayName(final float x, final float y, final int color) {
        if (this.enabled && this.shown) {
            ClientUtils.clientFont().drawStringWithShadow(String.format("%s" + ((this.suffix.length() > 0) ? " \247f7[%s]" : ""), this.displayName, this.suffix).toLowerCase(), x, y, color);
            return true;
        }
        return false;
    }
    
    public boolean drawDisplayName(final float x, final float y) {
        if (this.enabled && this.shown) {
            ClientUtils.clientFont().drawStringWithShadow(String.format("%s" + ((this.suffix.length() > 0) ? " \247f7[%s]" : ""), this.displayName, this.suffix).toLowerCase(), x, y, getColor());
            return true;
        }
        return false;
    }
    
    private int getColor() {
        switch (this.category) {
            case Fight: {
                return -4042164;
            }
            case View: {
                return -1781619;
            }
            case Move: {
                return -4927508;
            }
            case Other: {
                return -8921737;
            }
            case You: {
                return -8921737;
            }
            default: {
                return -1;
            }
        }
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getKeybind() {
        return this.keybind;
    }
    
    public void setKeybind(final int keybind) {
        this.keybind = keybind;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    public boolean isShown() {
        return this.shown;
    }
    
    public void setShown(final boolean shown) {
        this.shown = shown;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public Module getInstance() {
        for (final Module mod : ModuleManager.getModules()) {
            if (mod.getClass().equals(this.getClass())) {
                return mod;
            }
        }
        return null;
    }
    
    public enum Category
    {
        Fight("Fight", 0), 
        View("View", 1), 
        Move("Move", 2), 
        You("You", 3), 
        Other("Other", 4);
        
        private Category(final String s, final int n) {
        }
    }
    
    public void dankMemeSetupMods() {
    	
    }
    
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Mod {
        public String displayName() default "null";
        
        public boolean enabled() default false;
        
        public int keybind() default -1;
        
        public boolean shown() default true;
        
        public String suffix() default "";
    }

    public void postInitialize() {
    }
}
