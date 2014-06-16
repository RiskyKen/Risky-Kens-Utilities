package riskyken.utilities.common.entities;

import cpw.mods.fml.common.registry.EntityRegistry;
import riskyken.utilities.RiskyKensUtilities;


public class Entities {
	
	
	public static void init()
	{
		EntityRegistry.registerModEntity(EntityFloodLightDespawner.class, "FloodLightDespawner", 0, RiskyKensUtilities.instance, 0, 20, false);
		EntityRegistry.registerModEntity(EntityStarLightDespawner.class, "StarLightDespawner", 1, RiskyKensUtilities.instance, 0, 20, false);
	}
}
