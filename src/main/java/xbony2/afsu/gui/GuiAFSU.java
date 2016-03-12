package xbony2.afsu.gui;

import ic2.core.util.GuiTooltipHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIconButton;
import ic2.core.IC2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import xbony2.afsu.ConfigHandler;
import xbony2.afsu.container.ContainerAFSU;

@SideOnly(Side.CLIENT)
public class GuiAFSU extends GuiContainer {

	private final ContainerAFSU container;
	private final String armorInv;
	private final String level;
	private final String name;
	private static final ResourceLocation BACKGROUND = new ResourceLocation("afsu:textures/gui/gui_afsu.png");

	public GuiAFSU(ContainerAFSU container){
		super(container);
		
		this.xSize = ConfigHandler.AFSUxSize;
		this.ySize = ConfigHandler.AFSUySize;
	    this.container = container;
	    this.armorInv = StatCollector.translateToLocal("ic2.EUStorage.gui.info.armor");
	    this.level = StatCollector.translateToLocal("ic2.EUStorage.gui.info.level");
		this.name = StatCollector.translateToLocal("tile.AFSU.name"); //Lazy on my part? Maybe. Works though!
	}

	@Override
	public void initGui(){
	    super.initGui();
	    this.buttonList.add(new GuiIconButton(ConfigHandler.AFSUGuiButtonid1,
	    		(this.width - this.xSize) / 2 + ConfigHandler.AFSUGuiButtonxpart, 
	    		(this.height - this.ySize) / 2 + ConfigHandler.AFSUGuiButtonypart, 
	    		ConfigHandler.AFSUGuiButtonw, ConfigHandler.AFSUGuiButtonh, new ItemStack(Items.redstone), 
	    		ConfigHandler.AFSUGuiButtonDrawQuantity1));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, ConfigHandler.AFSUyName, ConfigHandler.AFSUTextColor);

	    this.fontRendererObj.drawString(this.level, ConfigHandler.AFSUxLevel, ConfigHandler.AFSUyLevel, ConfigHandler.AFSUTextColor);
	    int energy = new Double(Math.min((this.container.base).energy, (container.base).maxStorage)).intValue();
	    this.fontRendererObj.drawString(" " + energy, ConfigHandler.AFSUxEnergy, ConfigHandler.AFSUyEnergy, ConfigHandler.AFSUTextColor);
	    this.fontRendererObj.drawString("/" + (container.base).maxStorage, ConfigHandler.AFSUxEnergy2, ConfigHandler.AFSUyEnergy2, ConfigHandler.AFSUTextColor);

        String output = StatCollector.translateToLocalFormatted("ic2.EUStorage.gui.info.output", container.base.output);
	    this.fontRendererObj.drawString(output, ConfigHandler.AFSUxEnergy3, ConfigHandler.AFSUyEnergy3, ConfigHandler.AFSUTextColor);

	    GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, (container.base).getredstoneMode(), ConfigHandler.AFSUToolTipminx, 
	    		ConfigHandler.AFSUToolTipminy, ConfigHandler.AFSUToolTipmaxx, ConfigHandler.AFSUToolTipmaxy);
	  }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(GuiAFSU.BACKGROUND);
	    int j = (this.width - this.xSize) / 2; //Good here
	    int k = (this.height - this.ySize) / 2; //good here
	    this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);//Renders the actual gui texture.
	    if (container.base.energy > 0.0D) {
	    	int i1 = new Double(((ConfigHandler.AFSUbarWidth * container.base.getChargeLevel()) / 1.6D) + 5.0D).intValue();//Calculates the current energy in afsu.
	     	drawTexturedModalRect(j + ConfigHandler.AFSUBarxPlacement, k + ConfigHandler.AFSUBaryPlacement,
	     			ConfigHandler.AFSUBarxLocation, ConfigHandler.AFSUBaryLocation, i1 + 1, ConfigHandler.AFSUBarHeight);//Renders the blue energy bar.
	    }
    }

    @Override
	protected void actionPerformed(GuiButton guibutton){
		super.actionPerformed(guibutton);
        if (guibutton.id == ConfigHandler.AFSUGuiButtonid1) IC2.network.get().initiateClientTileEntityEvent(container.base, 0);
    }
}
