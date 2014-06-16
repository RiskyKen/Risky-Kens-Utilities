package riskyken.utilities.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCassetteTape extends AbstractModItem {

	public ItemCassetteTape() {
		super(LibItemNames.CASSETTE_TAPE);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "cassette-tape");
	}
}
