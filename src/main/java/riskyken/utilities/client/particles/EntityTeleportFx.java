package riskyken.utilities.client.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.common.blocks.ModBlocks;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityTeleportFx extends EntityFX  {

	public EntityTeleportFx(World world, double x, double y, double z, double xV, double yV, double zV) {
		super(world, x, y, z, xV, yV, zV);
		setParticleIcon(ModBlocks.deviceTeleporter.particleIcon);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		particleAlpha = 1 + -((float)particleAge / particleMaxAge);
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
}
