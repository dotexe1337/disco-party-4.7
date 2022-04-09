// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import club.michaelshipley.event.EventManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.KeyPressEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.FileUtils;

public final class ModuleManager
{
    private static final File MODULE_DIR;
    public static List<Module> moduleList;
    
    static {
        MODULE_DIR = FileUtils.getConfigFile("Mods");
        ModuleManager.moduleList = new ArrayList<Module>();
    }
    
    public static void start() {
        final Reflections reflections = new Reflections("club.michaelshipley.client.module.modules", new Scanner[0]);
        final Set<Class<? extends Module>> classes = (Set<Class<? extends Module>>)reflections.getSubTypesOf((Class)Module.class);
        for (final Class<? extends Module> clazz : classes) {
            try {
                final Module loadedModule = (Module)clazz.newInstance();
                if (!clazz.isAnnotationPresent(Module.Mod.class)) {
                    continue;
                }
                final Module.Mod modAnnotation = clazz.getAnnotation(Module.Mod.class);
                loadedModule.setProperties(clazz.getSimpleName(), modAnnotation.displayName().equals("null") ? clazz.getSimpleName() : modAnnotation.displayName(), Module.Category.valueOf(StringUtils.capitalize(clazz.getPackage().getName().split("modules.")[1])), modAnnotation.keybind(), modAnnotation.suffix(), modAnnotation.shown());
                if (modAnnotation.enabled()) {
                    loadedModule.enable();
                }
                ModuleManager.moduleList.add(loadedModule);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    	
        ModuleManager.moduleList.sort(new Comparator<Module>() {
            @Override
            public int compare(final Module m1, final Module m2) {
                final String s1 = m1.getDisplayName();
                final String s2 = m2.getDisplayName();
                return s1.compareTo(s2);
            }
        });
        load();
        save();
        EventManager.register(new ModuleManager());
    }
    
    @EventTarget
    private void onKeyBoard(final KeyPressEvent event) {
        for (final Module mod : ModuleManager.moduleList) {
            if (event.getKey() == mod.getKeybind()) {
                mod.toggle();
                save();
            }
        }
    }
    
    public static Module getModule(final String modName) {
        for (final Module module : ModuleManager.moduleList) {
            if (module.getId().equalsIgnoreCase(modName) || module.getDisplayName().equalsIgnoreCase(modName)) {
                return module;
            }
        }
        final Module empty = new Module();
        empty.setProperties("Null", "Null", null, -1, null, false);
        return empty;
    }
    
    public static Module getModule(final Class meme) {
        for (final Module module : ModuleManager.moduleList) {
            if (module.equals(meme)) {
                return module;
            }
        }
        final Module empty = new Module();
        empty.setProperties("Null", "Null", null, -1, null, false);
        return empty;
    }
    
    public static void load() {
        try {
            final List<String> fileContent = FileUtils.read(ModuleManager.MODULE_DIR);
            for (final String line : fileContent) {
                final String[] split = line.split(":");
                final String id = split[0];
                final String displayName = split[1];
                final String shown = split[2];
                final String keybind = split[3];
                final String strEnabled = split[4];
                final Module module = getModule(id);
                module.setProperties(module.getId(), module.getDisplayName(), module.getCategory(), module.getKeybind(), module.getSuffix(), module.isShown());
                if (module.getId().equalsIgnoreCase("null")) {
                    continue;
                }
                final int moduleKeybind = keybind.equalsIgnoreCase("null") ? -1 : Integer.parseInt(keybind);
                final boolean enabled = Boolean.parseBoolean(strEnabled);
                final boolean visible = Boolean.parseBoolean(shown);
                if (enabled != module.isEnabled()) {
                    module.toggle();
                }
                module.setShown(visible);
                //module.setDisplayName(displayName);
                module.setKeybind(moduleKeybind);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void save() {
        final List<String> fileContent = new ArrayList<String>();
        for (final Module module : ModuleManager.moduleList) {
            final String id = module.getId();
            final String displayName = module.getDisplayName();
            final String visible = Boolean.toString(module.isShown());
            final String moduleKey = (module.getKeybind() <= 0) ? "null" : Integer.toString(module.getKeybind());
            final String enabled = Boolean.toString(module.isEnabled());
            fileContent.add(String.format("%s:%s:%s:%s:%s", id, displayName, visible, moduleKey, enabled));
        }
        FileUtils.write(ModuleManager.MODULE_DIR, fileContent, true);
    }
    
    public static List<Module> getModsInCategory(final Module.Category category) {
        final List<Module> modList = new ArrayList<Module>();
        for (final Module mod : ModuleManager.getModules()) {
            if (mod.getCategory() == category) {
                modList.add(mod);
            }
        }
        modList.sort(new Comparator<Module>() {
            @Override
            public int compare(final Module m1, final Module m2) {
                final String s1 = m1.getDisplayName();
                final String s2 = m2.getDisplayName();
                return s1.compareTo(s2);
            }
        });
        return modList;
    }
    
    public static List<Module> getModules() {
        return ModuleManager.moduleList;
    }
    
    public static List<Module> getModulesForRender() {
        final List<Module> renderList = ModuleManager.moduleList;
        renderList.sort(new Comparator<Module>() {
            @Override
            public int compare(final Module m1, final Module m2) {
                final String s1 = String.format("%s" + ((m1.getSuffix().length() > 0) ? " §r%s" : ""), m1.getDisplayName(), m1.getSuffix());
                final String s2 = String.format("%s" + ((m2.getSuffix().length() > 0) ? " §r%s" : ""), m2.getDisplayName(), m2.getSuffix());
                return ClientUtils.clientFont().getStringWidth(s2) - ClientUtils.clientFont().getStringWidth(s1);
            }
        });
        return renderList;
    }
}
