// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.other;

import club.michaelshipley.client.friend.FriendManager;
import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.MouseEvent;
import club.michaelshipley.utils.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
@Mod(displayName = "Middle Click Friend")
public class MiddleClickFriend extends Module
{
    @EventTarget
    private void onMouseClick(final MouseEvent event) {
        if (event.getKey() == 2 && ClientUtils.mc().objectMouseOver != null && ClientUtils.mc().objectMouseOver.entityHit != null && ClientUtils.mc().objectMouseOver.entityHit instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)ClientUtils.mc().objectMouseOver.entityHit;
            final String name = player.getName();
            if (FriendManager.isFriend(name)) {
                FriendManager.removeFriend(name);
                ClientUtils.sendMessage("Removed " + name);
            }
            else {
                FriendManager.addFriend(name, name);
                ClientUtils.sendMessage("Added " + name);
            }
        }
    }
}
