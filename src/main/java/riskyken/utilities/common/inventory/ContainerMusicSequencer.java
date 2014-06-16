package riskyken.utilities.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.client.gui.SlotLinkCard;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import riskyken.utilities.common.tileentities.TileEntityMusicSequencer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMusicSequencer extends Container {

	private TileEntityMusicSequencer musicSequencer;
	
	public ContainerMusicSequencer(InventoryPlayer invPlayer, TileEntityMusicSequencer musicSequencer) {
		this.musicSequencer = musicSequencer;
		addSlotToContainer(new Slot(musicSequencer, 0, 192, 157));
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 215));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 157 + y * 18));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return musicSequencer.isUseableByPlayer(entityplayer);
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

	public TileEntityMusicSequencer getTileEntity() {
		return musicSequencer;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting player)
	{
		super.addCraftingToCrafters(player);
		
		oldNodes = musicSequencer.getNodesData();
		
		for (int i = 0; i < oldNodes.length; i++) {
			player.sendProgressBarUpdate(this, i + 1, oldNodes[i] ? 1 : 0);
		}
		//player.sendProgressBarUpdate(this, 96, musicSequencer.collumCount);
		
	}
	
	private boolean oldNodes[];
	private int oldCollumCount;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object player : crafters)
		{
			if ( oldNodes != null) {
				boolean[] curNodes = musicSequencer.getNodesData();
				
				for (int i = 0; i < curNodes.length; i++) {
					if (oldNodes[i] != curNodes[i]) {
						
						((ICrafting)player).sendProgressBarUpdate(this, i + 1, curNodes[i] ? 1 : 0);
						oldNodes[i] = curNodes[i];
					}
				}
			}
			
			/*
			if (oldCollumCount != musicSequencer.collumCount) {
				
				((ICrafting)player).sendProgressBarUpdate(this, 96, musicSequencer.collumCount);
				oldCollumCount = musicSequencer.collumCount;
			}
			 */
			
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
	{
		super.updateProgressBar(id, data);
		System.out.println("got data " + (id - 1));
		if (id > 0) {
			musicSequencer.updateNodeData(id - 1, data == 0 ? false : true);
		}
		
		switch (id) {
			case 96:
				//musicSequencer.collumCount = data;
				break;
		}
	}

}
