package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;

public class BlockMeteor extends AbstractModBlock {

	public BlockMeteor() {
		super(LibBlockNames.METEOR);
		setHardness(16.0F);
		setResistance(1200.0F);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlockWithMetadata.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
		for (int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] blockIcons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcons = new IIcon[2];
		blockIcons[0] = register.registerIcon(LibModInfo.ID + ":" + "meteor_inner");
		blockIcons[1] = register.registerIcon(LibModInfo.ID + ":" + "meteor_outer");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 0) { return blockIcons[0]; }
		return blockIcons[1];
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}
}
