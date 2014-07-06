package riskyken.utilities.common.hair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessagePlayerHairStyleData;
import riskyken.utilities.utils.Utils;

public class PlayerHairStyleData implements IExtendedEntityProperties {
	
	public final static String EXT_PROP_NAME = "PlayerClothingData";
	public static final int HAIR_ID_WATCHER = 20;
	public static final int HAIR_COLOUR_WATCHER = 21;
	public static final int ACCESSORY_ID_WATCHER = 22;
	public static final int ACCESSORY_COLOUR_1_WATCHER = 23;
	
	private final EntityPlayer player;

	private int hairStyleUnlockFlags;
	private int hairAccessoriesUnlockFlags;
	private int hairAccessoryColourUnlockFlags;
	
	public PlayerHairStyleData(EntityPlayer player) {
		this.player = player;
		
		hairStyleUnlockFlags = 3;
		hairAccessoriesUnlockFlags = 63;
		hairAccessoryColourUnlockFlags = 1;
		
		this.player.getDataWatcher().addObject(HAIR_ID_WATCHER, (byte)0);
		this.player.getDataWatcher().addObject(HAIR_COLOUR_WATCHER, Utils.getMinecraftColor(0));
		this.player.getDataWatcher().addObject(ACCESSORY_ID_WATCHER, (byte)0);
		this.player.getDataWatcher().addObject(ACCESSORY_COLOUR_1_WATCHER, Utils.getMinecraftColor(0));
	}
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PlayerHairStyleData.EXT_PROP_NAME, new PlayerHairStyleData(player));
	}
	
	public static final PlayerHairStyleData get(EntityPlayer player)
	{
		return (PlayerHairStyleData) player.getExtendedProperties(EXT_PROP_NAME);
	}

	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setByte("hairTypeId", player.getDataWatcher().getWatchableObjectByte(HAIR_ID_WATCHER));
		properties.setInteger("hairColour", player.getDataWatcher().getWatchableObjectInt(HAIR_COLOUR_WATCHER));
		properties.setInteger("accessoryColour1", player.getDataWatcher().getWatchableObjectInt(ACCESSORY_COLOUR_1_WATCHER));
		properties.setInteger("hairStyleUnlockFlags", this.hairStyleUnlockFlags);
		properties.setInteger("hairAccessoriesUnlockFlags", this.hairAccessoriesUnlockFlags);
		properties.setInteger("hairAccessoryColourUnlockFlags", this.hairAccessoryColourUnlockFlags);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.player.getDataWatcher().updateObject(HAIR_ID_WATCHER, properties.getByte("hairTypeId"));
		this.player.getDataWatcher().updateObject(HAIR_COLOUR_WATCHER, properties.getInteger("hairColour"));
		this.player.getDataWatcher().updateObject(ACCESSORY_COLOUR_1_WATCHER, properties.getInteger("accessoryColour1"));
		this.hairStyleUnlockFlags = properties.getInteger("hairStyleUnlockFlags");
		this.hairAccessoriesUnlockFlags = properties.getInteger("hairAccessoriesUnlockFlags");
		this.hairAccessoryColourUnlockFlags = properties.getInteger("hairAccessoryColourUnlockFlags");
	}

	@Override
	public void init(Entity entity, World world) {
	}

	
	public void toggleHair() {
		byte hair = this.player.getDataWatcher().getWatchableObjectByte(HAIR_ID_WATCHER);
		
		hair++;
		
		if (hair >= HairStyleType.values().length) {
			hair = 0;
		}

		this.player.getDataWatcher().updateObject(HAIR_ID_WATCHER, hair);
	}
	
	
	public boolean getHasHair() {
		int hairType = this.player.getDataWatcher().getWatchableObjectByte(HAIR_ID_WATCHER);
		return hairType != 0;
	}
	
	public void setHairType(byte type) {
		this.player.getDataWatcher().updateObject(HAIR_ID_WATCHER, type);
	}
	
	public byte getHairType() {
		return this.player.getDataWatcher().getWatchableObjectByte(HAIR_ID_WATCHER);
	}
	
	public int getHairColour() {
		return this.player.getDataWatcher().getWatchableObjectInt(HAIR_COLOUR_WATCHER);
	}
	
	public void setHairColour(int value) {
		this.player.getDataWatcher().updateObject(HAIR_COLOUR_WATCHER, value);
	}
	
	public int getAccessoryColour() {
		return this.player.getDataWatcher().getWatchableObjectInt(ACCESSORY_COLOUR_1_WATCHER);
	}
	
	public void setAccessoryColour(int value) {
		this.player.getDataWatcher().updateObject(ACCESSORY_COLOUR_1_WATCHER, value);
	}
	
	public boolean getHasHairStyle(HairStyleType type) {
	
		if ((type.getFlagId() & hairStyleUnlockFlags) == type.getFlagId()) {
			return true;
		}
		return false;
	}
	
	public boolean getHasAccessory(HairAccessoryType type) {
		
		if ((type.getFlagId() & hairAccessoriesUnlockFlags) == type.getFlagId()) {
			return true;
		}
		return false;
	}
	
	public boolean setHasHairStyle(HairStyleType type) {
		if ((type.getFlagId() & hairStyleUnlockFlags) == type.getFlagId()) {
			return false;
		}
		hairStyleUnlockFlags += type.getFlagId();
		sync();
		return true;
	}
	
	public boolean setHasAccessory(HairAccessoryType type) {
		if ((type.getFlagId() & hairAccessoriesUnlockFlags) == type.getFlagId()) {
			return false;
		}
		hairAccessoriesUnlockFlags += type.getFlagId();
		sync();
		return true;
	}
	
	public int getHairStyleUnlockFlags() {
		return hairStyleUnlockFlags;
	}
	
	public int getHairAccessoriesUnlockFlags() {
		return hairAccessoriesUnlockFlags;
	}
	
	public int getHairAccessoryColourUnlockFlags() {
		return hairAccessoryColourUnlockFlags;
	}
	
	public void setHairStyleUnlockFlags(int hairStyleUnlockFlags) {
		this.hairStyleUnlockFlags = hairStyleUnlockFlags;
	}
	
	public void setHairAccessoriesUnlockFlags(int hairAccessoriesUnlockFlags) {
		this.hairAccessoriesUnlockFlags = hairAccessoriesUnlockFlags;
	}
	
	public void setHairAccessoryColourUnlockFlags(int hairAccessoryColourUnlockFlags) {
		this.hairAccessoryColourUnlockFlags = hairAccessoryColourUnlockFlags;
	}
	
	public final void sync() {
		PacketHandler.networkWrapper.sendTo(new MessagePlayerHairStyleData(player), (EntityPlayerMP) player);
	}
}
