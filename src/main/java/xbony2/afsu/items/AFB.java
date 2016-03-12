package xbony2.afsu.items;

import xbony2.afsu.ConfigHandler;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AFB extends Item implements IElectricItem{

	@SideOnly(Side.CLIENT)
	private IIcon empty, partly, full;

	public AFB(){
		super();
		this.setCreativeTab(IC2.tabIC2);
		this.setUnlocalizedName("ALC");
		this.setMaxStackSize(1);
		this.setMaxDamage(100000000 + 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register){
		this.empty = register.registerIcon("AFSU" + ":" + "alcempty_" + ConfigHandler.afbtexture);
		this.partly = register.registerIcon("AFSU" + ":" + "alcpartly_" + ConfigHandler.afbtexture);
		this.full = register.registerIcon("AFSU" + ":" + "alcfull_" + ConfigHandler.afbtexture);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage){
		if(damage <= 100000) return empty;
		if(damage <= 99000000) return partly;
		else return full;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack){
		return true;
	}

	@Override
	public Item getChargedItem(ItemStack itemStack){
		return this;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack){
		return this;
	}

	@Override
	public double getMaxCharge(ItemStack itemStack){
		return 100000000.0D; //One hundred million
	}

	@Override
	public int getTier(ItemStack itemStack){
		return 5;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack){
		return 131072.0D;
	}
}
