package riskyken.utilities.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityHouseBuilder;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHouseBuilder extends AbstractModBlock implements ITileEntityProvider {

	protected BlockHouseBuilder() {
		super(LibBlockNames.HOUSE_BUILDER);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlock.class, "block." + name);
		return super.setBlockName(name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityHouseBuilder();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcon = register.registerIcon(LibModInfo.ID + ":" + "device-block-3");
	}

}
