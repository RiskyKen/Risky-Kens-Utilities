package riskyken.utilities.common.tileentities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.utils.Utils;
import riskyken.utilities.utils.UtilsInventory;
import riskyken.utilities.utils.Vector3;

public class TileEntityDeviceHollower extends TileEntityUtilitiesBasePowered {
	
	private static final String TAG_IGNORE_META = "ignoreMeta";
	private static final String TAG_LEAVE_WALLS = "leaveWalls";
	
	private Block targetBlock;
	private int targetBlockMeta;
	
	private int blocksScanned;
	private boolean ignoreMeta = true;
	private boolean leaveWalls = true;
	private static final boolean USE_POWER = true;
	
	private LinkedHashMap<String, Vector3> openBlockList;
	private LinkedHashMap<String, Vector3> closedBlockList;
	private LinkedHashMap<String, Vector3> removeBlockList;
	
	private BlockState state;
	
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
		
		if (getStackInSlot(0) != null) {
			targetBlock = Block.getBlockFromItem(getStackInSlot(0).getItem());
			targetBlockMeta = getStackInSlot(0).getItemDamage();
		} else {
			state = BlockState.NoTarget;
			return;
		}
		
		openBlockList = new LinkedHashMap<String, Vector3>();
		closedBlockList = new LinkedHashMap<String, Vector3>();
		removeBlockList = new LinkedHashMap<String, Vector3>();
		blocksScanned = 0;
		
		addBlockToList(new Vector3(xCoord, yCoord, zCoord), openBlockList);
		
