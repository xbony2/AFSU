package xbony2.afsu.gui;

import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Energy;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//100% copied from the IC2 class
@SideOnly(Side.CLIENT)
public class GuiAFSU extends GuiIC2<ContainerElectricBlock> {
	private static final ResourceLocation BACKGROUND = new ResourceLocation("ic2", "textures/gui/GUIElectricBlock.png");

	public GuiAFSU(final ContainerElectricBlock container) {
		super(container, 196);

		addElement(EnergyGauge.asBar(this, 79, 38, (TileEntityBlock) container.base));
		addElement(((VanillaButton) new VanillaButton(this, 152, 4, 20, 20, createEventSender(0)).withIcon(new Supplier(){
			public ItemStack get(){
				return new ItemStack(Items.REDSTONE);
			}
		})).withTooltip(new Supplier(){
			public String get(){
				return ((TileEntityElectricBlock) container.base).getRedstoneMode();
			}
		}));
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		super.drawGuiContainerForegroundLayer(par1, par2);

		this.fontRendererObj.drawString(Localization.translate("ic2.EUStorage.gui.info.armor"), 8, this.ySize - 126 + 3, 4210752);

		this.fontRendererObj.drawString(Localization.translate("ic2.EUStorage.gui.info.level"), 79, 25, 4210752);
		int e = (int) Math.min(((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).energy.getEnergy(),
				((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).energy.getCapacity());
		this.fontRendererObj.drawString(" " + e, 110, 35, 4210752);
		this.fontRendererObj.drawString("/" + (int) ((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).energy.getCapacity(), 110, 45, 4210752);

		String output = Localization.translate("ic2.EUStorage.gui.info.output", new Object[]{Double.valueOf(((TileEntityElectricBlock) ((ContainerElectricBlock) this.container).base).getOutput())});
		this.fontRendererObj.drawString(output, 85, 60, 4210752);
	}

	protected ResourceLocation getTexture(){
		return BACKGROUND;
	}
}
