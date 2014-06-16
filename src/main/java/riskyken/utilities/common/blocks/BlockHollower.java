package riskyken.utilities.common.blocks;

import javax.swing.Icon;

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
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHollower extends AbstractModBlock implements ITileEntityProvider {

	protected BlockHollower() {
		super(LibBlockNames.HOLLOWER);
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
			FMLNetworkHandler.openGui(player, RiskyKensUtilities.instance, LibGuiIds.HOLLOWER, world, x, y, z);
		}
		return true;
    }
	
	@Override
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		int meta = iBlockAccess.getBlockMetadata(x, y, z);
		if (side > 1) {return blockSideIcons[meta];}
		return blockIcons[meta];
		//return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side > 1) {return blockSideIcons[3];}
		return blockIcons[3];
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] blockSideIcons;
	@SideOnly(Side.CLIENT)
	private IIcon[] blockIcons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		blockSideIcons = new IIcon[4];
		blockIcons = new IIcon[4];

		blockSideIcons[0] = register.registerIcon(LibModInfo.ID + ":" + "device-block-hollower-off");
		blockSideIcons[1] = register.registerIcon(LibModInfo.ID + ":" + "device-block-hollower-idle");
		blockSideIcons[2] = register.registerIcon(LibModInfo.ID + ":" + "device-block-hollower-on");
		blockSideIcons[3] = register.registerIcon(LibModInfo.ID + ":" + "device-block-hollower-run");
		
		blockIcons[0] = register.registerIcon(LibModInfo.ID + ":" + "device-block-0");
		blockIcons[1] = register.registerIcon(LibModInfo.ID + ":" + "device-block-1");
		blockIcons[2] = register.registerIcon(LibModInfo.ID + ":" + "device-block-2");
		blockIcons[3] = register.registerIcon(LibModInfo.ID + ":" + "device-block-3");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityDeviceHollower();
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
