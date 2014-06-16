package riskyken.utilities.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public abstract class AbstractModItem extends Item {

	public AbstractModItem(String name) {
		setCreativeTab(RiskyKensUtilities.tabRiskyKensUtilities);
		setUnlocalizedName(name);
		setHasSubtypes(false);
		setNoRepair();
	}
	
	@Override
	public Item setUnlocalizedName(String name) {
		GameRegistry.registerItem(this, "item." + name);
		return super.setUnlocalizedName(name);
	}
}
