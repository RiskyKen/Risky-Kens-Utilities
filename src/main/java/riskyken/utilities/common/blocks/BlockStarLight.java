package riskyken.utilities.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityStarLight;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStarLight extends AbstractModBlock implements ITileEntityProvider {

	public BlockStarLight() {
		super(LibBlockNames.STAR_LIGHT);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlockWithMetadata.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		blockIcon = register.registerIcon(LibModInfo.ID + ":" + "test");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null & te instanceof TileEntityStarLight) {
			
			for (int i = 0; i < 5; i++) {
				int renderState = ((TileEntityStarLight)te).getRenderState();
	 			float particleX = x + 0.5F - 10 + rnd.nextFloat() * 20;
				float particleY = y + 0.5F;// - 5 + rnd.nextFloat() * 10;
				float particleZ = z + 0.5F - 10 + rnd.nextFloat() * 20;
				//System.out.println("render state " + renderState);
				if (renderState == 0) {
					//Particles.STAR_DUST.spawnParticle(world, particleX, particleY, particleZ, x + 0.5F, y + 0.5F, z + 0.5F, true);
				}
				
				if (renderState == 2) {
					//Particles.STAR_FLAME.spawnParticle(world, x + 0.5F, y + 0.5F, z + 0.5F, 0, 0, 0, true);
				}
			}

		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
		for (int i = 0; i < BlockStarLightTypes.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileEntityStarLight) {
				((TileEntityStarLight)te).preRemove();
			}
		}
		super.breakBlock(world, x, y, z, block, par6);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return BlockStarLightTypes.makeEntity(metadata);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
}
