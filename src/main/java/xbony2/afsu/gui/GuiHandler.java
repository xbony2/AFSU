package xbony2.afsu.gui;

import xbony2.afsu.container.ContainerAFSU;
import xbony2.afsu.tileentity.TileEntityAFSU;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity != null){
			return new ContainerAFSU(player, (TileEntityAFSU)entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null){
			ContainerAFSU cont = new ContainerAFSU(player, (TileEntityAFSU)entity);
			return new GuiAFSU(cont);
		}
		return null;
	}

}
