package riskyken.utilities.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRaisingHeart extends AbstractModItem {

	private static final String[] modes = {"Dig 1","Dig 2","Dig 4","Dig 8","Dig 16","Dig 32"};
	
	public ItemRaisingHeart() {
		super(LibItemNames.RAISING_HEART);
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "raising-heart");
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
		if(world.isRemote){return itemStack;}
		if (player.isSneaking())
		{
			int mode = getMode(itemStack);
			mode++;
			if (mode > modes.length - 1) {mode  = 0;}
			setMode(itemStack, mode);
			player.addChatMessage(new ChatComponentText("Mode " + modes[mode]));
		}
		//player.addChatMessage("Used!");
        return itemStack;
    }
	
	@Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int face, float par8, float par9, float par10)
    {
		if(world.isRemote) { return false; }
		if (player.isSneaking()) { return false; }
		if(!world.canMineBlock(player, x, y, z)) { return false; }
		if(world.isAirBlock(x, y, z)) { return false; }
		if (world.getBlock(x, y, z).getBlockHardness(world, x, y, z) == -1.0F) { return false; }
		
		//player.addChatMessage("Block face " + face);
		int mode = getMode(itemStack);
		
		if (itemStack.getItemDamage() + (Math.pow(2,mode) * 3 * 3) > itemStack.getMaxDamage()) {return false;}
		
		int destroyCount = 0;
		
		for (int i = -1; i < 2; i++ )
		{
			for (int j = -1; j < 2; j++ )
			{
				for (int depth = 0; depth < Math.pow(2,mode); depth++ )
				{
					switch (face)
					{
						case 0:
							if (checkDestroyBlock(world, player, x + j, y + depth, z + i)) { destroyCount++; }
							break;
						case 1:
							if (checkDestroyBlock(world, player, x + j , y - depth, z + i)) { destroyCount++; }
							break;
						case 2:
							if (checkDestroyBlock(world, player, x + i, y  + j, z + depth)) { destroyCount++; }
							break;
						case 3:
							if (checkDestroyBlock(world, player, x + i, y + j, z - depth)) { destroyCount++; }
							break;
						case 4:
							if (checkDestroyBlock(world, player, x + depth, y + i, z + j)) { destroyCount++; }
							break;
						case 5:
							if (checkDestroyBlock(world, player, x - depth, y + i, z + j)) { destroyCount++; }
							break;
					}
				}
			}
		}
		itemStack.setItemDamage(itemStack.getItemDamage() + destroyCount);
		return true;
    }
	
	private boolean checkDestroyBlock(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.canMineBlock(player, x, y, z)) { return false; }
		if(world.isAirBlock(x, y, z)) { return false; }
		if (world.getBlock(x, y, z).getBlockHardness(world, x, y, z) == -1.0F) { return false; }
		if(world.isRemote) {return false;}
		
		world.func_147480_a(x, y, z, true);
		return true;
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List hoverText, boolean par4) {
		hoverText.add("§" + EnumChatFormatting.DARK_RED.getFormattingCode() + "DEBUG ITEM");
		hoverText.add("§" + EnumChatFormatting.DARK_RED.getFormattingCode() + "DO NOT USE");
		int mode = getMode(itemStack);
		hoverText.add("Standby Ready!");
		hoverText.add("Mode " + modes[mode]);
		super.addInformation(itemStack, player, hoverText, par4);
	}
	
	private static int getMode(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{itemStack.setTagCompound(new NBTTagCompound());}
		if (!itemStack.getTagCompound().hasKey("Mode"))
		{itemStack.getTagCompound().setInteger("Mode", 0);}
		return itemStack.getTagCompound().getInteger("Mode");
	}
	
	private static void setMode(ItemStack itemStack, int mode)
	{
		itemStack.getTagCompound().setInteger("Mode", mode);
	}
}
