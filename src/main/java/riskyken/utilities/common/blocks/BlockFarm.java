package riskyken.utilities.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFarm extends AbstractModBlock {

	public BlockFarm() {
		super(LibBlockNames.FARM);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlock.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		blockIcon = register.registerIcon(LibModInfo.ID + ":" + "device-block-3");
	}
}
