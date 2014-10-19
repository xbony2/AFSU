package xbony2.afsu.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import ic2.core.IC2;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.GuiElectricBlock;

public class GuiAFSU extends GuiElectricBlock{

	private final ContainerElectricBlock container;
	private final String armorInv;
	private final String level;
	private final String name;
	private static final ResourceLocation background = new ResourceLocation("afsu/textures/gui/GUIAFSU.png");

	public GuiAFSU(ContainerElectricBlock container) {
		super(container);
		
		this.ySize = 196;
	    this.container = container;
	    this.armorInv = StatCollector.translateToLocal("ic2.EUStorage.gui.info.armor");
	    this.level = StatCollector.translateToLocal("ic2.EUStorage.gui.info.level");
		this.name = "AFSU";
	}

}
