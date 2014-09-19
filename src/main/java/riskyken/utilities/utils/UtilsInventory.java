package riskyken.utilities.utils;

import java.awt.Rectangle;
import java.util.ArrayList;

import cpw.mods.fml.common.Optional;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface="buildcraft.api.transport.IPipeTile", modid="BuildCraft|Core", striprefs = true)
public class UtilsInventory {

	public static void placeItemStackInAdjacentInventory(World world, int x, int y, int z, ArrayList<ItemStack> items) {
		ForgeDirection[] directions = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
		for(ForgeDirection dir : directions){
			TileEntity te =  world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (te != null & te instanceof IInventory) {
				placeItemStackInInventory(world, (IInventory) te, items);
				if (items == null) { break; }
			}
		}
	}
	
	public static void placeItemStackInInventory(World world,  IInventory inventory, ArrayList<ItemStack> items) {
		for(int i = 0; i < items.size(); i++){
			ItemStack stack = items.get(i);
			
			for (int j = 0; j < inventory.getSizeInventory(); j++) {
				if (inventory.isItemValidForSlot(j, stack)) {
					ItemStack iStack = inventory.getStackInSlot(j);
					if (iStack == null) {
						inventory.setInventorySlotContents(j, stack);
						items.remove(i);
						break;
					}
					
					
					if (iStack.isItemEqual(stack) & iStack.stackSize < iStack.getMaxStackSize()) {
						if (iStack.stackSize + stack.stackSize <= stack.getMaxStackSize()) {
							inventory.setInventorySlotContents(j, new ItemStack(stack.getItem(), iStack.stackSize + stack.stackSize, stack.getItemDamage()));
							items.remove(i);
							break;
						} else {
							inventory.setInventorySlotContents(j, new ItemStack(stack.getItem(), stack.getMaxStackSize(), stack.getItemDamage()));
							stack.stackSize -= iStack.stackSize + stack.stackSize - stack.getMaxStackSize();
						}
					}
					
				}
			}
			
		}
	}
	
	/*
	public static void placeItemStackInAdjacentPipe(World world, int x, int y, int z, ArrayList<ItemStack> items) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity te =  world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if (te != null & te instanceof IPipeTile) {
				IPipeTile pipe = (IPipeTile) te;
				if (pipe.getPipeType() != PipeType.ITEM) {
					continue;
				}
				if (!pipe.isPipeConnected(dir.getOpposite())) {
					continue;
				}
				
				for(int i = 0; i < items.size(); i++){
					pipe.injectItem(items.get(i), true, dir.getOpposite());
					items.remove(i);
				}
			}
		}
	}
	*/
	public static void spawnItemStackiInWorld(World world, int x, int y, int z, ArrayList<ItemStack> items) {
		for (int i = 0; i < items.size(); i++) {
			EntityItem droppedItem = new EntityItem(world, (double)x + 0.5, (double)y + 1.3, (double)z + 0.5, items.get(i));
			float mult = 0.05F;
			droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
			droppedItem.motionY = (8 + world.rand.nextFloat()) * mult;
			droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
			world.spawnEntityInWorld(droppedItem);
		}
	}
}
