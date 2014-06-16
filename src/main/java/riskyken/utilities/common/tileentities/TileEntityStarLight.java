package riskyken.utilities.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import riskyken.utilities.common.blocks.BlockStarLightTypes;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.entities.EntityFloodLightDespawner;
import riskyken.utilities.common.entities.EntityStarLightDespawner;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageDimensionSound;
import riskyken.utilities.utils.Utils;

public class TileEntityStarLight  extends TileEntity {
	
	private static final String TAG_TYPE = "type";
	private static final String TAG_STARTUP_TICK = "startUpTick";
	
	public static final int lightBlockActiveMeta = 6;
	public static final int lightBlockRemoveMeta = 14;
	
	public static final int WARM_UP_TICK = 85;
	public static final int ACTIVATE_TICK = 120;
	
	/** Star light type */
	public BlockStarLightTypes type;
	
	/** Tick update count */
	int updateCount;
	
	/** Radius count */
	int radiusCount;
	
	/** Star rotation */
	public float starRotation;
	
	
	private short startUpTick;
	
	public TileEntityStarLight() {}
	
	public TileEntityStarLight(int typeId) {
		type = BlockStarLightTypes.getBlockLightType(typeId);
		startUpTick = 0;
	}
	
	@Override
	public void updateEntity() {
		
		if (worldObj.isRemote)
		{
			starRotation += 0.5f;
			if (starRotation > 360f) {
				starRotation = 0f;
			}
			if (startUpTick < ACTIVATE_TICK) {
				startUpTick++;
				if (startUpTick == ACTIVATE_TICK) {
					System.out.println("bang");
					//bang
				}
			}
			return;
		}

		if (startUpTick < ACTIVATE_TICK) {
			System.out.println("startup tick " + startUpTick);
			if (startUpTick == 1) {
				PacketHandler.networkWrapper.sendToDimension(new MessageDimensionSound((byte)0), worldObj.provider.dimensionId);
			}
			startUpTick++;
			return;
		}
		
		updateCount++;
		if (updateCount >= 4) { updateCount = 0; }
		else { return; }
		
		if (type != null && type.getTypeId() < 4) {
			updateStarLight();
		}
	}
	
	public int getRenderState() {
		if (startUpTick < WARM_UP_TICK) { return 0; }
		if (startUpTick < ACTIVATE_TICK) { return 1; }
		return 2;
	}
	
	private void updateStarLight() {
		int blocksPlaced = 0;
		
		for (int ix = -radiusCount; ix <= radiusCount; ix++) {
			for (int iy = -radiusCount; iy <= radiusCount; iy++) {
				for (int iz = -radiusCount; iz <= radiusCount; iz++) {
					
					int targetX = xCoord + ix;
					int targetY = yCoord + iy;
					int targetZ = zCoord + iz;
					if (worldObj.blockExists(targetX, targetY, targetZ)) {
						if (worldObj.isAirBlock(targetX, targetY, targetZ)) {
							if (worldObj.getBlock(targetX, targetY, targetZ) != ModBlocks.utilitieLight) {
								if (Utils.getDistance(xCoord, yCoord, zCoord, targetX, targetY, targetZ) <= radiusCount) {
									worldObj.setBlock(targetX, targetY, targetZ, ModBlocks.utilitieLight, lightBlockActiveMeta, 2);
									blocksPlaced += 1;
								}
								if (blocksPlaced > 50) {
									return;
								}
							} else {
								if (worldObj.getBlockMetadata(targetX, targetY, targetZ) == lightBlockRemoveMeta) {
									if (Utils.getDistance(xCoord, yCoord, zCoord, targetX, targetY, targetZ) <= radiusCount) {
										worldObj.setBlockMetadataWithNotify(targetX, targetY, targetZ, lightBlockActiveMeta, 2);
									}
								}
							}
						}
					}
				}
			}
		}
		
		radiusCount++;
		
		if (radiusCount > type.getLightRadius()) {
			radiusCount = 0;
			//radiusCount = type.getLightRadius();
		}
		//System.out.println("radiusCount" + radiusCount);
	}
	
	public void preRemove() {
		
		if (type == null) {
			return;
		}
		
		
		for (int ix = -type.getLightRadius(); ix <= type.getLightRadius(); ix++) {
			for (int iy = -type.getLightRadius(); iy <= type.getLightRadius(); iy++) {
				for (int iz = -type.getLightRadius(); iz <= type.getLightRadius(); iz++) {
					
					int targetX = xCoord + ix;
					int targetY = yCoord + iy;
					int targetZ = zCoord + iz;
					
					if (worldObj.getBlock(targetX, targetY, targetZ) == ModBlocks.utilitieLight) {
						worldObj.setBlockMetadataWithNotify(targetX, targetY, targetZ, lightBlockRemoveMeta, 0);
					}
					
				}
			}
		}
		
		EntityStarLightDespawner entity = new EntityStarLightDespawner(worldObj, xCoord, yCoord, zCoord, type);
		worldObj.spawnEntityInWorld(entity);
	}
	
	@Override
	public Packet getDescriptionPacket() {
	    NBTTagCompound compound = new NBTTagCompound();
	    compound.setShort(TAG_STARTUP_TICK, startUpTick);
	    return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 5, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound compound = packet.func_148857_g();
		startUpTick = compound.getShort(TAG_STARTUP_TICK);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger(TAG_TYPE, type.getTypeId());
		compound.setShort(TAG_STARTUP_TICK, startUpTick);
		super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		type = BlockStarLightTypes.getBlockLightType(compound.getInteger(TAG_TYPE));
		startUpTick = compound.getShort(TAG_STARTUP_TICK);
		super.readFromNBT(compound);
	}
	
	@Override
	public double getMaxRenderDistanceSquared() {
		return 50240D;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		AxisAlignedBB bb = INFINITE_EXTENT_AABB;
		
		return bb;
		// TODO Auto-generated method stub
		//return super.getRenderBoundingBox();
	}
	
}
