package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityFloodLight;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFloodLight extends AbstractModBlock implements ITileEntityProvider {
		
	public BlockFloodLight() {
		super(LibBlockNames.FLOOD_LIGHT);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlockWithMetadata.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] blockSideIcons;
	private IIcon[] blockFaceIcons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockSideIcons = new IIcon[2];
		blockSideIcons[0] = register.registerIcon(LibModInfo.ID + ":" + "device-block-3");
		blockSideIcons[1] = register.registerIcon(LibModInfo.ID + ":" + "device-block-4");
		
		blockFaceIcons = new IIcon[10];
		blockFaceIcons[0] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-1-on");
		blockFaceIcons[1] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-2-on");
		blockFaceIcons[2] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-3-on");
		blockFaceIcons[3] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-4-on");
		blockFaceIcons[4] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-5-on");
		
		blockFaceIcons[5] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-1-off");
		blockFaceIcons[6] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-2-off");
		blockFaceIcons[7] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-3-off");
		blockFaceIcons[8] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-4-off");
		blockFaceIcons[9] = register.registerIcon(LibModInfo.ID + ":" + "flood-light-5-off");
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
        int facing = BlockPistonBase.determineOrientation(world, x, y, z, entityLivingBase);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityFloodLight) {
			((TileEntityFloodLight)te).setBlockFacing(ForgeDirection.getOrientation(facing), false);
		}
	}
	
	@Override
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		TileEntity te = iBlockAccess.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityFloodLight) {
			return ((TileEntityFloodLight)te).getIconForSide(side, iBlockAccess.getBlockMetadata(x, y, z));
		} else {
			return super.getIcon(iBlockAccess, x, y, z, side);
		}
	}
	
	public IIcon getFaceIcon(int side, int facing, boolean active, int type) {
		if (active) {
			if (side == facing) {
				int newType = type;
				if (newType > 4) { newType -= 5; }
				return blockFaceIcons[newType];
			}
			if (type > 4) {
				return blockSideIcons[1];
			}
			return blockSideIcons[0];
		} else {
			if (side == facing)
			{return blockFaceIcons[4];}
			return blockSideIcons[0];
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		int someMeta = meta;
		if (side == 3) {
			if (someMeta > 4) { someMeta-= 5; }
			return blockFaceIcons[someMeta];
		}
		if (someMeta > 4) {
			return blockSideIcons[1];
		}
		return blockSideIcons[0];
	}
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityFloodLight) {
			((TileEntityFloodLight)te).setBlockFacing(axis, true);
			return true;
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
		for (int i = 0; i < BlockFloodLightTypes.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileEntityFloodLight) {
				((TileEntityFloodLight)te).preRemove();
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
		return BlockFloodLightTypes.makeEntity(metadata);
	}
	
}
