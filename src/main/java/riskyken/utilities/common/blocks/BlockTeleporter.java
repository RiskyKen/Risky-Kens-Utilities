package riskyken.utilities.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.client.particles.Particles;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeleporter extends AbstractModBlock implements ITileEntityProvider {

	public BlockTeleporter() {
		super(LibBlockNames.TELEPORTER);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlock.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, RiskyKensUtilities.instance, LibGuiIds.TELEPORTER, world, x, y, z);
		}
		return true;
    }
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if (meta == 0 | meta == 1)
			return;
		
		if (!world.isAirBlock(x, y + 1, z))
			return;
		
		for (int i = 0; i < 3; i++) {
			
			float particleX = x + rand.nextFloat();
			float particleY = y + 1; //+ rand.nextFloat();
			float particleZ = z + rand.nextFloat();
			
			float particleMotionX = 0;
			float particleMotionY = -0.01F; //- (rand.nextFloat() * 0.1F);
			float particleMotionZ = 0;
			
			Particles.TELEPORTER_BLOCK.spawnParticle(world, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ, meta==4);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] topIcons;
	
	@SideOnly(Side.CLIENT)
	private IIcon[] IIcons;
	
	@SideOnly(Side.CLIENT)
	public IIcon particleIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		topIcons = new IIcon[4];
		IIcons = new IIcon[4];
		
		for (int i = 0; i <= 3; i++) {
			topIcons[i] = register.registerIcon(LibModInfo.ID + ":" + "device-block-teleporter-" + i);
			IIcons[i] = register.registerIcon(LibModInfo.ID + ":" + "device-block-" + i);
		}
		
		particleIcon = register.registerIcon(LibModInfo.ID + ":" + "test");
	}
	
	
	@Override
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		int meta = iBlockAccess.getBlockMetadata(x, y, z);
		
		int tergetId = 0;
		
		if (meta <= 1) { tergetId = 0; }
		
		if (meta == 2) { tergetId = 1; }
		
		if (meta == 3) { tergetId = 2; }
		
		if (meta == 4) { tergetId = 3; }
		
		if (side == 1) {return topIcons[tergetId];}
		return IIcons[tergetId];
		
		//return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
	}
	
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 1) {return topIcons[3];}
		return IIcons[3];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityDeviceTeleporter();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IInventory) {
			IInventory inventory = (IInventory)te;
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if (stack != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();
					
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
					
					float mult = 0.05F;
					
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
				
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
