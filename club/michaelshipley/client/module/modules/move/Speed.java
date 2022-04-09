// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.module.modules.move;

import org.lwjgl.input.Keyboard;

import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.module.ModuleManager;
import club.michaelshipley.client.module.modules.move.speed.AACBhop;
import club.michaelshipley.client.module.modules.move.speed.Arenabrawl;
import club.michaelshipley.client.module.modules.move.speed.Bhop;
import club.michaelshipley.client.module.modules.move.speed.Bypasshop;
import club.michaelshipley.client.module.modules.move.speed.ColdNetwork;
import club.michaelshipley.client.module.modules.move.speed.CosmicBhop;
import club.michaelshipley.client.module.modules.move.speed.GuardianBhop;
import club.michaelshipley.client.module.modules.move.speed.GuardianEvenstupiderhop;
import club.michaelshipley.client.module.modules.move.speed.GuardianStupiderhop;
import club.michaelshipley.client.module.modules.move.speed.GuardianStupidhop;
import club.michaelshipley.client.module.modules.move.speed.GuardianYPort;
import club.michaelshipley.client.module.modules.move.speed.Jump;
import club.michaelshipley.client.module.modules.move.speed.Latest;
import club.michaelshipley.client.module.modules.move.speed.Lowhop;
import club.michaelshipley.client.module.modules.move.speed.MinemanClub;
import club.michaelshipley.client.module.modules.move.speed.Mineverge;
import club.michaelshipley.client.module.modules.move.speed.Minez;
import club.michaelshipley.client.module.modules.move.speed.NCP;
import club.michaelshipley.client.module.modules.move.speed.PvPLounge;
import club.michaelshipley.client.module.modules.move.speed.Strafe;
import club.michaelshipley.client.module.modules.move.speed.Stupiderhop;
import club.michaelshipley.client.module.modules.move.speed.Stupidhop;
import club.michaelshipley.client.module.modules.move.speed.Vanilla;
import club.michaelshipley.client.option.Option;
import club.michaelshipley.client.option.OptionManager;
import club.michaelshipley.event.EventTarget;
import club.michaelshipley.event.events.MoveEvent;
import club.michaelshipley.event.events.TickEvent;
import club.michaelshipley.event.events.UpdateEvent;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.LiquidUtils;
import net.minecraft.potion.Potion;
@Mod
public class Speed extends Module
{
    public Latest latest;
    public Bhop bhop;
    public Lowhop lowhop;
    public Bypasshop bypasshop;
    public NCP ncp;
    private Vanilla vanilla;
    private Minez minez;
    private Jump fuckingcool;
    private Arenabrawl arenabrawl;
    private Strafe strafe;
    private GuardianBhop guardianBhop;
    private GuardianYPort guardianYPort;
    private GuardianStupidhop guardianStupidhop;
    private GuardianStupiderhop guardianStupiderhop;
    private GuardianEvenstupiderhop guardianEvenstupiderhop;
    private CosmicBhop cosmicBhop;
    private AACBhop aacBhop;
    private MinemanClub minemanClub;
    private ColdNetwork coldNetwork;
    private PvPLounge pvpLounge;
    private Mineverge mineverse;
    private Stupidhop stupidHop;
    private Stupiderhop stupiderHop;
    @Option.Op(min = 0.2, max = 10.0, increment = 0.05)
    public static double speed;
    
