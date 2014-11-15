package xbony2.afsu.blocks;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import xbony2.afsu.AFSUMod;
import xbony2.afsu.ConfigHandler;
import xbony2.afsu.tileentity.TileEntityAFSU;
import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

public class AFSUBlock extends Block{

    @SideOnly(Side.CLIENT)
    private IIcon top, output, input;

	public AFSUBlock() {
		super(Material.iron);
		this.setCreativeTab(IC2.tabIC2);
		this.setHardness(1.5F);
		this.setStepSound(soundTypeMetal);
		this.setBlockName("AFSU");
	}

	/**
	 * World only
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side){
        TileEntity tile = iBlockAccess.getTileEntity(x, y, z);
        if (tile instanceof TileEntityBlock) {
            switch (new Short(((TileEntityBlock)tile).getFacing()).intValue()) {
                case 0://Up
                    switch (side) {
                        case 5:
                            return this.top;
                        case 3:
                            return this.top;
                        case 0:
                            return this.output;
                        default:
                            return this.input;
                    }
                case 1://Down
                    switch (side) {
                        case 5:
                            return this.top;
                        case 3:
                            return this.top;
                        case 1:
                            return this.output;
                        default:
                            return this.input;
                    }
                case 2://South
                    switch (side) {
                        case 0:
                            return this.top;
                        case 1:
                            return this.top;
                        case 2:
                            return this.output;
                        default:
                            return this.input;
                    }
                case 3://North
                    switch (side) {
                        case 0:
                            return this.top;
                        case 1:
                            return this.top;
                        case 3:
                            return this.output;
                        default:
                            return this.input;
                    }
                case 4://East
                    switch (side) {
                        case 0:
                            return this.top;
                        case 1:
                            return this.top;
                        case 4:
                            return this.output;
                        default:
                            return this.input;
                    }
                case 5://West
                    switch (side) {
                        case 0:
                            return this.top;
                        case 1:
                            return this.top;
                        case 5:
                            return this.output;
                        default:
                            return this.input;
                    }
                default://Unknown
                    return input;
            }
        }
        return null;
	}

	/**
	 * Hand only (side- not west or east
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata){
		switch(side){
    	case 0: return this.top;
    	case 1: return this.top;
    	case 3: return this.output;
    	default: return this.input;
    	}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register){
		this.top = register.registerIcon("AFSU" + ":" + "AFSU_top_" + ConfigHandler.afsutexture);
		this.output = register.registerIcon("AFSU" + ":" + "AFSU_output_" + ConfigHandler.afsutexture);
		this.input = register.registerIcon("AFSU" + ":" + "AFSU_input_" + ConfigHandler.afsutexture);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
        TileEntity tile = blockAccess.getTileEntity(x, y, z);
        if (tile instanceof TileEntityElectricBlock) {
            return ((TileEntityElectricBlock)tile).isEmittingRedstone() ? 15 : 0;
        }
        return super.isProvidingWeakPower(blockAccess, x, y, z, side);
	}

	@Override
	public boolean canProvidePower(){
	    return true;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int i, int j, int k){
	    return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side){
	    return true;
	}

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack){
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock electricBlock = (TileEntityElectricBlock)tile;
            NBTTagCompound nbt = StackUtil.getOrCreateNbtData(itemStack);
            electricBlock.energy = nbt.getDouble("energy");
            if (entityliving == null) {
                electricBlock.setFacing(convertIntegerToShort(1));
            } else {
                electricBlock.setFacing(convertIntegerToShort(BlockPistonBase.determineOrientation(world, x, y, z, entityliving)));
            }
        }
    }

    private static short convertIntegerToShort(int integer_n) {
        return new Integer(integer_n).shortValue();
    }

	@Override
	public boolean hasComparatorInputOverride(){
	    return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side){
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock teb = (TileEntityElectricBlock) tile;
            return new Long(Math.round(Util.map(teb.energy, teb.maxStorage, 15.0D))).intValue();
        }
        return super.getComparatorInputOverride(world, x, y, z, side);
    }

	@Override
	public final boolean hasTileEntity(int metadata){
	    return true;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z){
	    return false;
	}

	@Override
	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis){
	    if (axis == ForgeDirection.UNKNOWN) return false;
	    TileEntity tileEntity = worldObj.getTileEntity(x, y, z);

	    if ((tileEntity instanceof IWrenchable)) {
	    	IWrenchable te = (IWrenchable)tileEntity;

	    	short newFacing = convertIntegerToShort(ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal());

	    	if (te.wrenchCanSetFacing(null, newFacing)) {
	    		te.setFacing(newFacing);
	    	}
            return true;
	    }
	    return false;
	}

	@Override
	public final TileEntity createTileEntity(World world, int metadata) {
	    return new TileEntityAFSU();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int var6, float var7, float var8, float var9){
        if (player.getCurrentEquippedItem() == IC2Items.getItem("wrench")) {
            return true;
        }
		if (!player.isSneaking()) {
            player.openGui(AFSUMod.instance, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List stackList) {
        ItemStack zeroStack = new ItemStack(this, 1, 0);
        StackUtil.getOrCreateNbtData(zeroStack).setInteger("energy", 0);
        stackList.add(zeroStack);
        ItemStack fullStack = new ItemStack(this, 1, 1);
        StackUtil.getOrCreateNbtData(fullStack).setInteger("energy", TileEntityAFSU.MAX_STORAGE);
        stackList.add(fullStack);
    }

}
