package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStarMultiBlock extends AbstractModBlock {

	public BlockStarMultiBlock() {
		super(LibBlockNames.STAR_MULTI_BLOCK);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlockWithMetadata.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
		for (int i = 0; i < BlockStarMultiBlockType.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta != 4) { return false; }
		
		if (!world.isRemote) {
			
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] blockIcons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcons = new IIcon[BlockStarMultiBlockType.values().length];
		
		blockIcons[0] = register.registerIcon(LibModInfo.ID + ":" + "star-1");
		blockIcons[1] = register.registerIcon(LibModInfo.ID + ":" + "star-2");
		blockIcons[2] = register.registerIcon(LibModInfo.ID + ":" + "star-3");
		blockIcons[3] = register.registerIcon(LibModInfo.ID + ":" + "star-4");
		
		blockIcons[6] = register.registerIcon(LibModInfo.ID + ":" + "shell-1");
		blockIcons[7] = register.registerIcon(LibModInfo.ID + ":" + "shell-2");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < BlockStarMultiBlockType.values().length) {
			return blockIcons[meta];
		} else {
			return blockIcons[0];
		}
	}
}
