package riskyken.utilities.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import riskyken.utilities.client.gui.SlotOutput;
import riskyken.utilities.client.gui.SlotTransportDevice;
import riskyken.utilities.client.gui.SlotTransporterUpgrade;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageBlockPowerUpdate;
import riskyken.utilities.common.tileentities.TileEntityTransporterBeacon;
import riskyken.utilities.common.tileentities.TileEntityUtilitiesBasePowered;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTransporterBeacon extends Container {
	
	private TileEntityTransporterBeacon transporterBeacon;
	
	public ContainerTransporterBeacon(InventoryPlayer invPlayer, TileEntityTransporterBeacon transporterBeacon)
	{
		this.transporterBeacon = transporterBeacon;
		
		for (int x = 0; x < 3; x++) {
			addSlotToContainer(new SlotTransporterUpgrade(transporterBeacon, x, 8 + 18 * x, 48));
		}
		
		addSlotToContainer(new SlotTransportDevice(transporterBeacon, 3, 98, 48));
		addSlotToContainer(new SlotOutput(transporterBeacon, 4, 134, 48));
		
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
		return transporterBeacon.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        Slot slot = getSlot(slotID);
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();
            
    		if (slotID < 5)
    		{
                if (!this.mergeItemStack(stack, 14, 41, false))
                {
                    if (!this.mergeItemStack(stack, 5, 14, false))
                    {
                        return null;
                    }
                }
    		}
    		else
    		{
    			
    			if (stack.getItem() == ModItems.upgrades)
    			{
                    if (!this.mergeItemStack(stack, 0, 3, false))
                    {
                        return null;
                    }
    			}
    			
    			if (stack.getItem() == ModItems.transportDevice)
    			{
                    if (!this.mergeItemStack(stack, 3, 4, false))
                    {
                        return null;
                    }
                    
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
	
	public TileEntityTransporterBeacon getTileEntity() {
		return transporterBeacon;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting player)
	{
		super.addCraftingToCrafters(player);
		//player.sendProgressBarUpdate(this, 1, transporterBeacon.getPowerLevel());
	}
	
	private int oldPowerLevel;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object player : crafters)
		{
			if (transporterBeacon.getPowerLevel() != oldPowerLevel)
			{
				if (player instanceof EntityPlayerMP) {
					oldPowerLevel = (int)transporterBeacon.getPowerLevel();
					PacketHandler.networkWrapper.sendTo(new MessageBlockPowerUpdate((TileEntityUtilitiesBasePowered)transporterBeacon), (EntityPlayerMP)player);
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
			case 1:
				transporterBeacon.powerLevel = data;
				break;
		}
	}
}
