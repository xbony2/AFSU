package xbony2.afsu;

import xbony2.afsu.blocks.AFSUBlock;
import xbony2.afsu.gui.GuiHandler;
import xbony2.afsu.items.AFB;
import xbony2.afsu.tileentity.TileEntityAFSU;
import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "AFSU", name = "AFSU Mod", version = "1.0.1a")
public class AFSUMod {

	@Instance
	public static AFSUMod instance;
	
	public static Block afsu;
	public static Item alc;
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		afsu = new AFSUBlock();
		alc = new AFB();
		
		GameRegistry.registerBlock(afsu, "AFSU");
		GameRegistry.registerItem(alc, "ALC");
		
		GameRegistry.registerTileEntity(TileEntityAFSU.class, "AFSU");
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		Recipes.advRecipes.addRecipe(new ItemStack(alc), new Object[]{
			"IGI", "CBC", "ICI",
				'I', IC2Items.getItem("iridiumPlate"),
				'G', IC2Items.getItem("glassFiberCableItem"),
				'B', IC2Items.getItem("lapotronCrystal"),
				'C', "circuitAdvanced"});
		
		Recipes.advRecipes.addRecipe(new ItemStack(afsu), new Object[]{
			"IGI", "GMG", "IGI",
				'I', IC2Items.getItem("iridiumPlate"),
				'G', alc,
				'M', IC2Items.getItem("mfsUnit")});
	}
}
