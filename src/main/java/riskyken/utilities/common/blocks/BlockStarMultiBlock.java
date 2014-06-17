package riskyken.utilities.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import riskyken.utilities.client.particles.EntityStarDust;
import riskyken.utilities.client.particles.ParticleManager;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityStarMultiBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStarMultiBlock extends AbstractModBlock implements ITileEntityProvider {

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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {
		//System.out.println("tick");
		int spread = 20;
		for (int i = 0; i < 5; i++) {
			float particleX = (x + 0.5F) + (rnd.nextFloat() * spread) - spread / 2;
			float particleY = (y + 0.5F) + (rnd.nextFloat() * spread) - spread / 2;
			float particleZ = (z + 0.5F) + (rnd.nextFloat() * spread) - spread / 2;
			
			EntityStarDust particle = new EntityStarDust(world, particleX, particleY, particleZ, x + 0.5F, y + 0.5F, z + 0.5F, 1F);
			ParticleManager.INSTANCE.spawnParticle(world, particle);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		if (metadata < 4) {
			return new TileEntityStarMultiBlock(metadata);
		}
		return null;
	}
}
