package xbony2.afsu.gui;

import ic2.core.GuiIconButton;
import ic2.core.IC2;
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

import xbony2.afsu.container.ContainerAFSU;
import xbony2.afsu.tileentity.TileEntityAFSU;

public class GuiAFSU extends GuiContainer{

	private final ContainerAFSU container;
	private final String armorInv;
	private final String level;
	private final String name;
	private static final ResourceLocation background = new ResourceLocation("afsu:textures/gui/gui_afsu.png");

	public GuiAFSU(ContainerAFSU container) {
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
	    this.buttonList.add(new GuiIconButton(0, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 7, 20, 20, new ItemStack(Items.redstone), true));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6, 4210752);
	    //this.fontRendererObj.drawString(this.armorInv, 8, this.ySize - 126 + 3, 4210752);

	    this.fontRendererObj.drawString(this.level, 16, 25, 4210752);
	    int energy = (int)Math.min(((TileEntityAFSU)this.container.base).energy, ((TileEntityAFSU)this.container.base).maxStorage);
	    this.fontRendererObj.drawString(" " + energy, 37, 35, 4210752);
	    this.fontRendererObj.drawString("/" + ((TileEntityAFSU)this.container.base).maxStorage, 37, 45, 4210752);

	    String output = StatCollector.translateToLocalFormatted("ic2.EUStorage.gui.info.output", new Object[] { Integer.valueOf(((TileEntityAFSU)this.container.base).output) });
	    this.fontRendererObj.drawString(output, 26, 60, 4210752);

	    GuiTooltiphelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, ((TileEntityAFSU)this.container.base).getredstoneMode(), 10, 7, 29, 26);
	  }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(background);
	    int j = (this.width - this.xSize) / 2;
	    int k = (this.height - this.ySize) / 2;
	    drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
	    if (((TileEntityAFSU)this.container.base).energy > 0.0D) {
	    	int i1 = (int)(176.0F /*<-width of bar*/ * ((TileEntityAFSU)this.container.base).getChargeLevel());
	     	drawTexturedModalRect(j + 8/*x of placement*/, k + 73 /*y of placement*/, 1/*x of bar*/, 251/*y of bar*/, i1 + 1, 5/*<-Height of bar*/);
	    }
	}

	protected void actionPerformed(GuiButton guibutton){
		super.actionPerformed(guibutton);

	    if (guibutton.id == 0)
	    	((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.base, 0);
		}

}
