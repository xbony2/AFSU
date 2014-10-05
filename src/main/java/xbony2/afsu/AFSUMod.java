package xbony2.afsu;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "AFSU", name = "AFSU Mod", version = "1.0.0a")
public class AFSUMod {

	@Instance
	public static AFSUMod instance;
	
	public static Block afsu;
	public static Item alc;
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		afsu = new AFSUBlock();
		alc = new AdvancedLapotronCrystal();
		
		GameRegistry.registerBlock(afsu, "AFSU");
		GameRegistry.registerItem(alc, "ALC");
		
		GameRegistry.registerTileEntity(TileEntityAFSU.class, "AFSU");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
}