    public Speed() {
        this.latest = new Latest("Latest", false, this);
        this.bhop = new Bhop("Bhop", false, this);
        this.bypasshop = new Bypasshop("Bypasshop", false, this);
        this.ncp = new NCP("NCP", false, this);
        this.strafe = new Strafe("Strafe", true, this);
        this.lowhop = new Lowhop("Lowhop", false, this);
        this.vanilla = new Vanilla("Vanilla", false, this);
        this.minez = new Minez("Minez", false, this);
        this.fuckingcool = new Jump("Jump", false, this);
        this.arenabrawl = new Arenabrawl("Arenabrawl", false, this);
        this.guardianBhop = new GuardianBhop("Guardian Bhop", false, this);
        this.guardianYPort = new GuardianYPort("Guardian YPort", false, this);
        this.guardianStupidhop = new GuardianStupidhop("Guardian Stupidhop", false, this);
        this.guardianStupiderhop = new GuardianStupiderhop("Guardian Stupiderhop", false, this);
        this.guardianEvenstupiderhop = new GuardianEvenstupiderhop("Guardian Evenstupiderhop", false, this);
        this.cosmicBhop = new CosmicBhop("Cosmic Bhop", false, this);
        this.aacBhop = new AACBhop("AAC Bhop", false, this);
        this.minemanClub = new MinemanClub("Mineman Club", false, this);
        this.coldNetwork = new ColdNetwork("Cold Network", false, this);
        this.pvpLounge = new PvPLounge("PvPLounge", false, this);
        this.mineverse = new Mineverge("Mineverge", false, this);
        this.stupidHop = new Stupidhop("Stupidhop", false, this);
        this.stupiderHop = new Stupiderhop("Stupiderhop", false, this);
        this.speed = 0.5;
        this.setKeybind(Keyboard.KEY_F);
    }
    
    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.latest);
        OptionManager.getOptionList().add(this.bhop);
        OptionManager.getOptionList().add(this.bypasshop);
        OptionManager.getOptionList().add(this.ncp);
        OptionManager.getOptionList().add(this.strafe);
        OptionManager.getOptionList().add(this.lowhop);
        OptionManager.getOptionList().add(this.vanilla);
        OptionManager.getOptionList().add(this.minez);
        OptionManager.getOptionList().add(this.fuckingcool);
        OptionManager.getOptionList().add(this.arenabrawl);
        OptionManager.getOptionList().add(this.guardianBhop);
        OptionManager.getOptionList().add(this.guardianYPort);
        OptionManager.getOptionList().add(this.guardianStupidhop);
        OptionManager.getOptionList().add(this.guardianStupiderhop);
        OptionManager.getOptionList().add(this.guardianEvenstupiderhop);
        OptionManager.getOptionList().add(this.cosmicBhop);
        OptionManager.getOptionList().add(this.aacBhop);
        OptionManager.getOptionList().add(this.minemanClub);
        OptionManager.getOptionList().add(this.coldNetwork);
        OptionManager.getOptionList().add(this.pvpLounge);
        OptionManager.getOptionList().add(this.mineverse);
        OptionManager.getOptionList().add(this.stupidHop);
        OptionManager.getOptionList().add(this.stupiderHop);
        this.updateSuffix();
        super.preInitialize();
    }
    
    @Override
    public void enable() {
        this.latest.enable();
        this.lowhop.enable();
        this.bhop.enable();
        this.strafe.enable();
        this.ncp.enable();
        this.vanilla.enable();
        this.minez.enable();
        this.fuckingcool.enable();
        this.arenabrawl.enable();
        this.cosmicBhop.enable();
        this.aacBhop.enable();
        this.minemanClub.enable();
        this.coldNetwork.enable();
        this.pvpLounge.enable();
        this.stupidHop.enable();
        this.stupiderHop.enable();
        super.enable();
    }
    
    @Override
    public void disable() {
    	ClientUtils.mc().timer.timerSpeed = 1.0f;
    	this.mineverse.disable();
    	super.disable();
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
    	if(!LiquidUtils.isOnLiquid() && !LiquidUtils.isInLiquid() && !ModuleManager.getModule("Fly").isEnabled()) {
            this.latest.onMove(event);
            this.lowhop.onMove(event);
            this.bhop.onMove(event);
            this.bypasshop.onMove(event);
            this.ncp.onMove(event);
            this.strafe.onMove(event);
            this.vanilla.onMove(event);
            this.minez.onMove(event);
            this.fuckingcool.onMove(event);
            this.arenabrawl.onMove(event);
            this.cosmicBhop.onMove(event);
            this.minemanClub.onMove(event);
            this.coldNetwork.onMove(event);
            this.pvpLounge.onMove(event);
            this.stupidHop.onMove(event);
            this.stupiderHop.onMove(event);
    	}
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if(!LiquidUtils.isOnLiquid() && !LiquidUtils.isInLiquid() && !ModuleManager.getModule("Fly").isEnabled()) {
        	this.latest.onUpdate(event);
            this.lowhop.onUpdate(event);
            this.bhop.onUpdate(event);
            this.bypasshop.onUpdate(event);
            this.ncp.onUpdate(event);
            this.strafe.onUpdate(event);
            this.vanilla.onUpdate(event);
            this.minez.onUpdate(event);
            this.fuckingcool.onUpdate(event);
            this.guardianBhop.onUpdate(event);
            this.guardianYPort.onUpdate(event);
            this.guardianStupidhop.onUpdate(event);
            this.guardianStupiderhop.onUpdate(event);
            this.guardianEvenstupiderhop.onUpdate(event);
            this.cosmicBhop.onUpdate(event);
            this.aacBhop.onUpdate(event);
            this.minemanClub.onUpdate(event);
            this.coldNetwork.onUpdate(event);
            this.pvpLounge.onUpdate(event);
            this.mineverse.onUpdate(event);
            this.stupidHop.onUpdate(event);
            this.stupiderHop.onUpdate(event);
        }
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
    
    private void updateSuffix() {
        if (this.latest.getValue()) {
            this.setSuffix("Latest");
        }
        else if (this.lowhop.getValue()) {
            this.setSuffix("Lowhop");
        }
        else if (this.bhop.getValue()) {
            this.setSuffix("Bhop");
        }
        else if (this.bypasshop.getValue()) {
            this.setSuffix("Bypasshop");
        }
        else if (this.ncp.getValue()) {
        	this.setSuffix("NCP");
        }
         else if (this.strafe.getValue()) {
            this.setSuffix("Strafe");
        }
        else if (this.vanilla.getValue()) {
            this.setSuffix("Vanilla");
        }
        else if (this.arenabrawl.getValue()) {
            this.setSuffix("Arenabrawl");
        }
        else if (this.fuckingcool.getValue()) {
            this.setSuffix("Jump");
        }
        else if (this.guardianBhop.getValue()) {
        	this.setSuffix("Guardian Bhop");
        }
        else if (this.guardianYPort.getValue()) {
        	this.setSuffix("Guardian YPort");
        }
        else if (this.guardianStupidhop.getValue()) {
        	this.setSuffix("Guardian Stupidhop");
        }
        else if (this.guardianStupiderhop.getValue()) {
        	this.setSuffix("Guardian Stupiderhop");
        }
        else if (this.guardianEvenstupiderhop.getValue()) {
        	this.setSuffix("Guardian Evenstupiderhop");
        }
        else if(this.cosmicBhop.getValue()) {
        	this.setSuffix("Cosmic Bhop");
        }
        else if(this.aacBhop.getValue()) {
        	this.setSuffix("AAC Bhop");
        }
        else if(this.minemanClub.getValue()) {
        	this.setSuffix("Mineman Club");
        }
        else if(this.coldNetwork.getValue()) {
        	this.setSuffix("Cold Network");
        }
        else if(this.mineverse.getValue()) {
        	this.setSuffix("Mineverge");
        }
        else if(this.pvpLounge.getValue()) {
        	this.setSuffix("PvPLounge");
        }
        else if (this.stupiderHop.getValue()) {
        	this.setSuffix("Stupiderhop");
        }
        else if (this.stupidHop.getValue()) {
        	this.setSuffix("Stupidhop");
        }
        else {
            this.setSuffix("Unknown");
        }
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            final int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
