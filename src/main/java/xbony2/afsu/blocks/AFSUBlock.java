package xbony2.afsu.blocks;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.apache.commons.lang3.mutable.MutableObject;

import xbony2.afsu.AFSUMod;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AFSUBlock extends Block{

	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon output;
	@SideOnly(Side.CLIENT)
	private IIcon input;
	private static final Class<?>[] emptyClassArray = new Class[0];
	private static final Object[] emptyObjArray = new Object[0];
	
	public AFSUBlock() {
		super(Material.iron);
		this.setCreativeTab(IC2.tabIC2);
		this.setHardness(1.5F);
		this.setStepSound(soundTypeMetal);
		this.setBlockName("AFSU");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side){
		int facing = getFacing(iBlockAccess, x, y, z);
	    if(facing == 0){ //up! WORKS!
	    	switch(side){
	    	case 5: return this.top;
	    	case 3: return this.top;
	    	case 0: return this.output;
	    	default: return this.input;
	    	}
	    }else if(facing == 2){ //south! WORKS!
	    	switch(side){
	    	case 0: return this.top;
	    	case 1: return this.top;
	    	case 2: return this.output;
	    	default: return this.input;
	    	}
	    }else if(facing == 1){ //down! WORKS!
	    	switch(side){
	    	case 5: return this.top;
	    	case 3: return this.top;
	    	case 1: return this.output;
	    	default: return this.input;
	    	}
	    }else if(facing == 3){ //north! WORKS!
	    	switch(side){
	    	case 0: return this.top;
	    	case 1: return this.top;
	    	case 3: return this.output;
	    	default: return this.input;
	    	}
	    }else if(facing == 4){ //east! WORKS!
	    	switch(side){
	    	case 0: return this.top;
	    	case 1: return this.top;
	    	case 4: return this.output;
	    	default: return this.input;
	    	}
	    }else if(facing == 5){ //west! WORKS!
	    	switch(side){
	    	case 0: return this.top;
	    	case 1: return this.top;
	    	case 5: return this.output;
	    	default: return this.input;
	    	}	
	    }else{ 
	    	return this.input;
	    }
	  }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register){
		this.top = register.registerIcon("AFSU" + ":" + "AFSU_top");
		this.output = register.registerIcon("AFSU" + ":" + "AFSU_output");
		this.input = register.registerIcon("AFSU" + ":" + "AFSU_input");
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side){
	    TileEntityBlock te = (TileEntityBlock)getOwnTe(blockAccess, x, y, z);
	    if (!(te instanceof TileEntityElectricBlock)) return 0;

	    return ((TileEntityElectricBlock)te).isEmittingRedstone() ? 15 : 0;
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

	public Class<? extends TileEntity> getTeClass(int meta, MutableObject<Class<?>[]> ctorArgTypes, MutableObject<Object[]> ctorArgs){
		return TileEntityAFSU.class;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack){
	    if (!IC2.platform.isSimulating()) return;

	    TileEntityBlock te = (TileEntityBlock)getOwnTe(world, x, y, z);
	    if (te == null) return;

	    if ((te instanceof TileEntityElectricBlock)) {
	      NBTTagCompound nbttagcompound = StackUtil.getOrCreateNbtData(itemStack);
	      ((TileEntityElectricBlock)te).energy = nbttagcompound.getDouble("energy");
	    }

	    if (entityliving == null) {
	      te.setFacing((short)1);
	    }else{
	      int yaw = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
	      int pitch = Math.round(entityliving.rotationPitch);

	      if (pitch >= 65)
	        te.setFacing((short)1);
	      else if (pitch <= -65)
	        te.setFacing((short)0);
	      else
	        switch (yaw) {
	        case 0:
	          te.setFacing((short)2);
	          break;
	        case 1:
	          te.setFacing((short)5);
	          break;
	        case 2:
	          te.setFacing((short)3);
	          break;
	        case 3:
	          te.setFacing((short)4);
	        }
	    }
	}
	
	@Override
	public boolean hasComparatorInputOverride(){
	    return true;
	}
	
	public int getComparatorInputOverride(World world, int x, int y, int z, int side){
	    TileEntityBlock te = (TileEntityBlock)getOwnTe(world, x, y, z);
	    if (!(te instanceof TileEntityBlock)) return 0;

	    TileEntityElectricBlock teb = (TileEntityElectricBlock)te;

	    return (int)Math.round(Util.map(teb.energy, teb.maxStorage, 15.0D));
	}
	
	@Override
	public final boolean hasTileEntity(int metadata){
	    return true;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z){
	    return false;
	}
	
	public TileEntity getOwnTe(IBlockAccess blockAccess, int x, int y, int z){
	    TileEntity te;
	    Block block;
	    int meta;
	    if ((blockAccess instanceof World)) {
	      World world = (World)blockAccess;
	      Chunk chunk;
	      if ((world.getChunkProvider() instanceof ChunkProviderServer)) {
	        ChunkProviderServer cps = (ChunkProviderServer)world.getChunkProvider();
	        chunk = (Chunk)cps.loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(x >> 4, z >> 4));
	      }else{
	        chunk = world.getChunkFromBlockCoords(x, z);
	      }

	      if ((chunk == null) || ((chunk instanceof EmptyChunk))) return null;

	      block = chunk.getBlock(x & 0xF, y, z & 0xF);
	      meta = chunk.getBlockMetadata(x & 0xF, y, z & 0xF);
	      te = chunk.func_150806_e(x & 0xF, y, z & 0xF);
	    }else{
	      block = blockAccess.getBlock(x, y, z);
	      meta = blockAccess.getBlockMetadata(x, y, z);
	      te = blockAccess.getTileEntity(x, y, z);
	    }

	    Class expectedClass = getTeClass(meta, null, null);
	    Class actualClass = te != null ? te.getClass() : null;

	    if (actualClass != expectedClass) {
	      if (block != this) {
	        if (Util.inDev()) {
	          StackTraceElement[] st = new Throwable().getStackTrace();
	          IC2.log.warn("Own tile entity query from {} to foreign block {} instead of {} at dim {}, {}/{}/{}.", new Object[] { st.length > 1 ? st[1] : "?", block != null ? block.getClass() : null, getClass(), (blockAccess instanceof World) ? Integer.valueOf(((World)blockAccess).provider.dimensionId) : "?", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });
	        }

	        return null;
	      }

	      IC2.log.warn("Mismatched tile entity at dim {}, {}/{}/{}, got {}, expected {}.", new Object[] { (blockAccess instanceof World) ? Integer.valueOf(((World)blockAccess).provider.dimensionId) : "?", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), actualClass, expectedClass });

	      if ((blockAccess instanceof World)) {
	        World world = (World)blockAccess;

	        te = createTileEntity(world, meta);
	        world.setTileEntity(x, y, z, te);
	      } else {
	        return null;
	      }
	    }

	    return te;
	}
	
	public int getFacing(IBlockAccess iBlockAccess, int x, int y, int z){
	    TileEntity te = getOwnTe(iBlockAccess, x, y, z);

	    if ((te instanceof TileEntityBlock)) {
	      return ((TileEntityBlock)te).getFacing();
	    }
	    int meta = iBlockAccess.getBlockMetadata(x, y, z);

	    return 3;
	}
	
	@Override
	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis){
	    if (axis == ForgeDirection.UNKNOWN) return false;

	    TileEntity tileEntity = getOwnTe(worldObj, x, y, z);

	    if ((tileEntity instanceof IWrenchable)) {
	      IWrenchable te = (IWrenchable)tileEntity;

	      int newFacing = ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal();

	      if (te.wrenchCanSetFacing(null, newFacing)) {
	        te.setFacing((short)newFacing);
	      }
	    }

	    return false;
	}
	
	@Override
	public final TileEntity createTileEntity(World world, int metadata) {
	    return new TileEntityAFSU();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int var6, float var7, float var8, float var9){
		if (!player.isSneaking() && player.getCurrentEquippedItem() != IC2Items.getItem("wrench")){
        	player.openGui(AFSUMod.instance, 0, world, x, y, z);
            player.openGui("AFSU", 0, world, x, y, z);
            return true;
        }else{
            return false;
        }
    }

}
