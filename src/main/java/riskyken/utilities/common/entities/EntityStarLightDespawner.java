package riskyken.utilities.common.entities;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraftforge.common.util.ForgeDirection;
import riskyken.utilities.common.blocks.BlockStarLightTypes;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.utils.Utils;

public class EntityStarLightDespawner extends Entity {
	
	BlockStarLightTypes type;
	int updateCount;
	int radiusCount;
	
	public EntityStarLightDespawner(World world) {
		super(world);
	}
	
	public EntityStarLightDespawner(World world,  int x, int y, int z, BlockStarLightTypes type) {
		super(world);
		this.type = type;
		posX = x;
		posY = y;
		posZ = z;
		radiusCount = type.getLightRadius();
	}
	
	@Override
	public void onUpdate() {
		if (worldObj.isRemote) { return; }
		
		if (type == null) {
			this.setDead();
			return;
		}
		
		updateCount++;
		if (updateCount >= 4) { updateCount = 0; }
		else { return; }
		
		int blocksRemoved = 0;
		
		//System.out.println("star despawn tick");
		
		for (int ix = -radiusCount; ix <= radiusCount; ix++) {
			for (int iy = -radiusCount; iy <= radiusCount; iy++) {
				for (int iz = -radiusCount; iz <= radiusCount; iz++) {
					
					int targetX = (int) (posX + ix);
					int targetY = (int) (posY + iy);
					int targetZ = (int) (posZ + iz);
					
					if (worldObj.blockExists(targetX, targetY, targetZ)) {
						if (worldObj.isAirBlock(targetX, targetY, targetZ)) {
							if (worldObj.getBlock(targetX, targetY, targetZ) == ModBlocks.utilitieLight) {
								if (worldObj.getBlockMetadata(targetX, targetY, targetZ) == 14) {
									if (Utils.getDistance((int)posX, (int)posY, (int)posZ, targetX, targetY, targetZ) >= radiusCount) {
										worldObj.setBlockToAir(targetX, targetY, targetZ);
										blocksRemoved += 1;
									}
									if (blocksRemoved > 50) {
										return;
									}
								}
							}
						}
					}
				}
			}
		}
		
		radiusCount--;
		
		if (radiusCount < 0) {
			System.out.println("star despawn killed");
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		type = BlockStarLightTypes.getBlockLightType(compound.getInteger("type"));
		updateCount = compound.getInteger("updateCount");
		radiusCount = compound.getInteger("radiusCount");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("type", type.ordinal());
		compound.setInteger("updateCount", updateCount);
		compound.setInteger("radiusCount", radiusCount);
	}
}
