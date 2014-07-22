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
	
	private int colour[];
	
	public TileEntityGift() {
		items = new ItemStack[9];
		colour = new int[2];
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.GIFT;
	}
	
	public void updateColor(int colour, int colorIndex) {
		this.colour[colorIndex] = colour;
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getColor(int colorIndex) {
		return this.colour[colorIndex];
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 5, tagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger(TAG_COLOUR_1, colour[0]);
		compound.setInteger(TAG_COLOUR_2, colour[1]);
		super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		colour[0] = compound.getInteger(TAG_COLOUR_1);
		colour[1] = compound.getInteger(TAG_COLOUR_2);
		super.readFromNBT(compound);
	}
	
	/**
	 * Used when the block is broken to save contents and colour data to the dropped item.
	 * @param compound
	 */
	public void writeItemNBT(NBTTagCompound compound) {
		compound.setInteger(TAG_COLOUR_1, colour[0]);
		compound.setInteger(TAG_COLOUR_2, colour[1]);
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
	
	/**
	 * Used when the block it placed to load contents and colour data from the item.
	 * @param compound
	 */
	public void readItemNBT(NBTTagCompound compound) {
		colour[0] = compound.getInteger(TAG_COLOUR_1);
		colour[1] = compound.getInteger(TAG_COLOUR_2);
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
