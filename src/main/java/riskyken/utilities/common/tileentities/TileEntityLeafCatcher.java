package riskyken.utilities.common.tileentities;

import java.util.ArrayList;

import riskyken.utilities.common.lib.LibBlockNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityLeafCatcher extends TileEntityUtilitiesBase {

	private static final String TAG_TICK_COOLDOWN = "tickCooldown";
	
	private static final int LEAF_CATCHER_RANGE = 16;
	private static final int TICK_RATE = 200;
	
	private int tickCooldown = TICK_RATE;
	
	public TileEntityLeafCatcher() {
		items = new ItemStack[1];
	}
	
	@Override
	public void updateEntity() {
		if (worldObj.isRemote) { return; }
		
		tickCooldown--;
		if (tickCooldown >= 0) { return; }
		tickCooldown = TICK_RATE;
		
		findLeaveBlock(worldObj, xCoord, yCoord + 1, zCoord);
	}
	
	private void findLeaveBlock(World world, int x, int y, int z) {
		for (int i = 0; i < LEAF_CATCHER_RANGE; i++) {
			if (!world.isAirBlock(x, y + 1 * i, z)) {
				Block block = world.getBlock(x, y + 1 * i, z);
				if (block.getMaterial() == Material.leaves) {
					addDropsToInventory(getBlockDrops(world, block, x, y + 1 * i, z));
				}
				break;
			}
		}
	}
	
	private ArrayList<ItemStack> getBlockDrops(World world, Block block, int x, int y, int z) {
		int meta = worldObj.getBlockMetadata(x, y, z);
		ArrayList<ItemStack> drops = block.getDrops(world, x, y, z, meta, 0);
		return drops;
	}
	
	private void addDropsToInventory(ArrayList<ItemStack> drops) {
		for (int i = 0; i < drops.size(); i++) {
			if (!drops.get(i).getUnlocalizedName().contains("sapling")) {
				continue;
			}
			ItemStack itemStack = getStackInSlot(0);
			if (itemStack == null) {
				setInventorySlotContents(0, drops.get(i));
			} else {
				if (itemStack.getItem() == drops.get(i).getItem() & itemStack.getItemDamage() == drops.get(i).getItemDamage()) {
					int newStackSize = itemStack.stackSize + drops.get(i).stackSize;
					setInventorySlotContents(0, new ItemStack(itemStack.getItem(), newStackSize, itemStack.getItemDamage()));
				}
			}
		}
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.LEAF_CATCHER;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(TAG_TICK_COOLDOWN, tickCooldown);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		tickCooldown = compound.getInteger(TAG_TICK_COOLDOWN);
	}
}
