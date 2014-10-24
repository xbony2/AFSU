package xbony2.afsu.gui;

import ic2.core.GuiIconButton;
import ic2.core.IC2;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.TileEntityElectricBlock;
import ic2.core.network.NetworkManager;
import ic2.core.util.GuiTooltiphelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiAFSU extends GuiContainer{

	private final ContainerElectricBlock container;
	private final String armorInv;
	private final String level;
	private final String name;
	private static final ResourceLocation background = new ResourceLocation("afsu/textures/gui/GUIAFSU.png");

	public GuiAFSU(ContainerElectricBlock container) {
		super(container);
		
		this.xSize = 176;
		this.ySize = 166;
	    this.container = container;
	    this.armorInv = StatCollector.translateToLocal("ic2.EUStorage.gui.info.armor");
	    this.level = StatCollector.translateToLocal("ic2.EUStorage.gui.info.level");
		this.name = "AFSU";
	}
	
	@Override
	public void initGui(){
	    super.initGui();
	    this.buttonList.add(new GuiIconButton(0, 10, 7, 20, 20, new ItemStack(Items.redstone), true));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
	    this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6, 4210752);
	    this.fontRendererObj.drawString(this.armorInv, 8, this.ySize - 126 + 3, 4210752);

	    this.fontRendererObj.drawString(this.level, 79, 25, 4210752);
	    int e = (int)Math.min(((TileEntityElectricBlock)this.container.base).energy, ((TileEntityElectricBlock)this.container.base).maxStorage);
	    this.fontRendererObj.drawString(" " + e, 110, 35, 4210752);
	    this.fontRendererObj.drawString("/" + ((TileEntityElectricBlock)this.container.base).maxStorage, 110, 45, 4210752);

	    String output = StatCollector.translateToLocalFormatted("ic2.EUStorage.gui.info.output", new Object[] { Integer.valueOf(((TileEntityElectricBlock)this.container.base).output) });
	    this.fontRendererObj.drawString(output, 85, 60, 4210752);

	    GuiTooltiphelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, ((TileEntityElectricBlock)this.container.base).getredstoneMode(), 153, 3, 172, 22);
	  }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(background);
	    int j = (this.width - this.xSize) / 2;
	    int k = (this.height - this.ySize) / 2;
	    drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
	    if (((TileEntityElectricBlock)this.container.base).energy > 0.0D) {
	      int i1 = (int)(24.0F * ((TileEntityElectricBlock)this.container.base).getChargeLevel());
	      drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
	    }
	}

	protected void actionPerformed(GuiButton guibutton){
		super.actionPerformed(guibutton);

	    if (guibutton.id == 0)
	      ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.base, 0);
		}

}
