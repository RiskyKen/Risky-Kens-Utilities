package riskyken.utilities.client.model;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.client.particles.Particles;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.PointD;
import riskyken.utilities.utils.Trig;

@SideOnly(Side.CLIENT)
public class ModelBigWings extends ModelBiped
{
    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation[] wingsImage;
    
  public ModelBigWings()
  {
    textureWidth = 64;
    textureHeight = 64;
    

      rightWing = new ModelRenderer(this, 0, 31);
      rightWing.addBox(-11F, 2F, 0F, 17, 30, 1);
      rightWing.setRotationPoint(0F, 0F, 0F);
      rightWing.setTextureSize(64, 32);
      rightWing.mirror = true;
      setRotation(rightWing, 2.094395F, 0F, -1.396263F);
      

      leftWing = new ModelRenderer(this, 0, 0);
      leftWing.addBox(-6F, 2F, 0F, 17, 30, 1);
      leftWing.setRotationPoint(0F, 0F, 0F);
      leftWing.setTextureSize(64, 32);
      leftWing.mirror = true;
      setRotation(leftWing, 2.094395F, 0F, 1.396263F);
      
      wingsImage = new ResourceLocation[3];
      wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/big-black-wings.png");
      wingsImage[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/big-white-wings.png");
      wingsImage[2] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/shana-wings.png");
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    rightWing.render(f5);
    leftWing.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
  
	public void render(EntityPlayer player, RenderPlayer renderer, int wingId) {
		
		if (wingId >= 0 & wingId < wingsImage.length) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[wingId]);
		}
		float mult = 0.0625F;
		
		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
		
		//GL11.glRotatef(180, 1, 0, 0);
		//GL11.glRotatef(player.renderYawOffset, 0, 1, 0);
		
		float lastBrightnessX = OpenGlHelper.lastBrightnessX;
		float lastBrightnessY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		Tessellator tessellator = Tessellator.instance;
		//tessellator.setBrightness(15728880);
		
		
	    rightWing.render(mult);
	    leftWing.render(mult);
	    
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
	    
	    GL11.glEnable(GL11.GL_LIGHTING);
	    //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper., par1, par2);
	    
	    GL11.glPopMatrix();
	    
	    if (wingId == 0) { spawnParticales(player, 0.2F, 0.2F, 0.2F); }
	    if (wingId == 1) { spawnParticales(player, 1F, 1F, 1F); }
	    if (wingId == 2) { spawnParticales(player, 1F, 0.5F, 0.2F); }
	}
	
	private void spawnParticales(EntityPlayer player, float red, float green, float blue) {
		Random rnd = new Random();
		if (rnd.nextFloat() * 1000 > 980) {
			PointD offset;// = new PointD(player.posX, player.posZ);
			
			if (rnd.nextFloat() > 0.5f) {
				offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f  + rnd.nextFloat() * 1.5f, player.renderYawOffset + 56);
			} else {
				offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.5f, player.renderYawOffset + 121);	
			}
			
			float yOffset = 0f;
			
			double parX = offset.x;
			double parY = player.posY - 0.4D;
			double parZ = offset.y;
			Particles.FEATHER.spawnParticle(player.worldObj, parX, parY, parZ, red, green, blue, true);
		}
	}

}
