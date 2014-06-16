package riskyken.utilities.client.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.common.blocks.ModBlocks;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityTeleporterFx extends EntityFX {

	public EntityTeleporterFx(World world, double x, double y, double z, boolean enabled) {
		super(world, x, y, z);
		
		setParticleIcon(ModBlocks.deviceTeleporter.particleIcon);
		particleScale = 0.5F;
		particleMaxAge = 50;
		
		this.motionX = 0;
		this.motionZ = 0;
		this.motionY = 0.06F;
		
		if (enabled) {
			particleRed = rand.nextFloat();
		} else {
			particleGreen = rand.nextFloat();
			particleBlue = rand.nextFloat();
		}
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
