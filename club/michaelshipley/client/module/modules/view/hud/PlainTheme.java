// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.view.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import club.michaelshipley.client.Client;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.view.ClientColor;
import club.michaelshipley.client.module.modules.view.Hud;
import club.michaelshipley.event.events.RenderTextEvent;
import club.michaelshipley.timer.Timer;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.FontManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class PlainTheme extends Theme
{
	
    public PlainTheme(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    static Map<String, Module> modules = new LinkedHashMap();
    
    private static int currentColor;
    private static int fadeState;
    private static boolean goingUp;
    
    private List<String> enabled()
    {
      List<String> list = new ArrayList();
      for (String s : modules.keySet())
      {
        Module module = modules.get(s);
        if(module.isEnabled()) {
        	list.add(s);
        }
      }
      return list;
    }
    
	public static Color setRainbow(long offset, float fade) {
		float hue = (float) (System.nanoTime() * -5 + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 0.3F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed()/255.0F*fade, c.getGreen()/255.0F*fade, c.getBlue()/255.0F*fade);
	}
	
	Timer timer = new Timer();
    
    @Override
    public boolean onRender(final RenderTextEvent event) {
        if (super.onRender(event)) {
        	boolean beta = true;
        	ClientUtils.mc().fontRendererObj.func_175063_a(Client.clientName, 2, 2, new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB());
        	//ClientUtils.mc().fontRendererObj.drawStringWithShadow("\2477v" + Client.clientVersion, ClientUtils.mc().fontRendererObj.getStringWidth(Client.clientName) + 4, 2, 0xffffff);
        	int y = 2;
            if(Hud.direction) {
            	EnumFacing yaw = EnumFacing.getHorizontal(MathHelper.floor_double((double) (ClientUtils.mc().thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
                String displayString = yaw.getName().substring(0, 1).toUpperCase() + yaw.getName().substring(1);
                ClientUtils.mc().fontRendererObj.func_175063_a(displayString, (event.getWidth() - ClientUtils.mc().fontRendererObj.getStringWidth(displayString)) / 2, (BossStatus.statusBarTime > 0 ? 20 : 2), 0xffffff);
            }
            int x2 = event.getWidth() - 2;
            int y2 = event.getHeight() - 12;
            if(Hud.potions) {
                if (ClientUtils.mc().ingameGUI.getChatGUI().getChatOpen())
                    y2 -= 16;
                for (Object o : ClientUtils.mc().thePlayer.getActivePotionEffects()) {
                    PotionEffect effect = (PotionEffect) o;
                    String name = I18n.format(effect.getEffectName());
                    

                    if (effect.getAmplifier() == 1)
                        name = name + " " + "2";
                    else if (effect.getAmplifier() == 2)
                        name = name + " " + "3";
                    else if (effect.getAmplifier() == 3)
                        name = name + " " + "4";
                    else if (effect.getAmplifier() > 0)
                        name = name + " " + (effect.getAmplifier() + 1);

                    int var1 = effect.getDuration() / 20;
                    int var2 = var1 / 60;
                    var1 %= 60;
                    char color = 'f';
                    if (var2 == 0)
                        if (var1 <= 5)
                            color = '4';
                        else if (var1 <= 10)
                            color = 'c';
                        else if (var1 <= 15)
                            color = '6';
                        else if (var1 <= 20)
                            color = 'e';
                    
                    String maymay = String.format("%s %s", name, Potion.getDurationString(effect));;
                    name = String.format("%s \247%s%s", name, color, Potion.getDurationString(effect));
                    ClientUtils.mc().fontRendererObj.func_175063_a(name, x2 - ClientUtils.mc().fontRendererObj.getStringWidth(maymay), y2, new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB());
                    y2 -= 10;
                }
            }
            if(Hud.coords) {
            	ClientUtils.mc().fontRendererObj.func_175063_a("XYZ \247f" + MathHelper.floor_double(ClientUtils.player().posX) + ", " + MathHelper.floor_double(ClientUtils.player().posY) + ", " + MathHelper.floor_double(ClientUtils.player().posZ), 2, event.getHeight() - 12, new Color((int) ClientColor.red, (int) ClientColor.green, (int) ClientColor.blue).getRGB());
            }
            List<String> list = this.enabled();
            for (final Module mod : getModulesForRender()) {
                if (mod.isEnabled() && mod.isShown()) {
        			//Gui.drawRect(event.getWidth() - (int) ClientUtils.mc().fontRendererObj.getWidth(String.format("%s" + ((mod.getSuffix().length() > 0) ? " \247f%s" : ""), mod.getDisplayName(), mod.getSuffix())) - 3, y + 10, event.getWidth(), y, 0x7d24eb);
                    //Gui.drawRect(event.getWidth() - (int) ClientUtils.mc().fontRendererObj.getWidth(String.format("%s" + ((mod.getSuffix().length() > 0) ? " \2477[%s]" : ""), mod.getDisplayName(), mod.getSuffix())) - 6, y, event.getWidth(), y+10, new Color(0, 0, 0, 100).getRGB());
                    //Gui.drawRect(event.getWidth() - 2, y, event.getWidth(), y+10, setRainbow(100000000L * ModuleManager.moduleList.indexOf(mod), 1.0F).getRGB());
                    ClientUtils.mc().fontRendererObj.func_175063_a(String.format("%s" + ((mod.getSuffix().length() > 0) ? " \247f%s" : ""), mod.getDisplayName(), mod.getSuffix()), event.getWidth() - ClientUtils.mc().fontRendererObj.getStringWidth(String.format("%s" + ((mod.getSuffix().length() > 0) ? " \2477%s" : ""), mod.getDisplayName(), mod.getSuffix())) - 2, y + 2, setRainbow(100000000L * ModuleManager.moduleList.indexOf(mod), 1.0F).getRGB());
                    y += 10;
                }
            }
        }
        return super.onRender(event);
    }
    
    public static List<Module> getModulesForRender() {
        final List<Module> renderList = ModuleManager.moduleList;
        renderList.sort(new Comparator<Module>() {
            @Override
            public int compare(final Module m1, final Module m2) {
                final String s1 = String.format("%s" + ((m1.getSuffix().length() > 0) ? " %s" : ""), m1.getDisplayName(), m1.getSuffix());
                final String s2 = String.format("%s" + ((m2.getSuffix().length() > 0) ? " %s" : ""), m2.getDisplayName(), m2.getSuffix());
                return ClientUtils.mc().fontRendererObj.getStringWidth(s2) - ClientUtils.mc().fontRendererObj.getStringWidth(s1);
            }
        });
        return renderList;
    }
    
}
