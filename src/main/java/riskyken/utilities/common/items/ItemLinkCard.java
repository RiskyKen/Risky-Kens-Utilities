package riskyken.utilities.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLinkCard extends AbstractModItem {

	public ItemLinkCard() {
		super(LibItemNames.LINK_CARD);
		setMaxStackSize(16);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
			int z, int par7, float par8, float par9, float par10) {
		// TODO Auto-generated method stub
		if (!world.isRemote & player.isSneaking())
		{
			setLinkPos(itemStack, world, x, y + 1, z);
			player.addChatMessage(new ChatComponentText(GetOverleyText(itemStack)));
			//player.addChatMessage(GetOverleyText(itemStack));
		}
		
		return super.onItemUse(itemStack, player, world, x, y, z, par7, par8, par9, par10);
	}
	
	private void setTileEntityData(TileEntityDeviceTeleporter te, ItemStack itemStack){
		if (!IsLinked(itemStack)) { return; }
		
		int x = itemStack.getTagCompound().getInteger("xPos");
		int y = itemStack.getTagCompound().getInteger("yPos");
		int z = itemStack.getTagCompound().getInteger("zPos");
		int id = itemStack.getTagCompound().getInteger("worldId");
		
		te.setTeleportPos(id, x, y, z);
	}
	
	private void setLinkPos(ItemStack itemStack, World world, int x, int y, int z) {
		if (!itemStack.hasTagCompound())
		{itemStack.setTagCompound(new NBTTagCompound());}
		itemStack.getTagCompound().setInteger("xPos", x);
		itemStack.getTagCompound().setInteger("yPos", y);
		itemStack.getTagCompound().setInteger("zPos", z);
		itemStack.getTagCompound().setInteger("worldId", world.provider.dimensionId);
	}
	
	private boolean IsLinked(ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) { return false; }
		return true;
	}
	
	private String GetOverleyText(ItemStack itemStack) {
		if (!IsLinked(itemStack)) { return "Not linked"; }
		
		int x = itemStack.getTagCompound().getInteger("xPos");
		int y = itemStack.getTagCompound().getInteger("yPos");
		int z = itemStack.getTagCompound().getInteger("zPos");
		int id = itemStack.getTagCompound().getInteger("worldId");
		
		return "Linked to X:" + x + " Y:" + y + " Z:" + z + " ID:" + id;
	}
	
	@Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4)
    {
		par3List.add("Sneak right click block to save location.");
		par3List.add("Place inside teleporter block to use.");
		par3List.add("");
		par3List.add(GetOverleyText(itemStack));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "link-card");
	}

}
