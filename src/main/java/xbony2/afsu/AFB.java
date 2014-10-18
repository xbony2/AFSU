package xbony2.afsu;

import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AFB extends Item implements IElectricItem{

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
		this.itemIcon = register.registerIcon("AFSU" + ":" + "alc");
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 100000000; //One hundred million
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 5;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return 131072;
	}
}
