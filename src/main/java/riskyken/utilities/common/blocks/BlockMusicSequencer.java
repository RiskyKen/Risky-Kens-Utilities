package riskyken.utilities.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.client.renderer.RenderBlockFlipped;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityMusicSequencer;
import riskyken.utilities.proxies.ClientProxy;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMusicSequencer extends AbstractModBlock implements ITileEntityProvider {

	public BlockMusicSequencer() {
		super(LibBlockNames.MUSIC_SEQUENCER);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlock.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon sideFlippedIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		blockIcon = register.registerIcon(LibModInfo.ID + ":" + "device-block-3");
		sideIcon = register.registerIcon(LibModInfo.ID + ":" + "music-sequencer");
		sideFlippedIcon = new IconFlipped(sideIcon, true, false);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {

		if (side > 1) {
			if (side == 2 | side == 5) {
				return sideFlippedIcon;
			}
			return sideIcon;
		}
		
		return blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityMusicSequencer();
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, RiskyKensUtilities.instance, LibGuiIds.MUSIC_SEQUENCER, world, x, y, z);
		}
		return true;
    }
}
