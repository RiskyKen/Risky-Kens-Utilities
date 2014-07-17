package riskyken.utilities.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.lib.LibModInfo;

public abstract class AbstractModBlock extends Block {

	public AbstractModBlock(String name) {
		super(Material.iron);
		setCreativeTab(RiskyKensUtilities.tabRiskyKensUtilities);
		setHardness(3.0F);
		setStepSound(soundTypeMetal);
		setBlockName(name);
	}
	
	public AbstractModBlock(String name, Material material, SoundType soundType) {
		super(material);
		setCreativeTab(RiskyKensUtilities.tabRiskyKensUtilities);
		setHardness(3.0F);
		setStepSound(soundType);
		setBlockName(name);
	}
	
	@Override
	public String getUnlocalizedName() {
		return getModdedUnlocalizedName(super.getUnlocalizedName());
	}
	
	protected String getModdedUnlocalizedName(String unlocalizedName) {
		String name = unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
		return "tile." + LibModInfo.ID.toLowerCase() + ":" + name;
	}
}
