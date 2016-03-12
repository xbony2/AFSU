package xbony2.afsu.container;

import java.util.List;

import xbony2.afsu.ConfigHandler;
import xbony2.afsu.tileentity.TileEntityAFSU;
import net.minecraft.entity.player.EntityPlayer;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;

public class ContainerAFSU extends ContainerFullInv<TileEntityAFSU> {

	public ContainerAFSU(EntityPlayer player, TileEntityAFSU tileentity){
		super(player, tileentity, ConfigHandler.AFSUxSize + 3, ConfigHandler.AFSUySize);
		for (int i = 0; i < 4; i++)
			addSlotToContainer(new SlotArmor(player.inventory, i, ConfigHandler.AFSUArmxDispPosition, ConfigHandler.AFSUArmyDispPosition + i * 18));

        addSlotToContainer(new SlotInvSlot(tileentity.chargeSlot, 0, ConfigHandler.AFSUCxDispPosition, ConfigHandler.AFSUCyDispPosition));
        addSlotToContainer(new SlotInvSlot(tileentity.dischargeSlot, 0, ConfigHandler.AFSUDxDispPosition, ConfigHandler.AFSUDyDispPosition));
	}

	@Override
	public List<String> getNetworkedFields(){
	    List<String> list = super.getNetworkedFields();
	    list.add("energy");
	    list.add("redstoneMode");
	    return list;
	}
}
