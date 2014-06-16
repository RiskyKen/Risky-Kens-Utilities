package riskyken.utilities.common.inventory;

import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityGift;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGift extends Container {

	private TileEntityGift tileEntityGift;
	
	public ContainerGift(InventoryPlayer invPlayer, TileEntityGift tileEntityGift) {
		this.tileEntityGift = tileEntityGift;
		
		//addSlotToContainer(new Slot(tileEntityGift, 0, 26, 36));
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				addSlotToContainer(new Slot(tileEntityGift, x + (y * 3), 62 + 18 * x, 17 + y * 18));
			}
		}
		
		
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
		return tileEntityGift.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        Slot slot = getSlot(slotID);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();
            
    		if (slotID <= 8)
    		{
                if (!this.mergeItemStack(stack, 18, 45, false))
                {
                    if (!this.mergeItemStack(stack, 9, 18, false))
                    {
                        return null;
                    }
                }
    		}
    		else
    		{
                if (!this.mergeItemStack(stack, 0, 9, false))
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

}
