package riskyken.utilities.common.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityLeafCatcher;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLeafCatcher extends AbstractModBlock implements ITileEntityProvider {

	public BlockLeafCatcher() {
		super(LibBlockNames.LEAF_CATCHER);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlock.class, "block." + name);
		return super.setBlockName(name);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityLeafCatcher();
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon blockSideIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		blockIcon = register.registerIcon(LibModInfo.ID + ":" + "device-block-3");
		blockSideIcon = register.registerIcon(LibModInfo.ID + ":" + "leaf-catcher");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side < 2) { return blockIcon; }
		return blockSideIcon;
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, RiskyKensUtilities.instance, LibGuiIds.LEAF_CATCHER, world, x, y, z);
		}
		return true;
    }
}
