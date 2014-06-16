package riskyken.utilities.common.tileentities;

import javax.swing.Icon;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import riskyken.utilities.common.blocks.BlockFloodLightTypes;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.entities.EntityFloodLightDespawner;

public class TileEntityFloodLight extends TileEntity {
	
	ForgeDirection facing = ForgeDirection.UP;
	BlockFloodLightTypes type;
	int updateCount;
	int blockCount;
	boolean addingBlocks = true;
	boolean active = true;
	
	public TileEntityFloodLight() {
		
	}
	
	public TileEntityFloodLight(BlockFloodLightTypes type) {
		this.type = type;
	}
	
	@Override
	public void updateEntity() {
		if (worldObj.isRemote) { return; }
		
		updateCount++;
		if (updateCount >= 5) { updateCount = 0; }
		else { return; }
		
		if (type != null) {
			updateFloodLight();
		}
	}
	
	private void updateFloodLight() {
		blockCount++;
		
		if (blockCount > type.getLightRange()) {
			blockCount = 1;
			addingBlocks = true;
		}
		
		int targetX = xCoord + facing.offsetX * blockCount;
		int targetY = yCoord + facing.offsetY * blockCount;
		int targetZ = zCoord + facing.offsetZ * blockCount;
		
		if (!worldObj.isAirBlock(targetX, targetY, targetZ)) {
			if (worldObj.getBlockLightOpacity(targetX, targetY, targetZ) > 0) {
				if (!type.isPhased()) {
					addingBlocks = false;
				} else if(worldObj.getBlock(targetX, targetY, targetZ) == ModBlocks.phasedLightSponge) {
					addingBlocks = false;
				}
			}
		} else {
			if (addingBlocks) {
				if (worldObj.getBlock(targetX, targetY, targetZ) != ModBlocks.utilitieLight) {
					worldObj.setBlock(targetX, targetY, targetZ, ModBlocks.utilitieLight, facing.ordinal(), 2);
				}
			} else {
				if (worldObj.getBlock(targetX, targetY, targetZ) == ModBlocks.utilitieLight) {
					if (worldObj.getBlockMetadata(targetX, targetY, targetZ) == facing.ordinal()) {
						worldObj.setBlockToAir(targetX, targetY, targetZ);
					}
				}
			}
		}
		
		markDirty();
	}
	
	public void preRemove() {
		EntityFloodLightDespawner entity = new EntityFloodLightDespawner(worldObj, xCoord, yCoord, zCoord, type, facing);
		worldObj.spawnEntityInWorld(entity);
	}
	
	public BlockFloodLightTypes getType() {
		return type;
	}
	
	public void setBlockFacing(ForgeDirection facing, boolean changed) {
		if (changed) { preRemove(); }
		this.facing = facing;
		blockCount = 0;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	public IIcon getIconForSide(int side, int meta) {
		return ModBlocks.floodLight.getFaceIcon(side, this.facing.ordinal(), this.active, this.type.ordinal());
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 5, tagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound compound = packet.func_148857_g();
		facing = ForgeDirection.getOrientation(compound.getInteger("facing"));
		type = BlockFloodLightTypes.getBlockLightType(compound.getInteger("type"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("facing", facing.ordinal());
		compound.setInteger("type", type.ordinal());
		compound.setInteger("updateCount", updateCount);
		compound.setInteger("blockCount", blockCount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		facing = ForgeDirection.getOrientation(compound.getInteger("facing"));
		type = BlockFloodLightTypes.getBlockLightType(compound.getInteger("type"));
		updateCount = compound.getInteger("updateCount");
		blockCount = compound.getInteger("blockCount");
	}
}
