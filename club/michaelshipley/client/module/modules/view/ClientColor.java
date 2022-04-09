package club.michaelshipley.client.module.modules.view;


import club.michaelshipley.client.module.Module;
import club.michaelshipley.client.module.Module.Mod;
import club.michaelshipley.client.option.Option.Op;

@Mod(displayName = "Client Color")
public class ClientColor extends Module {
	
	@Op(min = 0.0, max = 255.0, increment = 5.0, name = "Red")
    public static double red;
    @Op(min = 0.0, max = 255.0, increment = 5.0, name = "Green")
    public static double green;
    @Op(min = 0.0, max = 255.0, increment = 5.0, name = "Blue")
    public static double blue;
    
    public ClientColor() {
        super();
        ClientColor.red = 0.0;
        ClientColor.green = 255.0;
        ClientColor.red = 0.0;
    }
	
}
