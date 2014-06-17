package riskyken.utilities.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.PointD;
import riskyken.utilities.utils.Trig;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityStarDust extends EntityFX {

	private static final ResourceLocation whiteFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-white-feather.png");
	
	public EntityStarDust(World world, double x, double y, double z, double starX, double starY, double starZ, float size) {
		super(world, x, y, z);
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.particleScale = 4F;
		this.particleMaxAge = (int) (50 + rand.nextFloat() * 20);
		this.particleGravity = 0;
		this.noClip = true;
		
		//double rotationYaw = (float) Trig.GetAngle(x, z, starX, starZ);
		
		this.rotationYaw = (float) Trig.GetAngle(x, z, starX, starZ);
		this.rotationPitch = (float) Trig.GetAngle(0, y, 0, starY);
		
		double distance1 = Utils.getDistance((int)x, (int)0, (int)z, (int)starX, (int)0, (int)starZ);
		double distance2 = Utils.getDistance((int)0, (int)y, (int)0, (int)0, (int)starY, (int)0);
		
		PointD point1;
		PointD point2;
		
		point1 = Trig.moveTo(new PointD(x, z), (float) (distance1 / this.particleMaxAge), (float)rotationYaw);
		point2 = Trig.moveTo(new PointD(0, y), (float) (distance2 / this.particleMaxAge), (float)rotationPitch);
		
		this.motionX = point1.x - x;
		this.motionY = point2.y - y;
		this.motionZ = point1.y - z;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		//particleAlpha = 1 + -((float)particleAge / particleMaxAge);
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (isDead) { return; }
		
		tessellator.draw();
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		Minecraft.getMinecraft().renderEngine.bindTexture(whiteFeather);
		
        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        
        tessellator.startDrawingQuads();
        tessellator.setBrightness(15728880);
        drawBillboard(f11 - par3 * f10 - par6 * f10, f12 - par4 * f10, f13 - par5 * f10 - par7 * f10, 0);
        
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		
		ResourceLocation particleTextures;
		particleTextures = ReflectionHelper.getPrivateValue(EffectRenderer.class, null, "particleTextures", "field_110737_b", "b");
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
		tessellator.startDrawingQuads();
	}
	
	private void drawBillboard(double x, double y, double z, float rotation) {
		RenderManager renderManager = RenderManager.instance;
		Tessellator tessellator = Tessellator.instance;
		
		float scale = 0.4F;		
		
        GL11.glTranslatef((float)x, (float)y, (float)z);
        
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        
        GL11.glTranslatef((float)0.20, (float)0.20, (float)0.20);
        GL11.glScalef(scale, scale, scale);
        
		//GL11.glRotatef(180, 0, 0, 1);
		
		//GL11.glTranslatef((float)-0.1F, (float)-0.1F, (float)-0.1F);
		//GL11.glRotatef(rotation, 0, 0, 1);
		//GL11.glTranslatef((float)0.1F, (float)0.1F, (float)0.1F);
		
		tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tessellator.addVertexWithUV(0, 0, 0, 0, 0);
		tessellator.addVertexWithUV(0, 1, 0, 0, 1);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(1, 0, 0, 1, 0);
	}
}
