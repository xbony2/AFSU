package xbony2.afsu.container;

import java.util.List;

import xbony2.afsu.tileentity.TileEntityAFSU;
import net.minecraft.entity.player.EntityPlayer;
import ic2.core.ContainerFullInv;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;

public class ContainerAFSU extends ContainerFullInv<TileEntityAFSU>{

	public ContainerAFSU(EntityPlayer player, TileEntityAFSU tileentity) {
		super(player, tileentity, 166);
		
		for (int i = 0; i < 4; i++){
			//addSlotToContainer(new SlotArmor(entityPlayer.inventory, i, 8 + i * 18, 84)); <-default
			addSlotToContainer(new SlotArmor(player.inventory, i, 152, 5 + i * 18));
		}
		
		addSlotToContainer(new SlotInvSlot(tileentity.chargeSlot, 0, 128, 13));
	    addSlotToContainer(new SlotInvSlot(tileentity.dischargeSlot, 0, 128, 50));
	}
	
	@Override
	public List<String> getNetworkedFields(){
	    List list = super.getNetworkedFields();
	    list.add("energy");
	    list.add("redstoneMode");

	    return list;
	}

}
