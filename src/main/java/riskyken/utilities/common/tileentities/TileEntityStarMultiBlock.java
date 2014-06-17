package riskyken.utilities.common.tileentities;

import riskyken.utilities.common.blocks.BlockFloodLightTypes;
import riskyken.utilities.common.blocks.ModBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStarMultiBlock extends TileEntity {
	
	private static final String TAG_TYPE_ID = "typeId";
	private static final String TAG_TICK_COOLDOWN = "tickCooldown";
	
	private static final int TICK_RATE = 20;
	
	private int typeId;
	private int tickCooldown = TICK_RATE;
	
	public TileEntityStarMultiBlock() {}
	
	public TileEntityStarMultiBlock(int typeId) {
		this.typeId = typeId;
	}
	
	@Override
	public void updateEntity() {
		if (worldObj.isRemote) { return; }
		
		tickCooldown--;
		if (tickCooldown >= 0) { return; }
		tickCooldown = TICK_RATE;
		System.out.println("star check tick");
		checkMultiBlock();
	}
	
	private void checkMultiBlock() {
		switch (typeId) {
		case 0:
			checkForStar1();
			break;
		case 1:
			checkForStar2();
			break;
		case 2:
			checkForStar3();
			break;
		case 3:
			checkForStar4();
			break;
		}
	}
	
	private void checkForStar1() {
		if (!checkForActiveBurnners(xCoord, yCoord, zCoord, 1)) { return; }
		
		removeBurnners(xCoord, yCoord, zCoord, 1);
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.starLight, typeId, 2);
		System.out.println("found star 1");
	}
	
	private void checkForStar2() {
		if (!checkForLayer1(xCoord, yCoord, zCoord)) { return; }
		if (!checkForActiveBurnners(xCoord, yCoord, zCoord, 2)) { return; }
		
		replaceLayer1(xCoord, yCoord, zCoord);
		removeBurnners(xCoord, yCoord, zCoord, 2);
		
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.starLight, typeId, 2);
		System.out.println("found star 2");
	}
	
	private void checkForStar3() {
		if (!checkForLayer1(xCoord, yCoord, zCoord)) { return; }
		
		if (!checkForActiveBurnners(xCoord, yCoord, zCoord, 2)) { return; }
		
		replaceLayer1(xCoord, yCoord, zCoord);
		removeBurnners(xCoord, yCoord, zCoord, 2);
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.starLight, typeId, 2);
		System.out.println("found star 3");
	}
	
	private void checkForStar4() {
		if (!checkForLayer1(xCoord, yCoord, zCoord)) { return; }
		
		if (!checkForActiveBurnners(xCoord, yCoord, zCoord, 3)) { return; }
		
		replaceLayer1(xCoord, yCoord, zCoord);
		removeBurnners(xCoord, yCoord, zCoord, 3);
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.starLight, typeId, 2);
		System.out.println("found star 4");
	}
	
	private boolean checkForLayer1(int x, int y, int z) {
		if (!checkMultiBlockMeta(x, y + 1, z, 6)) { return false; }
		if (!checkMultiBlockMeta(x, y - 1, z, 6)) { return false; }
		if (!checkMultiBlockMeta(x + 1, y, z, 6)) { return false; }
		if (!checkMultiBlockMeta(x - 1, y, z, 6)) { return false; }
		if (!checkMultiBlockMeta(x, y, z + 1, 6)) { return false; }
		if (!checkMultiBlockMeta(x, y, z - 1, 6)) { return false; }
		return true;
	}
	
	private void replaceLayer1(int x, int y, int z) {
		worldObj.setBlock(x + 1, y, z, ModBlocks.utilitieSolid, 6, 2);
		worldObj.setBlock(x - 1, y, z, ModBlocks.utilitieSolid, 6, 2);
		worldObj.setBlock(x, y + 1, z, ModBlocks.utilitieSolid, 6, 2);
		worldObj.setBlock(x, y - 1, z, ModBlocks.utilitieSolid, 6, 2);
		worldObj.setBlock(x, y, z + 1, ModBlocks.utilitieSolid, 6, 2);
		worldObj.setBlock(x, y, z - 1, ModBlocks.utilitieSolid, 6, 2);
	}
	
	private boolean checkForActiveBurnners(int x, int y, int z, int offset) {
		if (!checkMultiBlockMeta(x, y + offset, z, 5)) { return false; }
		if (!checkMultiBlockMeta(x, y - offset, z, 5)) { return false; }
		if (!checkMultiBlockMeta(x + offset, y, z, 5)) { return false; }
		if (!checkMultiBlockMeta(x - offset, y, z, 5)) { return false; }
		if (!checkMultiBlockMeta(x, y, z + offset, 5)) { return false; }
		if (!checkMultiBlockMeta(x, y, z - offset, 5)) { return false; }
		return true;
	}
	
	private void removeBurnners(int x, int y, int z, int offset) {
		worldObj.setBlockToAir(x + offset, y, z);
		worldObj.setBlockToAir(x - offset, y, z);
		worldObj.setBlockToAir(x, y + offset, z);
		worldObj.setBlockToAir(x, y - offset, z);
		worldObj.setBlockToAir(x, y, z + offset);
		worldObj.setBlockToAir(x, y, z - offset);
	}
	
	private boolean checkMultiBlockMeta(int x, int y, int z, int meta) {
		if (worldObj.getBlock(x, y, z) != ModBlocks.starMultiBlock) { return false; }
		if (worldObj.getBlockMetadata(x, y, z) != meta) { return false; }
		return true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(TAG_TYPE_ID, typeId);
		compound.setInteger(TAG_TICK_COOLDOWN, tickCooldown);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		typeId = compound.getInteger(TAG_TYPE_ID);
		tickCooldown = compound.getInteger(TAG_TICK_COOLDOWN);
	}
}
