package xbony2.afsu.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import xbony2.afsu.tileentity.TileEntityAFSU;

import java.util.List;

/**
 * Created by Master801 on 11/14/2014.
 * @author Master801
 */
public class ItemBlockAFSU extends ItemBlock {

    public ItemBlockAFSU(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltipList, boolean par4) {
        final String output = StatCollector.translateToLocal("ic2.item.tooltip.Output") + " " + TileEntityAFSU.MAX_OUTPUT + "EU/t";
        final String capacity = StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " " + "1b EU";
        final String stored = StatCollector.translateToLocal("ic2.item.tooltip.Store") + " ";
        tooltipList.add(output + " " + capacity);
        
        if (stack.hasTagCompound())
            tooltipList.add(stored + stack.getTagCompound().getInteger("energy") + " EU");
        else
            tooltipList.add(stored + 0 + " EU");
    }

}
