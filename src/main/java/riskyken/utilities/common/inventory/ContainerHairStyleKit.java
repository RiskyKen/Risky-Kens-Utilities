package riskyken.utilities.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerHairStyleKit extends Container {

	private EntityPlayer player;
	
	public ContainerHairStyleKit(EntityPlayer player) {
		this.player = player;
		
		/*
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(player.inventory, x, 8 + 18 * x, 142));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
			}
		}
		*/
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

}
