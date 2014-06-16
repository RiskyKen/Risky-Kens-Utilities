package riskyken.utilities.common.tileentities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityDeviceTeleporter extends TileEntityUtilitiesBasePowered {
	
	private static final int POWER_TO_TELEPORT = 50000;
	private static final int POWER_MAX_STORE = 100000;
	
	private static final String TAG_IS_LINKED = "isLinked";
	private static final String TAG_COOLDOWN = "cooldown";
	private static final String TAG_DIMENSION_ID_TARGET = "dimensionIdTarget";
	private static final String TAG_X_TARGET = "xTarget";
	private static final String TAG_Y_TARGET = "yTarget";
	private static final String TAG_Z_TARGET = "zTarget";
	
	private boolean isLinked = false;
	private int cooldown;
	private int dimensionIdTarget;
	private int xTarget;
	private int yTarget;
	private int zTarget;
	
	/** How long a player has been standing on the teleporter. */
	private int playerInCount = 0;
	
	private int tickUpdateCount = 0;
	
	public TileEntityDeviceTeleporter() {
		super(POWER_TO_TELEPORT, POWER_MAX_STORE);
		items = new ItemStack[1];
	}
	
	@Override
	public void updateEntity() {
        if (worldObj.isRemote) { return; }
        
    	tickUpdateCount++;
    	if (tickUpdateCount < 5)
    		return;
    	
    	updateMetaData();
    	
    	tickUpdateCount = 0;
    	
    	if (cooldown < 1) {
            List list = null;
            list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getSensitiveAABB(xCoord, yCoord + 1, zCoord));
            if (list != null && !list.isEmpty())
            {
                Iterator iterator = list.iterator();
                while (iterator.hasNext())
                {
                    Entity entity = (Entity)iterator.next();
                    OnPlayerStanding(entity);
                }
            }
            else
            {
            	playerInCount = 0;
            }
    	}
    	if (cooldown > 0)
    	{ cooldown--; }
        
		super.updateEntity();
	}
	
	//0 no link card
	//1 invalid link card
	//2 no power
	//3 ready
	
	private void updateMetaData() {
		int oldMeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int newMeta = 0;
		
		if (items[0] != null)
		{
			if (items[0].getItem() == ModItems.linkCard && isLinked)
			{
				newMeta = 2;
				if (havePowerToWork())
				{
					newMeta = 3;
					if (cooldown < 1) {
						newMeta = 4;
					}
				}
			}
			else
			{ newMeta = 1; }
		}
		
		if (items[0] == null) { newMeta = 0; }
		
		if (newMeta != oldMeta)
		{
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, 2);
		}
	}
	
	public void setTeleportPos(ItemStack itemStack) {
		if (itemStack == null)
		{
			isLinked = false;
			return;
		}
		
		if (!itemStack.hasTagCompound())
		{
			isLinked = false;
			return;
		}
		
		int x = itemStack.getTagCompound().getInteger("xPos");
		int y = itemStack.getTagCompound().getInteger("yPos");
		int z = itemStack.getTagCompound().getInteger("zPos");
		int id = itemStack.getTagCompound().getInteger("worldId");
		
		setTeleportPos(id, x, y, z);
	}
	
	public void setTeleportPos(int worldId, int xPos, int yPos, int zPos) {
		dimensionIdTarget = worldId;
		xTarget = xPos;
		yTarget = yPos;
		zTarget = zPos;
		isLinked = true;
	}
	
	private void OnPlayerStanding(Entity entity) {
		
		if (!havePowerToWork())
			return;
		
		if (isLinked) {
			
			if (playerInCount == 0)
				worldObj.playSoundAtEntity(entity, LibModInfo.ID.toLowerCase() + ":" + "teleportfxsend", 1, 1);
			
			playerInCount++;
			
			if (playerInCount < 9)
				return;
			
			playerInCount = 0;
			
			EntityPlayer player = (EntityPlayer)entity;
			
			useWorkPower();
			
			if (player.worldObj.provider.dimensionId != dimensionIdTarget) {
				
				//Utils.transferEntityToWorld(player, idTar, (double)xTar + .5, (double)yTar, (double)zTar + .5);
				//player.setPosition((double)xTar + .5, (double)yTar, (double)zTar + .5);
				player.travelToDimension(dimensionIdTarget);
				player.setPositionAndUpdate((double)xTarget + .5, (double)yTarget, (double)zTarget + .5);
			}
			else
			{
				player.setPositionAndUpdate((double)xTarget + .5, (double)yTarget, (double)zTarget + .5);
			}

			worldObj.playSoundAtEntity(entity, LibModInfo.ID.toLowerCase() + ":" + "teleportfxrecieve", 1, 1);
			//player.addChatMessage("Warping to X:" + xTar + " Y:" + yTar + " Z:" + zTar);
			cooldown = 5;
			updateMetaData();
		}
	}
	
    protected AxisAlignedBB getSensitiveAABB(int par1, int par2, int par3) {
        float f = 0.125F;
        return AxisAlignedBB.getAABBPool().getAABB((double)((float)par1 + f), (double)par2, (double)((float)par3 + f), (double)((float)(par1 + 1) - f), (double)par2 + 0.25D, (double)((float)(par3 + 1) - f));
    }
    
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean(TAG_IS_LINKED, this.isLinked);
		compound.setInteger(TAG_COOLDOWN, this.cooldown);
		compound.setInteger(TAG_DIMENSION_ID_TARGET, this.dimensionIdTarget);
		compound.setInteger(TAG_X_TARGET, this.xTarget);
		compound.setInteger(TAG_Y_TARGET, this.yTarget);
		compound.setInteger(TAG_Z_TARGET, this.zTarget);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        this.isLinked = compound.getBoolean(TAG_IS_LINKED);
        this.cooldown = compound.getInteger(TAG_COOLDOWN);
        this.dimensionIdTarget = compound.getInteger(TAG_DIMENSION_ID_TARGET);
        this.xTarget = compound.getInteger(TAG_X_TARGET);
        this.yTarget = compound.getInteger(TAG_Y_TARGET);
        this.zTarget = compound.getInteger(TAG_Z_TARGET);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		super.setInventorySlotContents(i, itemstack);
		setTeleportPos(items[0]);
	}
	
	@Override
	public String getInventoryName() {
		return LibBlockNames.TELEPORTER;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return itemstack.getItem() == ModItems.linkCard;
	}
	
	public String getStatusText() {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		switch (meta)
		{
			case 0:
				return "No link card.";
			case 1:
				return "Invalid link card.";
			case 2:
				return "Low power.";
			case 3:
				return "Cooldown";
			case 4:
				return "Ready";
		}
		
		return "Unknown State";
	}
	
}
