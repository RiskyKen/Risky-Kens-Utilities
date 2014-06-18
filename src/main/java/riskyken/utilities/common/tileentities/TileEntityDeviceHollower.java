package riskyken.utilities.common.tileentities;

import java.util.ArrayList;

import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.lib.LibBlockNames;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDeviceHollower extends TileEntityUtilitiesBasePowered {
	
	private Block targetBlock;
	private int targetBlockMeta;
	
	private int blocksScanned;
	
	private ArrayList airBlockOpenList = null;
	private ArrayList airBlockClosedList = null;
	private ArrayList removeBlockOpenList = null;
	
	public enum BlockState
	{
		Scan_Needed(0),
		Ready(1),
		Scanning(2),
		NoTarget(3),
		InvalidArea(4),
		Running(5),
		Finished(6),
		Unknown(7);
		
		private int value;
	    private BlockState(int value) {
	        this.value = value;
	    }
	}
	
	public BlockState state;
	
	public TileEntityDeviceHollower() {
		super(100,500);
		items = new ItemStack[1];
		state = BlockState.Scan_Needed;
	}
	
	private void checkMeta()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if (state == BlockState.Scan_Needed)
		{
			if (meta != 0)
			{ worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2); }
		}
	}
	
	private void startBlockSearch() {
		if (state == BlockState.Scanning) { return; }
		if (state == BlockState.Running) { return; }
		//System.out.println("Starting block search");
		airBlockOpenList = new ArrayList();
		airBlockClosedList = new ArrayList();
		removeBlockOpenList = new ArrayList();
		
		blocksScanned = 0;
		
		if (getStackInSlot(0) != null)
		{
			targetBlock = Block.getBlockFromItem(getStackInSlot(0).getItem());
			targetBlockMeta = getStackInSlot(0).getItemDamage();
		}
		else
		{
			state = BlockState.NoTarget;
			return;
		}
		if (!worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) { return; }
		
		addBlockToList(new Coord3D(xCoord, yCoord + 1, zCoord), airBlockOpenList);
		
		state = BlockState.Scanning;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
	}
	
	private void blockSearchTick() {

		switch (state)
		{
		case Scan_Needed:
			checkMeta();
			break;
		case Scanning:
			scanAirBlocks();
			break;
		case Running:
			hollowBlocks();
			break;
		default:
			break;
		}
	}
	
	private void scanAirBlocks() {
		if (airBlockOpenList.size() == 0) {
			//System.out.println("End of open block list");
			//System.out.println(airBlockClosedList.size() + " blocks on air closed list");
			//System.out.println(removeBlockOpenList.size() + " blocks on remove open list");
			airBlockClosedList.clear();
			airBlockOpenList.clear();
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 2);
			state = BlockState.Ready;
			return;
		}
		
		//System.out.println("Scanning open " + airBlockOpenList.size() + " closed " +  airBlockClosedList.size() + " remove " + removeBlockOpenList.size() + " scanned:" + blocksScanned);
		
		
		int blocksToScan = ConfigHandler.hollowerBlockSearchPerTick;
				
		if (blocksToScan > airBlockOpenList.size()) { blocksToScan = airBlockOpenList.size(); }
		
		for (int i = 0; i < blocksToScan; i++)
		{
			addBlocksAroundBlock((Coord3D)airBlockOpenList.get(0), airBlockOpenList, airBlockClosedList, true);
			moveBlockToClosedList((Coord3D)airBlockOpenList.get(0), airBlockOpenList, airBlockClosedList);
		}
		
		if (blocksScanned > ConfigHandler.hollowerBlockSearchMax) {
			state = BlockState.InvalidArea;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
			//System.out.println("Block overflow");
		}
	}
	
	private void hollowBlocks() {
		if (removeBlockOpenList.size() == 0) {
			//System.out.println("End of hollow block list");
			state = BlockState.Finished;
			removeBlockOpenList.clear();
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
			return;
		}
		
		if (!havePowerToWork())
			return;
		
		useWorkPower();
		
		HollowBlock((Coord3D)removeBlockOpenList.get(0));
		removeBlockOpenList.remove(0);
	}
	
	private void startHollowing() {
		if (state == BlockState.Ready)
		{
			state = BlockState.Running;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 3, 2);
		}
	}
	
	private void HollowBlock(Coord3D blockCoord) {
		
		if (!worldObj.isAirBlock(blockCoord.x, blockCoord.y, blockCoord.z)) {
			
			Block block = worldObj.getBlock(blockCoord.x, blockCoord.y, blockCoord.z);
			int blockMeta = worldObj.getBlockMetadata(blockCoord.x, blockCoord.y, blockCoord.z);
			
			//DOTO
			ArrayList<ItemStack> items = block.getDrops(worldObj, blockCoord.x, blockCoord.y, blockCoord.z, blockMeta, 0);
			
			for (int i = 0; i < items.size(); i++) {
				EntityItem droppedItem = new EntityItem(worldObj, (double)xCoord + 0.5, (double)yCoord + 1.2, (double)zCoord + 0.5, items.get(i));
				float mult = 0.05F;
				droppedItem.motionX = (-0.5F + worldObj.rand.nextFloat()) * mult;
				droppedItem.motionY = (8 + worldObj.rand.nextFloat()) * mult;
				droppedItem.motionZ = (-0.5F + worldObj.rand.nextFloat()) * mult;
				worldObj.spawnEntityInWorld(droppedItem);
			}
			worldObj.func_147480_a(blockCoord.x, blockCoord.y, blockCoord.z, false);
			//worldObj.func_147478_e(p_147478_1_, p_147478_2_, p_147478_3_, p_147478_4_)
			//worldObj.setBlockToAir(blockCoord.x, blockCoord.y, blockCoord.z);
			
		}
	}
	
	private void addBlockToList(Coord3D blockCoord, ArrayList list) {
		list.add(blockCoord);
		blocksScanned++;
	}
	
	private void addBlocksAroundBlock(Coord3D blockCoord, ArrayList openList, ArrayList closedList, boolean allowAir) {
		ArrayList checkBlocks = getBlockAroundBlock(blockCoord);
		for (int i = 0; i < checkBlocks.size(); i++)
		{
			checkAndAddBlock((Coord3D)checkBlocks.get(i), openList, closedList, allowAir);
		}
	}
	
	private ArrayList getBlockAroundBlock(Coord3D blockCoord) {
		ArrayList result = new ArrayList();
		result.add(new Coord3D(blockCoord.x, blockCoord.y + 1, blockCoord.z));
		result.add(new Coord3D(blockCoord.x, blockCoord.y - 1, blockCoord.z));
		result.add(new Coord3D(blockCoord.x + 1, blockCoord.y, blockCoord.z));
		result.add(new Coord3D(blockCoord.x - 1, blockCoord.y, blockCoord.z));
		result.add(new Coord3D(blockCoord.x, blockCoord.y, blockCoord.z + 1));
		result.add(new Coord3D(blockCoord.x, blockCoord.y, blockCoord.z - 1));
		return result;
	}
	
	private void checkAndAddBlock(Coord3D blockCoord, ArrayList openList, ArrayList closedList, boolean allowAir) {

		if (worldObj.getBlock(blockCoord.x, blockCoord.y, blockCoord.z) == targetBlock)
		{
			if (worldObj.getBlockMetadata(blockCoord.x, blockCoord.y, blockCoord.z) == targetBlockMeta)
			{
				if (isValidRemoveBlock(blockCoord))
				{
					if (!isOnList(blockCoord, openList)) {
						if (!isOnList(blockCoord, closedList)) {
							addBlockToList(blockCoord, openList);
							addBlockToList(blockCoord, removeBlockOpenList);
						}
					}
				}
				//return;
			}
		}
		
		if (worldObj.isAirBlock(blockCoord.x, blockCoord.y, blockCoord.z)) {
			if (!isOnList(blockCoord, openList)) {
				if (!isOnList(blockCoord, closedList)) {
					addBlockToList(blockCoord, openList);
				}
			}
		}
	}
	
	private boolean isValidRemoveBlock(Coord3D blockCoord) {
		ArrayList checkBlocks = getBlockAroundBlock(blockCoord);
		
		for (int i = 0; i < checkBlocks.size(); i++)
		{
			Coord3D thisCoord = (Coord3D)checkBlocks.get(i);
			
			if (worldObj.isAirBlock(thisCoord.x, thisCoord.y, thisCoord.z)) {
				if (!isOnList(thisCoord, airBlockOpenList))
				{
					if (!isOnList(thisCoord, airBlockClosedList))
					{
						return false;
					}	
				}
			}
			
			
			Block block = worldObj.getBlock(thisCoord.x, thisCoord.y, thisCoord.z);
			int blockMeta = worldObj.getBlockMetadata(thisCoord.x, thisCoord.y, thisCoord.z);
			
			if (!worldObj.isAirBlock(thisCoord.x, thisCoord.y, thisCoord.z)) {
				if (block != targetBlock) {
					if (block.isReplaceable(worldObj, thisCoord.x, thisCoord.y, thisCoord.z))
					{
						return false;
					}
				}
				else
				{
					if (blockMeta != targetBlockMeta) {
						if (block.isReplaceable(worldObj, thisCoord.x, thisCoord.y, thisCoord.z))
						{
							return false;
						}
					}
				}
			}
			
		}
		
		return true;
	}
	
	private void moveBlockToClosedList(Coord3D blockCoord, ArrayList openList, ArrayList closedList) {
		for (int i = 0; i < openList.size(); i++)
		{
			Coord3D iBlock = (Coord3D)openList.get(i);
			if (iBlock.equals(blockCoord)) {
				openList.remove(i);
				closedList.add(blockCoord);
				return;
			}
		}
	}
	
	private boolean isOnList(Coord3D blockCoord, ArrayList list) {
		for (int i = 0; i < list.size(); i++)
		{
			Coord3D iBlock = (Coord3D)list.get(i);
			if (iBlock.equals(blockCoord)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote)
		{
			blockSearchTick();
		}	
		super.updateEntity();
	}
	
	public String getStatusText() {
		return state.toString();
	}
	
	public int getStatusInt() {
		return state.value;
	}

	@Override
	public String getInventoryName() {
		return LibBlockNames.HOLLOWER;
	}
	
	public void receiveButtonEvent(short buttonId) {
		switch (buttonId) {
			case 0:
				startBlockSearch();
				break;
			case 1:
				startHollowing();
				break;
		}
	}
	
	private class Coord3D {
	    public int x;
	    public int y;
	    public int z;
	    
	    public Coord3D(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	    
	    public boolean equals(Coord3D blockCoord) {
			if (this.x == blockCoord.x) {
				if (this.y == blockCoord.y) {
					if (this.z == blockCoord.z) {
						return true;
					}
				}
			}
			return false;
	    }
	}
}
