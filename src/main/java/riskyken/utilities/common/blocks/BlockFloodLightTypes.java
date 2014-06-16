package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import riskyken.utilities.common.tileentities.TileEntityFloodLight;

public enum BlockFloodLightTypes {
	FLOOD_LIGHT_8(0, 8, "Very Short Flood Light", false),
	FLOOD_LIGHT_16(1, 16, "Short Flood Light", false),
	FLOOD_LIGHT_32(2, 32, "Medium Flood Light", false),
	FLOOD_LIGHT_64(3, 64, "Long Flood Light", false),
	FLOOD_LIGHT_128(4, 128, "Extra Long Flood Light", false),
	
	
	PHASED_FLOOD_LIGHT_8(5, 8, "Very Short Phased Flood Light", true),
	PHASED_FLOOD_LIGHT_16(6, 16, "Short Phased Flood Light", true),
	PHASED_FLOOD_LIGHT_32(7, 32, "Medium Phased Flood Light", true),
	PHASED_FLOOD_LIGHT_64(8, 64, "Long Phased Flood Light", true),
	PHASED_FLOOD_LIGHT_128(8, 128, "Extra Long Phased Flood Light", true);
	
	int id;
	int lightRange;
	String name;
	boolean phased;
	
	BlockFloodLightTypes(int id, int lightRange, String name, boolean phased) {
		this.id = id;
		this.lightRange = lightRange;
		this.name = name;
		this.phased = phased;
	}
	
	public String getBlockName() {
		return name;
	}
	
	public int getLightRange() {
		return lightRange;
	}
	
	public boolean isPhased() {
		return phased;
	}
	
    public static TileEntityFloodLight makeEntity(int typeId) {
    	TileEntityFloodLight te = new TileEntityFloodLight(BlockFloodLightTypes.getBlockLightType(typeId));
        return te;
    }
    
    public static BlockFloodLightTypes getBlockLightType (int id) {
    	return BlockFloodLightTypes.values()[id];
    }
    
    public static void addHoverText(ItemStack itemStack, EntityPlayer player, List hoverText) {
    	BlockFloodLightTypes lightType = getBlockLightType(itemStack.getItemDamage());
    	hoverText.add("Lights up " + lightType.lightRange + " blocks in front of it.");
    }
}
