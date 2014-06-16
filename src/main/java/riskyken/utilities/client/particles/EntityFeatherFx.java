package riskyken.utilities.client.particles;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import riskyken.utilities.common.blocks.ModBlocks;

public class EntityFeatherFx extends EntityFX {

	protected EntityFeatherFx(World world, double x, double y, double z, double xV, double yV, double zV, boolean enabled) {
		super(world, x, y, z);
		//System.out.println("x:" + x + " y:" + y + " z:" + z);
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		
		//this.interpPosY = 0;
		
		setParticleIcon(ModBlocks.deviceBlock.smallFetherIcon);
		particleScale = 0.5F;
		particleMaxAge = 200;
		
		//this.rotationPitch = rand.nextFloat();
		//this.rotationYaw = 45f;
		
		particleRed = (float) xV;
		particleGreen = (float) yV;
		particleBlue = (float) zV;
		
		this.motionX = (rand.nextFloat() - 0.5F) * 0.03F;
		this.motionZ = (rand.nextFloat() - 0.5F) * 0.03F;;
		this.motionY = -0.03F;
		this.particleGravity = 0;
		
		
		//this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		this.motionY = -0.03F;
		particleAlpha = 1 + -((float)particleAge / particleMaxAge);
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
	
	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (!isDead) {
			//GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			par1Tessellator.setBrightness(15728880);
			float lastBrightnessX = OpenGlHelper.lastBrightnessX;
			float lastBrightnessY = OpenGlHelper.lastBrightnessY;
			//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
			
			super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
			GL11.glEnable(GL11.GL_LIGHTING);
			//GL11.glPopMatrix();
		}
	}
}
