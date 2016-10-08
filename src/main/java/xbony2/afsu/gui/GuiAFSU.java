package xbony2.afsu.gui;

import ic2.core.GuiIconButton;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.gui.EnergyGauge;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Supplier;

import xbony2.afsu.AFSUMod;
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
	    this.armorInv = AFSUMod.translate("ic2.EUStorage.gui.info.armor");
	    this.level = AFSUMod.translate("ic2.EUStorage.gui.info.level");
		this.name = AFSUMod.translate("tile.AFSU.name"); //Lazy on my part? Maybe. Works though!
		
		addElement(EnergyGauge.asBar(this, 79, 38, (TileEntityBlock)container.base));
	    addElement(((VanillaButton)new VanillaButton(this, 152, 4, 20, 20, createEventSender(0)).withIcon(new Supplier(){
	        public ItemStack get(){
	          return new ItemStack(Items.REDSTONE);
	        }
	      }))
	      
	      .withTooltip(new Supplier()
	      {
	        public String get()
	        {
	          return ((TileEntityElectricBlock)container.base).getRedstoneMode();
	        }
	      }));
	}

	@Override
	public void initGui(){
	    super.initGui();
	    this.buttonList.add(new GuiIconButton(ConfigHandler.AFSUGuiButtonid1,
	    		(this.width - this.xSize) / 2 + ConfigHandler.AFSUGuiButtonxpart, 
	    		(this.height - this.ySize) / 2 + ConfigHandler.AFSUGuiButtonypart, 
	    		ConfigHandler.AFSUGuiButtonw, ConfigHandler.AFSUGuiButtonh, new ItemStack(Items.REDSTONE), 
	    		ConfigHandler.AFSUGuiButtonDrawQuantity1));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, ConfigHandler.AFSUyName, ConfigHandler.AFSUTextColor);

	    this.fontRendererObj.drawString(this.level, ConfigHandler.AFSUxLevel, ConfigHandler.AFSUyLevel, ConfigHandler.AFSUTextColor);
	    int energy = new Double(Math.min((this.container.base).energy.getEnergy(), (container.base).MAX_STORAGE /*.maxStorage*/)).intValue();
	    this.fontRendererObj.drawString(" " + energy, ConfigHandler.AFSUxEnergy, ConfigHandler.AFSUyEnergy, ConfigHandler.AFSUTextColor);
	    this.fontRendererObj.drawString("/" + (container.base).MAX_STORAGE /*.maxStorage*/, ConfigHandler.AFSUxEnergy2, ConfigHandler.AFSUyEnergy2, ConfigHandler.AFSUTextColor);

        String output = AFSUMod.translateFormatted("ic2.EUStorage.gui.info.output", container.base.getOutput());
	    this.fontRendererObj.drawString(output, ConfigHandler.AFSUxEnergy3, ConfigHandler.AFSUyEnergy3, ConfigHandler.AFSUTextColor);

	    GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, (container.base).getRedstoneMode(), ConfigHandler.AFSUToolTipminx, 
	    		ConfigHandler.AFSUToolTipminy, ConfigHandler.AFSUToolTipmaxx, ConfigHandler.AFSUToolTipmaxy);
	  }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(GuiAFSU.BACKGROUND);
	    int j = (this.width - this.xSize) / 2; //Good here
	    int k = (this.height - this.ySize) / 2; //good here
	    this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);//Renders the actual gui texture.
	    if (container.base.energy.getEnergy() > 0.0D) {
	    	int i1 = new Double(((ConfigHandler.AFSUbarWidth * container.base.energy.getCapacity()/* .getChargeLevel()*/) / 1.6D) + 5.0D).intValue();//Calculates the current energy in afsu.
	     	drawTexturedModalRect(j + ConfigHandler.AFSUBarxPlacement, k + ConfigHandler.AFSUBaryPlacement,
	     			ConfigHandler.AFSUBarxLocation, ConfigHandler.AFSUBaryLocation, i1 + 1, ConfigHandler.AFSUBarHeight);//Renders the blue energy bar.
	    }
    }

    @Override
	protected void actionPerformed(GuiButton guibutton){
		super.actionPerformed(guibutton);
        if (guibutton.id == ConfigHandler.AFSUGuiButtonid1) IC2.network.get(true).initiateClientTileEntityEvent(container.base, 0);
    }
}
