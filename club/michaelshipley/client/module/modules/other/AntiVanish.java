package club.michaelshipley.client.module.modules.other;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.UUID;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.Event.State;
import club.michaelshipley.event.events.PacketReceiveEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.ChatComponentText;

@Mod(displayName = "Anti Vanish")
public class AntiVanish extends Module {
	
	
    private ArrayList<UUID> vanished;
    private int delay;
    
    @EventTarget
    public void onReceivePacket(final PacketReceiveEvent e) {
            final Minecraft mc = ClientUtils.mc();
            if (ClientUtils.mc().theWorld != null && e.getPacket() instanceof S38PacketPlayerListItem) {
                final S38PacketPlayerListItem listItem = (S38PacketPlayerListItem)e.getPacket();
                if (listItem.func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
                    for (final Object o : listItem.func_179767_a()) {
                        final S38PacketPlayerListItem.AddPlayerData data = (S38PacketPlayerListItem.AddPlayerData)o;
                        if (ClientUtils.mc().getNetHandler().func_175102_a(data.field_179964_d.getId()) == null && !this.checkList(data.field_179964_d.getId())) {
                            ClientUtils.sendMessage(this.getName(data.field_179964_d.getId()) + "is vanished.");
                        }
                    }
                }
            }
    }
   
    @EventTarget(0)
    public void preTick(final UpdateEvent e) {
    	if(e.getState() == State.PRE) {
    		final StringBuilder sb = new StringBuilder("");
            final Minecraft mc = ClientUtils.mc();
            this.setSuffix(sb.append(this.vanished.size()).toString());
            for (final UUID uuid : this.vanished) {
                if (ClientUtils.mc().getNetHandler().func_175102_a(uuid) != null) {
                    ClientUtils.sendMessage(this.getName(uuid) + "is no longer vanished.");
                    this.vanished.remove(uuid);
                }
            }
    	}
    }
   
    public String getName(final UUID uuid) {
        try {
            final URL url = new URL("https://namemc.com/profile/" + uuid.toString());
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String name = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<title>")) {
                    name = line.split("§")[0].trim().replaceAll("<title>", "").replaceAll("</title>", "").replaceAll("\u2013 Minecraft Profile \u2013 NameMC", "").replaceAll("\u00e2\u20ac\u201c Minecraft Profile \u00e2\u20ac\u201c NameMC", "");
                }
            }
            reader.close();
            return name;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "(Failed) " + uuid;
        }
    }
   
    private boolean checkList(final UUID uuid) {
        if (this.vanished.contains(uuid)) {
            this.vanished.remove(uuid);
            return true;
        }
        this.vanished.add(uuid);
        return false;
    }
	
}
