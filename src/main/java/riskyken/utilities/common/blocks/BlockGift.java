package riskyken.utilities.common.blocks;

import java.util.ArrayList;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.items.block.ItemBlockGift;
import riskyken.utilities.common.items.block.ModItemBlock;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityGift;
import riskyken.utilities.proxies.ClientProxy;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGift extends AbstractModBlock implements ITileEntityProvider {

	public BlockGift() {
		super(LibBlockNames.GIFT, Material.cloth, soundTypeCloth);
		setHardness(1.0F);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ItemBlockGift.class, "block." + name);
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		icons = new IIcon[3];
		icons[0] = register.registerIcon(LibModInfo.ID + ":" + "gift-0");
		icons[1] = register.registerIcon(LibModInfo.ID + ":" + "gift-1");
		icons[2] = register.registerIcon(LibModInfo.ID + ":" + "gift-2");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 1) {
			return  icons[0];
		}
		if (meta == 2) {
			if (side == 0) { return  icons[1]; }
			if (side == 1) { return  icons[1]; }
			return  icons[2];
		}
		
		if (ClientProxy.renderPass == 0)
		{
			return  icons[0];
		}
		if (ClientProxy.renderPass == 1)
		{
			if (side == 0) { return  icons[1]; }
			if (side == 1) { return  icons[1]; }
		}
		return  icons[2];
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
    public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean canRenderInPass(int pass)
	{
		ClientProxy.renderPass = pass;
	    return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderType()
	{
		//return -1;
		return ClientProxy.giftBlockRenderType;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess par1iBlockAccess, int x, int y, int z) {
		
		TileEntity te = par1iBlockAccess.getTileEntity(x, y, z);
		
		if (te != null & te instanceof TileEntityGift) {
			if (ClientProxy.renderPass == 0)
			{
				return ((TileEntityGift)te).getColor(0);
			}
			if (ClientProxy.renderPass == 1)
			{
				return ((TileEntityGift)te).getColor(1);
			}
		}
		
		if (ClientProxy.renderPass == 0)
		{
			return  Utils.getMinecraftColor(14);
		}
		return  Utils.getMinecraftColor(13);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
		
		if (!world.isRemote)
		{
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.dye) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te != null & te instanceof TileEntityGift) {
					
					if (side == 0 | side == 1) {

						if (xHit < 0.625F & xHit > 0.375F)
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 1);
							
						}
						else if (zHit < 0.625F & zHit > 0.375F)
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 1);
							
						}
						else
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 0);
						}
					}
					if (side == 2 | side == 3) {

						if (xHit < 0.625F & xHit > 0.375F)
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 1);
							
						}
						else
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 0);
						}
					}
					if (side == 4 | side == 5) {

						if (zHit < 0.625F & zHit > 0.375F)
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 1);
							
						}
						else
						{
							((TileEntityGift)te).updateColor(Utils.getMinecraftColor(-player.getCurrentEquippedItem().getItemDamage() + 15), 0);
						}
					}
				}
				return true;
			}
			else {
				FMLNetworkHandler.openGui(player, RiskyKensUtilities.instance, LibGuiIds.GIFT, world, x, y, z);
			}
		}
		
		return true;
	}
	
	@Override
    public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityGift();
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		// TODO Auto-generated method stub
		//return super.getDrops(world, x, y, z, metadata, fortune);
		return null;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMeta) {

		ItemStack itemstack = this.createStackedBlock(oldMeta);
        float f = 0.7F;
        double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, itemstack);
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null & te instanceof TileEntityGift) {
            NBTTagCompound compound = new NBTTagCompound();
            ((TileEntityGift)te).writeItemNBT(compound);
            //compound.setInteger("color1", ((TileEntityGift)te).getColor(0));
            //compound.setInteger("color2", ((TileEntityGift)te).getColor(1));
            itemstack.setTagCompound(compound);
		}
		
		world.spawnEntityInWorld(entityitem);
		
		super.breakBlock(world, x, y, z, oldBlock, oldMeta);
	}
}