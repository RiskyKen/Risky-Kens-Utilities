package riskyken.utilities.common.tileentities;

import riskyken.utilities.common.lib.LibBlockNames;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants.NBT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGift extends TileEntityUtilitiesBase {

	private static final String TAG_COLOUR_1 = "colour1";
	private static final String TAG_COLOUR_2 = "colour2";
	private static final String TAG_ITEMS = "items";
	private static final String TAG_SLOT = "slot";
	
	private int colour1;
	private int colour2;
	
	public TileEntityGift() {
		items = new ItemStack[9];
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.GIFT;
	}
	
	public void updateColor1(int colour) {
		colour1 = colour;
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public void updateColor2(int colour) {
		colour2 = colour;
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateColor(byte colorId, int colorData) {
		if (colorId == 0) { colour1 = colorData; }
		if (colorId == 1) { colour2 = colorData; }
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getColor(int colorId) {
		if (colorId == 0) { return colour1; }
		if (colorId == 1) { return colour2; }
		return 0;
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 5, tagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readItemNBT(packet.func_148857_g());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger(TAG_COLOUR_1, colour1);
		compound.setInteger(TAG_COLOUR_2, colour2);
		super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		colour1 = compound.getInteger(TAG_COLOUR_1);
		colour2 = compound.getInteger(TAG_COLOUR_2);
		super.readFromNBT(compound);
	}
	
	public void writeItemNBT(NBTTagCompound compound) {
		compound.setInteger(TAG_COLOUR_1, colour1);
		compound.setInteger(TAG_COLOUR_2, colour2);
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte(TAG_SLOT, (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag(TAG_ITEMS, items);
	}
	
	public void readItemNBT(NBTTagCompound compound) {
		colour1 = compound.getInteger(TAG_COLOUR_1);
		colour2 = compound.getInteger(TAG_COLOUR_2);
		NBTTagList items = compound.getTagList(TAG_ITEMS, NBT.TAG_COMPOUND);
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
			int slot = item.getByte(TAG_SLOT);
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}

}
