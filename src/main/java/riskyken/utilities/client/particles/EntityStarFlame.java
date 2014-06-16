package riskyken.utilities.client.particles;

import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.utils.PointD;
import riskyken.utilities.utils.Trig;
import riskyken.utilities.utils.Utils;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityStarFlame extends EntityFX {

	protected EntityStarFlame(World world, double x, double y, double z, double starX, double starY, double starZ) {
		super(world, x, y, z);
		//System.out.println("x:" + x + " y:" + y + " z:" + z);
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.particleScale = 4F;
		this.particleMaxAge = 25;
		this.particleGravity = 0;
		this.noClip = true;
		
		double angleToStar1 = Trig.GetAngle(x, z, starX, starZ);
		//double angleToStar2 = Trig.GetAngle(0, y, 0, starY);
		
		double distance = Utils.getDistance((int)x, (int)0, (int)z, (int)starX, (int)0, (int)starZ);
		
		//System.out.println("distance:" + distance);
		
		PointD point1;
		PointD point2;
		
		point1 = Trig.moveTo(new PointD(x, z), 0.2F, rand.nextFloat() * 360);
		//point2 = Trig.moveTo(new PointD(0, y), (float) (distance / this.particleMaxAge), (float)angleToStar2);
		
		this.motionX = point1.x - x;
		//this.motionY = point2.y - y;
		this.motionZ = point1.y - z;
		
		setParticleIcon(ModBlocks.deviceTeleporter.particleIcon);
		
		this.particleRed = 1;
		this.particleGreen = 0.7F;
		this.particleBlue = 0;
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
