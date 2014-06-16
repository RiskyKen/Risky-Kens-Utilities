package riskyken.utilities.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUtilitieLight extends Block {

	public BlockUtilitieLight() {
		super(Material.air);
		setBlockName(LibBlockNames.UTILITIE_LIGHT);
		setLightLevel(1F);
		setBlockUnbreakable();
		setBlockBounds(0, 0, 0, 0, 0, 0);
		setLightOpacity(0);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlock.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcon =  register.registerIcon(LibModInfo.ID + ":" + "gift-0");
	}
}
