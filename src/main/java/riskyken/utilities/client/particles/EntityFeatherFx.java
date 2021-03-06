package riskyken.utilities.client.particles;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityStarLight;
import scala.util.Random;

@SideOnly(Side.CLIENT)
public class EntityFeatherFx extends EntityFX {
	
	private static final ResourceLocation whiteFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-white-feather.png");
	private static final ResourceLocation blackFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-black-feather.png");
	private static final ResourceLocation redFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-red-feather.png");
	
	private final int type;
	private final boolean isUnlit;
	private float rotationSpeed;
	
	public EntityFeatherFx(World world, double x, double y, double z, int type) {
		super(world, x, y, z);
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		
		this.type = type;
		this.isUnlit = type != 0;
		
		particleScale = 0.5F;
		particleMaxAge = 400;
		
		this.motionX = (rand.nextFloat() - 0.5F) * 0.01F;
		this.motionZ = (rand.nextFloat() - 0.5F) * 0.01F;;
		this.motionY = -0.03F;
		this.rotationSpeed = 2.0F + rand.nextFloat();
		this.rotationPitch = rand.nextFloat() * 20 - 10;
		if (rand.nextFloat() >= 0.5F) {
			this.rotationSpeed = -this.rotationSpeed;
		}
		this.particleGravity = 0;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (this.isCollidedVertically) {
			this.rotationSpeed = 0F;
		}
		
		this.motionY = -0.01F;
		this.rotationPitch += rotationSpeed;
		if (this.rotationPitch > 360F) {
			this.rotationPitch -= 360F;
		}
		particleAlpha = 1 + -((float)particleAge / particleMaxAge);
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (isDead) { return; }
		
		tessellator.draw();
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		
		switch (type) {
		case 0:
			Minecraft.getMinecraft().renderEngine.bindTexture(blackFeather);
			break;
		case 1:
			Minecraft.getMinecraft().renderEngine.bindTexture(whiteFeather);
			break;
		case 2:
			Minecraft.getMinecraft().renderEngine.bindTexture(redFeather);
			break;
		}
		
        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        
        tessellator.startDrawingQuads();
        if (isUnlit) {
        	tessellator.setBrightness(15728880);
        }
        
        drawBillboard(f11 - par3 * f10 - par6 * f10, f12 - par4 * f10, f13 - par5 * f10 - par7 * f10, rotationPitch);
        
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
		
		float scale = 0.05F;		
		
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180, 0, 0, 1);
		
		GL11.glTranslatef((float)-0.1F, (float)-0.1F, (float)-0.1F);
		GL11.glRotatef(rotation, 0, 0, 1);
		GL11.glTranslatef((float)0.1F, (float)0.1F, (float)0.1F);
		GL11.glScalef(scale, scale, scale);
		
		tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tessellator.addVertexWithUV(-1, -1, 0, 0, 0);
		tessellator.addVertexWithUV(-1, 1, 0, 0, 1);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(1, -1, 0, 1, 0);
	}
}
