package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPaperCube extends AbstractModBlock {

	public BlockPaperCube() {
		super(LibBlockNames.PAPER_CUBE, Material.cloth, soundTypeCloth);
		setHardness(1.0F);
	}

	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlockWithMetadata.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcon =  register.registerIcon(LibModInfo.ID + ":" + "gift-0");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return Utils.getMinecraftColor(meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		return Utils.getMinecraftColor(meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
		for (int i = 0; i < 16; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
}
