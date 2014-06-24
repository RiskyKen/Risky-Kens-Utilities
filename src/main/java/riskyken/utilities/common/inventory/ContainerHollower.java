package riskyken.utilities.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageBlockPowerUpdate;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityUtilitiesBasePowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerHollower extends Container {
	
	private TileEntityDeviceHollower deviceHollower;
	
	public ContainerHollower(InventoryPlayer invPlayer, TileEntityDeviceHollower deviceHollower)
	{
		this.deviceHollower = deviceHollower;
		
		addSlotToContainer(new Slot(deviceHollower, 0, 8, 30));
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 158));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 100 + y * 18));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return deviceHollower.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        Slot slot = getSlot(slotID);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();
            
    		if (slotID == 0)
    		{
                if (!this.mergeItemStack(stack, 10, 37, false))
                {
                    if (!this.mergeItemStack(stack, 1, 10, false))
                    {
                        return null;
                    }
                }
    		}
    		else
    		{
                if (!this.mergeItemStack(stack, 0, 1, false))
                {
                    return null;
                }
    		}
    		
    		if (stack.stackSize == 0) {
    			slot.putStack(null);
    		}
    		else {
    			slot.onSlotChanged();
    		}
    		
    		slot.onPickupFromSlot(player, stack);
    		
    		return result;
        }

        return null;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		player.sendProgressBarUpdate(this, 0, deviceHollower.getState());
		player.sendProgressBarUpdate(this, 1, deviceHollower.isIgnoreMeta() ? 1 : 0);
		player.sendProgressBarUpdate(this, 2, deviceHollower.isLeaveWalls() ? 1 : 0);
		
		oldPowerLevel = (int)deviceHollower.getPowerLevel();
		oldIgnoreMeta = deviceHollower.isIgnoreMeta();
		oldLeaveWalls = deviceHollower.isLeaveWalls();
	}
	
	private int oldStatusText;
	private int oldPowerLevel;
	private boolean oldIgnoreMeta;
	private boolean oldLeaveWalls;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object player : crafters)
		{
			if (deviceHollower.getState() != oldStatusText) {
				((ICrafting)player).sendProgressBarUpdate(this, 0, deviceHollower.getState());
				oldStatusText = deviceHollower.getState();
			}
			if (deviceHollower.isIgnoreMeta() != oldIgnoreMeta) {
				((ICrafting)player).sendProgressBarUpdate(this, 1, deviceHollower.isIgnoreMeta() ? 1 : 0);
				oldIgnoreMeta = deviceHollower.isIgnoreMeta();
			}
			if (deviceHollower.isLeaveWalls() != oldLeaveWalls) {
				((ICrafting)player).sendProgressBarUpdate(this, 2, deviceHollower.isLeaveWalls() ? 1 : 0);
				oldLeaveWalls = deviceHollower.isLeaveWalls();
			}
			if (deviceHollower.getPowerLevel() != oldPowerLevel) {
				if (player instanceof EntityPlayerMP) {
					oldPowerLevel = (int)deviceHollower.getPowerLevel();
					PacketHandler.networkWrapper.sendTo(new MessageBlockPowerUpdate((TileEntityUtilitiesBasePowered)deviceHollower), (EntityPlayerMP)player);
				}
			}
		}
		
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
			case 0:
				deviceHollower.setState(data);
				break;
			case 1:
				deviceHollower.setIgnoreMeta(data == 0 ? false : true);
				break;
			case 2:
				deviceHollower.setLeaveWalls(data == 0 ? false : true);
				break;
		}
	}

	public TileEntityDeviceHollower getTileEntity() {
		return deviceHollower;
	}
}
