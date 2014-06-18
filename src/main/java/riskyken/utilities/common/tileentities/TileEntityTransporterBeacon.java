package riskyken.utilities.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import riskyken.utilities.common.items.ItemTransportDevice;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibBlockNames;

public class TileEntityTransporterBeacon extends TileEntityUtilitiesBasePowered {

	private static final int BASE_RANGE = 8;
	
	public TileEntityTransporterBeacon() {
		super(50000, 100000);
		items = new ItemStack[5];
	}
	
	private int getExtraRange()
	{
		int upgrades = 0;
		for (int i = 0; i < 3; i++)
		{
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack != null && itemstack.getItem() == ModItems.upgrades)
			{
				if (itemstack.getItemDamage() == 3)
				{
					return (-BASE_RANGE - 1);
				}
				if (itemstack.getItemDamage() == 0)
				{
					upgrades += (itemstack.stackSize * 8);
				}
				if (itemstack.getItemDamage() == 1)
				{
					upgrades += (itemstack.stackSize * 32);
				}
				if (itemstack.getItemDamage() == 2)
				{
					upgrades += (itemstack.stackSize * 128);
				}
			}
		}
		return upgrades;
	}
	
	public int getMaxRange()
	{
		return BASE_RANGE + getExtraRange();
	}
	
	public boolean canDimensionJump() {
		return getMaxRange() == -1;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		super.setInventorySlotContents(i, itemstack);
		checkForTransportDevice();
	}
	
	private void checkForTransportDevice() {
		ItemStack itemstack1 = getStackInSlot(3);
		ItemStack itemstack2 = getStackInSlot(4);
		if (itemstack1 != null && itemstack1.getItem() == ModItems.transportDevice)
		{
			if (itemstack2 != null) { return; }
			//items[3] = null;
			//items[4] = itemstack;
			setInventorySlotContents(3, null);
			setInventorySlotContents(4, itemstack1);
			//onInventoryChanged();
			((ItemTransportDevice)itemstack1.getItem()).setLinkPos(itemstack1, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.TRANSPORTER_BEACON;
	}

	public boolean havePowerToJump() {
		return havePowerToWork();
	}

	public void useJumpPower() {
		useWorkPower();
	}
}