		state = BlockState.Scanning;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
	}
	
	private void blockSearchTick() {
		switch (state) {
		case Scan_Needed:
			checkMeta();
			break;
		case Scanning:
			scanBlocks();
			break;
		case Running:
			hollowBlocks();
			break;
		default:
			break;
		}
	}
	
	private void scanBlocks() {
		if (openBlockList.size() == 0) {
			openBlockList.clear();
			closedBlockList.clear();
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 2);
			state = BlockState.Ready;
			return;
		}
		
		int blocksToScan = ConfigHandler.hollowerBlockSearchPerTick * 10;
				
		if (blocksToScan > openBlockList.size()) { blocksToScan = openBlockList.size(); }
		
		for (int i = 0; i < blocksToScan; i++) {
			String key = (String) openBlockList.keySet().toArray()[0];
			addBlocksAroundBlock((Vector3)openBlockList.get(key), openBlockList, closedBlockList, true);
			moveBlockToClosedList((Vector3)openBlockList.get(key));
		}
		
		if (blocksScanned > ConfigHandler.hollowerBlockSearchMax * 10) {
			state = BlockState.InvalidArea;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		}
	}
	
	private void addBlockToList(Vector3 blockCoord, LinkedHashMap<String, Vector3> list) {
		list.put(blockCoord.hashString(), blockCoord);
		blocksScanned++;
	}
	
	private void addBlocksAroundBlock(Vector3 blockCoord, LinkedHashMap<String, Vector3> openList, LinkedHashMap<String, Vector3> closedList, boolean allowAir) {
		ArrayList<Vector3> checkBlocks = Utils.getBlockAroundBlock(blockCoord);
		for (int i = 0; i < checkBlocks.size(); i++) {
			checkAndAddBlock((Vector3)checkBlocks.get(i), allowAir);
		}
	}
	
	private void checkAndAddBlock(Vector3 blockCoord, boolean allowAir) {
		if (isTargetBlock(blockCoord)){
			if (isValidRemoveBlock(blockCoord)) {
				if (!openBlockList.containsKey(blockCoord.hashString())) {
					if (!closedBlockList.containsKey(blockCoord.hashString())) {
						addBlockToList(blockCoord, openBlockList);
						addBlockToList(blockCoord, removeBlockList);
					}
				}
			}
		}
		
		if (worldObj.isAirBlock(blockCoord.x, blockCoord.y, blockCoord.z)) {
			if (!openBlockList.containsKey(blockCoord.hashCode())) {
				if (!closedBlockList.containsKey(blockCoord.hashString())) {
					addBlockToList(blockCoord, openBlockList);
				}
			}
		}
	}
	
	private boolean isTargetBlock(Vector3 blockCoord) {
		if (worldObj.getBlock(blockCoord.x, blockCoord.y, blockCoord.z) != targetBlock) { return false; }
		if (!ignoreMeta) {
			if (worldObj.getBlockMetadata(blockCoord.x, blockCoord.y, blockCoord.z) != targetBlockMeta) { return false; }
		}
		return true;
	}
	
	private boolean isValidRemoveBlock(Vector3 blockCoord) {
		ArrayList checkBlocks = Utils.getBlockAroundBlock(blockCoord);
		
		for (int i = 0; i < checkBlocks.size(); i++) {
			Vector3 thisCoord = (Vector3)checkBlocks.get(i);
			
			if (worldObj.isAirBlock(thisCoord.x, thisCoord.y, thisCoord.z)) {
				if (!openBlockList.containsKey(thisCoord.hashString())) {
					if (!closedBlockList.containsKey(thisCoord.hashString())) {
						return false;
					}	
				}
			}
			
			Block block = worldObj.getBlock(thisCoord.x, thisCoord.y, thisCoord.z);
			if (!worldObj.isAirBlock(thisCoord.x, thisCoord.y, thisCoord.z)) {
				if (!isTargetBlock(thisCoord)) {
					if (block.isReplaceable(worldObj, thisCoord.x, thisCoord.y, thisCoord.z)) {
						return false;
					}
					if (leaveWalls) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void moveBlockToClosedList(Vector3 blockCoord) {
		openBlockList.remove(blockCoord.hashString());
		closedBlockList.put(blockCoord.hashString(), blockCoord);
	}
	
	private void startHollowing() {
		if (state == BlockState.Ready) {
			state = BlockState.Running;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 3, 2);
		}
	}
	
	private void hollowBlocks() {
		if (removeBlockList.size() == 0) {
			state = BlockState.Finished;
			removeBlockList.clear();
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
			return;
		}
		
		if (USE_POWER) {
			if (!havePowerToWork()) { return; }
			useWorkPower();
		}
		
		String key = (String) removeBlockList.keySet().toArray()[0];
		HollowBlock(removeBlockList.get(key));
		removeBlockList.remove(key);
	}
	
	private void HollowBlock(Vector3 blockCoord) {
		if (worldObj.isAirBlock(blockCoord.x, blockCoord.y, blockCoord.z)) { return; }
			
		Block block = worldObj.getBlock(blockCoord.x, blockCoord.y, blockCoord.z);
		int blockMeta = worldObj.getBlockMetadata(blockCoord.x, blockCoord.y, blockCoord.z);
		
		ArrayList<ItemStack> items = block.getDrops(worldObj, blockCoord.x, blockCoord.y, blockCoord.z, blockMeta, 0);
		
		if (items != null) {
			UtilsInventory.placeItemStackInAdjacentInventory(worldObj, xCoord, yCoord, zCoord, items);
		}
		if (items != null) {
			UtilsInventory.spawnItemStackiInWorld(worldObj, xCoord, yCoord, zCoord, items);
		}
		
		worldObj.func_147480_a(blockCoord.x, blockCoord.y, blockCoord.z, false);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote) { return; }
		blockSearchTick();
	}
	
	public String getStatusText() {
		return state.toString();
	}
	
	public int getState() {
		return state.value;
	}
	
	public void setState(int value) {
		state = TileEntityDeviceHollower.BlockState.values()[value];
	}
	
	public boolean isIgnoreMeta() {
		return ignoreMeta;
	}
	
	public void setIgnoreMeta(boolean ignoreMeta) {
		this.ignoreMeta = ignoreMeta;
		markDirty();
	}
	
	public boolean isLeaveWalls() {
		return leaveWalls;
	}
	
	public void setLeaveWalls(boolean leaveWalls) {
		this.leaveWalls = leaveWalls;
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return LibBlockNames.HOLLOWER;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean(TAG_IGNORE_META, this.ignoreMeta);
		compound.setBoolean(TAG_LEAVE_WALLS, this.leaveWalls);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        this.ignoreMeta = compound.getBoolean(TAG_IGNORE_META);
        this.leaveWalls = compound.getBoolean(TAG_LEAVE_WALLS);
	}
	
	public void receiveButtonEvent(short buttonId) {
		switch (buttonId) {
			case 0:
				startBlockSearch();
				break;
			case 1:
				startHollowing();
				break;
			case 2:
				setIgnoreMeta(!this.ignoreMeta);
				break;
			case 3:
				setLeaveWalls(!this.leaveWalls);
				break;
		}
	}
	
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
		
		public int value;
	    private BlockState(int value) {
	        this.value = value;
	    }
	}
}
