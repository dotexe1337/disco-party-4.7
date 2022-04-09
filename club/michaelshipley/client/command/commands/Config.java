package club.michaelshipley.client.command.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.client.option.types.BooleanOption;
import club.michaelshipley.client.option.types.NumberOption;
import club.michaelshipley.client.option.types.StringOption;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.FileUtils;

//Made by _Jaimeslespates credits me faggot
@Com(names = { "config", "cfg" })
public class Config extends Command {

 public static File CONFIG_DIR;

 @Override
 public void runCommand(final String[] args) {
     if (args.length < 3) {
         ClientUtils.sendMessage(this.getHelp());
         return;
     }
     if (args[1].equalsIgnoreCase("load") || args[1].equalsIgnoreCase("use")) {
         File CONFIG_DIR = FileUtils.getConfigFile(args[2].toString().toUpperCase());
         final List<String> fileContent = FileUtils.read(CONFIG_DIR);
         ClientUtils.sendMessage("Sucessfuly loaded the Config §9" + args[2].toUpperCase());
         for (final String line : fileContent) {
             try {
                 final String[] split = line.split(":");
                 final String optionId = split[0];
                 final String optionValue = split[1];
                 final String modId = split[2];
                 final Option option = OptionManager.getOption(optionId, split[2]);
                 if (option == null) {
                     continue;
                 }
                 if (option instanceof NumberOption) {
                     ((NumberOption)option).setValue(optionValue);
                 }
                 else if (option instanceof BooleanOption) {
                     ((BooleanOption)option).setValueHard(Boolean.parseBoolean(optionValue));
                 }
                 else {
                     if (!(option instanceof StringOption)) {
                         continue;
                     }
                     option.setValue(optionValue);
                 }
             }
             catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     else if (args[1].equalsIgnoreCase("save") || args[1].equalsIgnoreCase("create")) {
         File CONFIG_DIR = FileUtils.getConfigFile(args[2].toString().toUpperCase());
         ClientUtils.sendMessage("Sucessfuly created the Config §9" + args[2].toUpperCase());
         final List<String> fileContent = new ArrayList<String>();
         for(final Option option : OptionManager.optionList){
             final String optionId = option.getId();
             final String optionVal = option.getValue().toString();
             final Module mod = option.getModule();
             fileContent.add(String.format("%s:%s:%s", optionId, optionVal, mod.getId()));
         }
         FileUtils.write(CONFIG_DIR, fileContent, true);
     }
     else if (args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("delete")) {
         File CONFIG_DIR = FileUtils.getConfigFile(args[2].toString());
         CONFIG_DIR.delete();
         ClientUtils.sendMessage("Sucessfuly deleted the Config §9" + args[2].toUpperCase());
     }
     else {
         ClientUtils.sendMessage(this.getHelp());
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

 @Override
 public String getHelp() {
     return "Config - Manager your config for a anticheat Usage: config [create/load/delete] [name]";
 }

}