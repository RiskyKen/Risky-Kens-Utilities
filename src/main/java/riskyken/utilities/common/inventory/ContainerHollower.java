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
		
		addSlotToContainer(new Slot(deviceHollower, 0, 26, 36));
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
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
	public void addCraftingToCrafters(ICrafting player)
	{
		super.addCraftingToCrafters(player);
		player.sendProgressBarUpdate(this, 0, deviceHollower.getStatusInt());
		//player.sendProgressBarUpdate(this, 1, deviceHollower.getPowerLevel());
	}
	
	private int oldStatusText;
	private int oldPowerLevel;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object player : crafters)
		{
			if (deviceHollower.getStatusInt() != oldStatusText)
			{
				((ICrafting)player).sendProgressBarUpdate(this, 0, deviceHollower.getStatusInt());
				oldStatusText = deviceHollower.getStatusInt();
				//System.out.println(ModInformation.ID +  " block status update status");
			}
			if (deviceHollower.getPowerLevel() != oldPowerLevel)
			{
				if (player instanceof EntityPlayerMP) {
					oldPowerLevel = (int)deviceHollower.getPowerLevel();
					PacketHandler.networkWrapper.sendTo(new MessageBlockPowerUpdate((TileEntityUtilitiesBasePowered)deviceHollower), (EntityPlayerMP)player);
				}
			}

		}
		
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
	{
		super.updateProgressBar(id, data);
		switch (id)
		{
			case 0:
				deviceHollower.state = TileEntityDeviceHollower.BlockState.values()[(byte)data];
				break;
			case 1:
				deviceHollower.powerLevel = data;
				break;
		}
	}

	public TileEntityDeviceHollower getTileEntity() {
		return deviceHollower;
	}
}
