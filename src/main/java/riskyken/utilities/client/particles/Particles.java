package riskyken.utilities.client.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public enum Particles {	
	TELEPORTER_BLOCK,
	TELEPORT,
	FEATHER,
	STAR_DUST,
	STAR_FLAME;
	
	public void spawnParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ, boolean enabled) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
			int particleSetting = mc.gameSettings.particleSetting;
			
			if (particleSetting == 2 || (particleSetting == 1 && world.rand.nextInt(3) == 0)) {
				return;
			}
			
			double distanceX = mc.renderViewEntity.posX - x;
			double distanceY = mc.renderViewEntity.posY - y;
			double distanceZ = mc.renderViewEntity.posZ - z;
			
			double maxDistance = 16;
			if (distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ > maxDistance * maxDistance) {
				return;
			}
			
			EntityFX particleEffect = null;
			switch(this) {
			case TELEPORTER_BLOCK:
				particleEffect = new EntityTeleporterFx(world, x, y, z, motionX, motionY, motionZ, enabled);
				break;
			case TELEPORT:
				
				break;
			case FEATHER:
				particleEffect = new EntityFeatherFx(world, x, y, z, motionX, motionY, motionZ, enabled);
				break;
			case STAR_DUST:
				particleEffect = new EntityStarDust(world, x, y, z, motionX, motionY, motionZ);
				break;
			case STAR_FLAME:
				//System.out.println("spawn");
				particleEffect = new EntityStarFlame(world, x, y, z, motionX, motionY, motionZ);
				break;
			default:
				break;
			}
			
			if (particleEffect != null) {
				Minecraft.getMinecraft().effectRenderer.addEffect(particleEffect);
			}
			
			
		}
	}
	
}