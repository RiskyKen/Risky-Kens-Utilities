package riskyken.utilities.common.tileentities;

import riskyken.utilities.common.lib.LibBlockNames;

public class TileEntityHouseBuilder extends TileEntityUtilitiesBasePowered {

	public TileEntityHouseBuilder() {
		super(100, 500);
	}

	@Override
	public String getInventoryName() {
		return LibBlockNames.HOUSE_BUILDER;
	}

}
