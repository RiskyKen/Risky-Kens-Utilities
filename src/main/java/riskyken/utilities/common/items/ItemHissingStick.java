package riskyken.utilities.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHissingStick extends AbstractModItem {

	private static final String modes[] = {"Hiss", "Fuse"};
	
	public ItemHissingStick() {
		super(LibItemNames.HISSING_STICK);
		setMaxStackSize(1);
		setMaxDamage(50);
	}
	
	private int getMode(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{itemStack.setTagCompound(new NBTTagCompound());}
		if (!itemStack.getTagCompound().hasKey("Mode"))
		{itemStack.getTagCompound().setInteger("Mode", 0);}
		return itemStack.getTagCompound().getInteger("Mode");
	}
	
	private void setMode(ItemStack itemStack, int mode)
	{
		itemStack.getTagCompound().setInteger("Mode", mode);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote)
		{
			int mode = getMode(itemStack);
			if (player.isSneaking())
			{
				mode++;
				if (mode > 1){mode = 0;}
				setMode(itemStack, mode);
				player.addChatMessage(new ChatComponentText("Mode: " + modes[mode]));
				//player.addChatMessage("Mode: " + modes[mode]);
			}
			else
			{
				if (mode == 0)
				{
					world.playSoundAtEntity( player, "mob.creeper.say", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);
				}
				if (mode == 1)
				{
					world.playSoundAtEntity( player, "random.fuse", 1.0F, 1.0F);
				}
				itemStack.damageItem(1, player);
			}
			return super.onItemRightClick(itemStack, world, player);
		}
		else
		{
			return super.onItemRightClick(itemStack, world, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4) {
		int mode = getMode(itemStack);
		par3List.add("Thats a very nice everything you have there!");
		par3List.add("Sneak right click to change mode.");
		par3List.add("");
		par3List.add("Mode: " + modes[mode]);
		super.addInformation(itemStack, player, par3List, par4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "hissing-stick");
	}
}
