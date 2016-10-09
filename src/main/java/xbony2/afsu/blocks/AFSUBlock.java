package xbony2.afsu.blocks;

import java.util.List;

import javax.annotation.Nullable;

import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.ref.BlockName;
import ic2.core.ref.IBlockModelProvider;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import xbony2.afsu.AFSUMod;
import xbony2.afsu.tileentity.TileEntityAFSU;

public class AFSUBlock extends Block implements ITileEntityProvider, IWrenchable {

	/*@SideOnly(Side.CLIENT)
	private IIcon top, output, input;*/

	public AFSUBlock(){
		super(Material.IRON);
		this.setCreativeTab(IC2.tabIC2);
		this.setHardness(1.5F);
		this.setUnlocalizedName("AFSU:AFSU");
		this.setRegistryName("AFSU:AFSU");
	}

	/* **
	 * World only
	 *
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side){
		TileEntity tile = iBlockAccess.getTileEntity(x, y, z);
		if (tile instanceof TileEntityBlock){
			switch(new Short(((TileEntityBlock)tile).getFacing()).intValue()){
				case 0://Up
					switch (side){
						case 2:
							return this.top;
						case 3:
							return this.top;
						case 0:
							return this.output;
						default:
							return this.input;
					}
				case 1://Down
					switch (side){
						case 2:
							return this.top;
						case 3:
							return this.top;
						case 1:
							return this.output;
						default:
							return this.input;
					}
				case 2://South
					switch (side){
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
					switch (side){
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
					switch (side){
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
					switch (side){
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

	**
	 * Hand only (side- not west or east
	 *
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
	}*/

	/*@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity tile = blockAccess.getTileEntity(x, y, z);
		if (tile instanceof TileEntityElectricBlock)
			return ((TileEntityElectricBlock)tile).isEmittingRedstone() ? 15 : 0;
		
		return super.isProvidingWeakPower(blockAccess, x, y, z, side);
	}*/

	@Override
	public boolean canProvidePower(IBlockState state){
		return true;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos){
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack){
		/*TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityElectricBlock) {
			TileEntityElectricBlock electricBlock = (TileEntityElectricBlock)tile;
			NBTTagCompound nbt = StackUtil.getOrCreateNbtData(itemstack);
			electricBlock.energy = new Energy(electricBlock, nbt.getDouble("energy"));
			if (placer == null)
				electricBlock.setFacing(EnumFacing.UP);
			else
				//electricBlock.setFacing(convertIntegerToShort(BlockPistonBase.determineOrientation(world, x, y, z, entityliving)));
				electricBlock.setFacing(EnumFacing.UP);
		}*/
	}

	private static short convertIntegerToShort(int integer_n) {
		return new Integer(integer_n).shortValue();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state){
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos){
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityElectricBlock) {
			TileEntityElectricBlock teb = (TileEntityElectricBlock) tile;
			return new Long(Math.round(Util.map(teb.energy.getEnergy(), TileEntityAFSU.MAX_STORAGE, 15.0D))).intValue();
		}
		
		return super.getComparatorInputOverride(blockState, world, pos);
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type){
		return false;
	}

	/*@Override
	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis){
		if (axis == ForgeDirection.UNKNOWN) return false;
		TileEntity tileEntity = worldObj.getTileEntity(new BlockPos(x, y, z));

		if ((tileEntity instanceof IWrenchable)) {
			IWrenchable te = (IWrenchable)tileEntity;

			short newFacing = convertIntegerToShort(ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal());

			if (te.wrenchCanSetFacing(null, newFacing))
				te.setFacing(newFacing);
			
			return true;
		}
		return false;
	}*/

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (player.inventory.getCurrentItem() == IC2Items.getItem("wrench") || player.inventory.getCurrentItem() == IC2Items.getItem("electricWrench"))
			return true;
		
		if (!player.isSneaking()) {
			player.openGui(AFSUMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
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

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) return;

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IInventory){
			IInventory inventory = (IInventory)tile;
			for (int j1 = 0; j1 < inventory.getSizeInventory(); ++j1) {
				ItemStack itemstack = inventory.getStackInSlot(j1);
				if (itemstack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
					while (itemstack.stackSize > 0) {
						int k1 = world.rand.nextInt(21) + 10;
						if (k1 > itemstack.stackSize)
							k1 = itemstack.stackSize;
						itemstack.stackSize -= k1;
						EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
						entityitem.motionX = world.rand.nextGaussian() * 0.05F;
						entityitem.motionY = world.rand.nextGaussian() * 0.05F + 0.2F;
						entityitem.motionZ = world.rand.nextGaussian() * 0.05F;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			//world.func_147453_f(xCoord, yCoord, zCoord, block);
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAFSU();
    }

	@Override
	public EnumFacing getFacing(World world, BlockPos pos){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te, EntityPlayer player, int fortune){
		// TODO Auto-generated method stub
		return null;
	}
}
