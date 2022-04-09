package club.michaelshipley.client.command.commands;

import club.michaelshipley.client.command.Com;
import club.michaelshipley.client.command.Command;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.Timer;
import net.minecraft.item.ItemEnderPearl;

@Com(names = { "pclip" })
public class Pearlclip extends Command
{
    @Override
    public void runCommand(final String[] args) {
    	float distance = Float.parseFloat(args[1]);
        ClientUtils.mc().thePlayer.setPosition(ClientUtils.mc().thePlayer.posX, ClientUtils.mc().thePlayer.posY + distance, ClientUtils.mc().thePlayer.posZ);
        Timer timer = new Timer();
        if (timer.delay(3)){
            if(ClientUtils.mc().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemEnderPearl)
            ClientUtils.mc().playerController.sendUseItem(ClientUtils.mc().thePlayer,ClientUtils.mc().theWorld, ClientUtils.mc().thePlayer.getCurrentEquippedItem());
        }
    }
    
    @Override
    public String getHelp() {
        return "Pearl Clip - pclip <n> - Uses an ender pearl to vclip <n> blocks";
    }
}
