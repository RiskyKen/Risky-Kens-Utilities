package riskyken.utilities.common.items.block;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.tileentities.TileEntityGift;
import riskyken.utilities.utils.Utils;

public class ItemBlockGift extends ModItemBlock {

	
    public ItemBlockGift(Block block) {
		super(block);
		setMaxStackSize(16);
	}
    
    @Override
    public boolean requiresMultipleRenderPasses() {
    	return true;
    }
    
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4) {
		switch (itemStack.getItemDamage())
		{
			case 0:
				if (itemStack.hasTagCompound()) {
					par3List.add("");
					NBTTagCompound compound = itemStack.getTagCompound();
					//par3List.add("Color 1 - " + compound.getInteger("color1"));
					//par3List.add("Color 2 - " + compound.getInteger("color2"));
					NBTTagList items = compound.getTagList("Items", NBT.TAG_COMPOUND);
					
					for (int i = 0 ; i < items.tagCount(); i++) {
						NBTTagCompound itemTag = (NBTTagCompound)items.getCompoundTagAt(i);
						ItemStack item = ItemStack.loadItemStackFromNBT(itemTag);
						par3List.add(item.stackSize + "x " + item.getDisplayName());
					}
					
					//par3List.add("count - " + items.tagCount());
				}
				break;
		}
		super.addInformation(itemStack, player, par3List, par4);
	}
	
	
	
	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		//ClientProxy.renderPass = pass;
		if (pass == 0) { return ModBlocks.gift.icons[0]; }
		if (pass == 1) { return ModBlocks.gift.icons[1]; }
		return super.getIcon(stack, pass);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack itemStack, int pass) {

		if (itemStack.hasTagCompound()) {
			NBTTagCompound compound = itemStack.getTagCompound();
			int color1 = compound.getInteger("colour1");
			int color2 = compound.getInteger("colour2");
			if (pass == 0) { return color1; }
			if (pass == 1) { return color2; }
			System.out.println("gpt coluor");
		}
		
		if (pass == 0) { return Utils.getMinecraftColor(14); }
		if (pass == 1) { return Utils.getMinecraftColor(13); }
		
		System.out.println("get coluor");
		return super.getColorFromItemStack(itemStack, pass);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		
		if (!world.setBlock(x, y, z, this.field_150939_a, metadata, 3))
		{
			return false;
       	}
		
		if (world.getBlock(x, y, z) == this.field_150939_a)
		{
			//TODO fix
			//Blocks.class[this.field_150939_a].onBlockPlacedBy(world, x, y, z, player, stack);
           	//Blocks.class[this.field_150939_a].onPostBlockPlaced(world, x, y, z, metadata);
       	}
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null & te instanceof TileEntityGift) {
			if (stack.hasTagCompound()) {
				NBTTagCompound compound = stack.getTagCompound();
				((TileEntityGift)te).readItemNBT(compound);
			}
			else
			{
				((TileEntityGift)te).updateColor(Utils.getMinecraftColor(14), 0);
				((TileEntityGift)te).updateColor(Utils.getMinecraftColor(13), 1);
			}
		}

		return true;
	}
}
