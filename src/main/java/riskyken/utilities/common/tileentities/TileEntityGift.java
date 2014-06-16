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

	public int color1;
	public int color2;
	
	public TileEntityGift() {
		items = new ItemStack[9];
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.GIFT;
	}
	
	public void updateColor1(int c1) {
		color1 = c1;
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public void updateColor2(int c2) {
		color2 = c2;
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateColor(byte colorId, int colorData) {
		if (colorId == 0) { color1 = colorData; }
		if (colorId == 1) { color2 = colorData; }
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getColor(int colorId) {
		if (colorId == 0) { return color1; }
		if (colorId == 1) { return color2; }
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
		//super.onDataPacket(net, packet);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger("color1", color1);
		compound.setInteger("color2", color2);
		super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		color1 = compound.getInteger("color1");
		color2 = compound.getInteger("color2");
		super.readFromNBT(compound);
		//worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void writeItemNBT(NBTTagCompound compound) {
		compound.setInteger("color1", color1);
		compound.setInteger("color2", color2);
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items", items);
	}
	
	public void readItemNBT(NBTTagCompound compound) {
		color1 = compound.getInteger("color1");
		color2 = compound.getInteger("color2");
		NBTTagList items = compound.getTagList("Items", NBT.TAG_COMPOUND);
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}

}
