package riskyken.utilities.common.entities;

import cpw.mods.fml.common.registry.EntityRegistry;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.lib.LibEntityIds;


public class Entities {
	
	
	public static void init()
	{
		EntityRegistry.registerModEntity(EntityFloodLightDespawner.class, "FloodLightDespawner", LibEntityIds.FLOOD_LIGHT_DESPAWN, RiskyKensUtilities.instance, 0, 20, false);
		EntityRegistry.registerModEntity(EntityStarLightDespawner.class, "StarLightDespawner", LibEntityIds.STAR_LIGHT_DESPAWN, RiskyKensUtilities.instance, 0, 20, false);
	}
}
