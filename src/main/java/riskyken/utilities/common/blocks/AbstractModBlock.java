package riskyken.utilities.common.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
}
