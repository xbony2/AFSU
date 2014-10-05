package xbony2.afsu;

import ic2.core.block.wiring.TileEntityElectricBlock;

public class TileEntityAFSU extends TileEntityElectricBlock{

	public TileEntityAFSU() {
		super(5, 8192, 1000000000); //One billion!
	}

	@Override
	public String getInventoryName() {
		return "AFSU";
	}
	
}
