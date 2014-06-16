package riskyken.utilities.common.entities;

import riskyken.utilities.common.blocks.BlockFloodLightTypes;
import riskyken.utilities.common.blocks.ModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityFloodLightDespawner extends Entity  {

	BlockFloodLightTypes type;
	ForgeDirection facing;
	int updateCount;
	int blockCount;
	
	public EntityFloodLightDespawner(World world) {
		super(world);
	}
	
	public EntityFloodLightDespawner(World world,  int x, int y, int z, BlockFloodLightTypes type, ForgeDirection facing) {
		super(world);
		this.type = type;
		this.facing = facing;
		posX = x;
		posY = y;
		posZ = z;
	}
	
	@Override
	public void onUpdate() {
		if (worldObj.isRemote) { return; }
		
		if (type == null) {
			this.setDead();
			return;
		}

		updateCount++;
		if (updateCount >= 5) { updateCount = 0; }
		else { return; }
		
		blockCount++;
		
		if (worldObj.getBlock((int)posX, (int)posY, (int)posZ) == ModBlocks.utilitieLight) {
			worldObj.setBlockToAir((int)posX, (int)posY, (int)posZ);
		}
		
		posX += facing.offsetX;
		posY += facing.offsetY;
		posZ += facing.offsetZ;
		
		if (blockCount > type.getLightRange()) {
			this.setDead();
		}
	}
	
	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		facing = ForgeDirection.getOrientation(compound.getInteger("facing"));
		type = BlockFloodLightTypes.getBlockLightType(compound.getInteger("type"));
		blockCount = compound.getInteger("blockCount");
		updateCount = compound.getInteger("updateCount");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("facing", facing.ordinal());
		compound.setInteger("type", type.ordinal());
		compound.setInteger("blockCount", blockCount);
		compound.setInteger("updateCount", updateCount);
	}

}
