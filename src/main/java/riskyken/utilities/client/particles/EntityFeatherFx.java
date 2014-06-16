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
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.lib.LibModInfo;

@SideOnly(Side.CLIENT)
public class EntityFeatherFx extends EntityFX {
	
	private static final ResourceLocation whiteFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-white-feather.png");
	private static final ResourceLocation blackFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-black-feather.png");
	private static final ResourceLocation redFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/tiny-red-feather.png");
	
	private final int type;
	private final boolean isUnlit;
	
	public EntityFeatherFx(World world, double x, double y, double z, int type) {
		super(world, x, y, z);
		//System.out.println("x:" + x + " y:" + y + " z:" + z);
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		
		this.type = type;
		this.isUnlit = type != 0;
		
		particleScale = 0.5F;
		particleMaxAge = 200;
		
		this.motionX = (rand.nextFloat() - 0.5F) * 0.03F;
		this.motionZ = (rand.nextFloat() - 0.5F) * 0.03F;;
		this.motionY = -0.03F;
		this.particleGravity = 0;
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		this.motionY = -0.03F;
		particleAlpha = 1 + -((float)particleAge / particleMaxAge);
	}
	
	@Override
	public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (isDead) { return; }
		
		if (isUnlit) {
			tessellator.draw();
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
		}

		
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
		
		
        float f6 = 0;
        float f7 = 1;
        float f8 = 0;
        float f9 = 1;
        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        
        if (isUnlit) {
        	tessellator.startDrawingQuads();
        	tessellator.setBrightness(15728880);
        }

        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
        tessellator.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
        
        if (isUnlit) {
            tessellator.draw();
    		GL11.glEnable(GL11.GL_LIGHTING);
    		GL11.glPopMatrix();
    		
    		ResourceLocation particleTextures;
    		particleTextures = ReflectionHelper.getPrivateValue(EffectRenderer.class, null, "particleTextures", "field_110737_b", "b");
    		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
    		tessellator.startDrawingQuads();
        }
	}
}
