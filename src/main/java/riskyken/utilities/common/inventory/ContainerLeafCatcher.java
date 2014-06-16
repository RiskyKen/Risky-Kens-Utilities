package riskyken.utilities.common.inventory;

import riskyken.utilities.common.tileentities.TileEntityGift;
import riskyken.utilities.common.tileentities.TileEntityLeafCatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLeafCatcher extends Container {
	
	private TileEntityLeafCatcher tileEntityLeafCatcher;
	
	public ContainerLeafCatcher(InventoryPlayer invPlayer, TileEntityLeafCatcher tileEntityLeafCatcher) {
		this.tileEntityLeafCatcher = tileEntityLeafCatcher;
		
		addSlotToContainer(new Slot(tileEntityLeafCatcher, 0, 80, 35));
		/*
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				addSlotToContainer(new Slot(tileEntityLeafCatcher, x + (y * 3), 62 + 18 * x, 17 + y * 18));
			}
		}
		*/
		
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
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tileEntityLeafCatcher.isUseableByPlayer(entityplayer);
	}
}
