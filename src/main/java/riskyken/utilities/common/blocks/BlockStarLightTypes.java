package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import riskyken.utilities.common.tileentities.TileEntityFloodLight;
import riskyken.utilities.common.tileentities.TileEntityStarLight;

public enum BlockStarLightTypes {

	MINI_STAR(0, 8, "Mini Star", false),
	STAR(1, 16, "Star", false),
	NOVA_STAR(2, 32, "Nova Star", false),
	PULSAR_STAR(3, 64, "Pulsar Star", false);
	
	int id;
	int lightRadius;
	String name;
	boolean phased;
	
	BlockStarLightTypes(int id, int lightRadius, String name, boolean phased) {
		this.id = id;
		this.lightRadius = lightRadius;
		this.name = name;
		this.phased = phased;
	}
	
	public String getBlockName() {
		return name;
	}
	
	public int getLightRadius() {
		return lightRadius;
	}
	
	public boolean isPhased() {
		return phased;
	}
	
	public int getTypeId() {
		return id;
	}
	
    public void addHoverText(List hoverText) {
    	hoverText.add("Lights up a radius of " + lightRadius + " blocks around it.");
    }
	
    public static TileEntityStarLight makeEntity(int type) {
    	TileEntityStarLight te = new TileEntityStarLight(type);
        return te;
    }
    
    public static BlockStarLightTypes getBlockLightType (int id) {
    	return BlockStarLightTypes.values()[id];
    }
}
