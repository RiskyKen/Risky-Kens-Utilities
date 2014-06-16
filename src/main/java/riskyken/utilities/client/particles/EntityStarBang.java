package riskyken.utilities.client.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityStarBang extends EntityFX {

	protected EntityStarBang(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

}
