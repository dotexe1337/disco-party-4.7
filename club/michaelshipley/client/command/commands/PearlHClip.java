package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.client.module.modules.move.speed.Bypasshop;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.Timer;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.MathHelper;

@Com(names = { "phclip" })
public class PearlHClip extends Command
{
    @Override
    public void runCommand(final String[] args) {
    	float distance = Float.parseFloat(args[1]);
    	ClientUtils.mc().thePlayer.posX = ClientUtils.mc().thePlayer.posX + (-MathHelper.sin(Bypasshop.getDirection()) * distance);
        ClientUtils.mc().thePlayer.posZ = ClientUtils.player().posZ + (MathHelper.cos(Bypasshop.getDirection()) * distance);
        if(ClientUtils.mc().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemEnderPearl)
            ClientUtils.mc().playerController.sendUseItem(ClientUtils.mc().thePlayer,ClientUtils.mc().theWorld, ClientUtils.mc().thePlayer.getCurrentEquippedItem());
        Timer timer = new Timer();
        //if (timer.delay(0)){
            
        //}
    }
    
    @Override
    public String getHelp() {
        return "Pearl HClip - phclip <n> - Uses an ender pearl to hclip <n> blocks";
    }
}
