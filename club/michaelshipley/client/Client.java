// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.Display;

import club.michaelshipley.client.command.CommandManager;
import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.gui.account.AccountScreen;
import club.michaelshipley.client.gui.click.ClickGui;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.view.hud.tabgui.Tab;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.MCStencil;

public final class Client
{
    public static String clientName = "Disco Party";
    public static double clientVersion = 4.7;
    public static AccountScreen accountScreen;
    
    public static String capeUrl = "http://i.imgur.com/WaTyDUd.png";
    
    public void start() {
    	ClientUtils.loadClientFont();
        ModuleManager.start();
        CommandManager.start();
        OptionManager.start();
        FriendManager.start();
        ClickGui.start();
        MCStencil.checkSetupFBO();
        Tab.init();
        
        Display.setTitle(Client.clientName + " v" + Client.clientVersion + " | Created by Michael in association with Phase Squad and NyxGriefing");
        
        Runtime.getRuntime().addShutdownHook(new Thread("gay shutdown thead") {
            public void run() {
                OptionManager.save();
                ModuleManager.save();
            }
        });
    }
    
}
