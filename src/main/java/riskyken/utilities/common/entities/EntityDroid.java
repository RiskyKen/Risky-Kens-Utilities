package riskyken.utilities.common.entities;

import riskyken.utilities.common.blocks.BlockFloodLightTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityDroid extends Entity {
	
	private float rotation;
	
	public EntityDroid(World par1World) {
		super(par1World);
		setSize(1F, 1F);
	}
	
	public EntityDroid(World world, double x, double y, double z) {
		this(world);
		posX = x;
		posY = y;
		posZ = z;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}
	
	@Override
	public boolean interactFirst(EntityPlayer par1EntityPlayer) {
		setDead();
		return true;
	}
	
	@Override
	public void onUpdate() {
		rotation = rotation + 0.05F;
		if (rotation >= Math.PI * 4) { rotation = 0F; }
		
		super.onUpdate();
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}
	
	public float getRotateAngle() {
		return rotation;
	}

}
