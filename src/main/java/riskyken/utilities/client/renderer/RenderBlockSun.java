package riskyken.utilities.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityStarLight;

public class RenderBlockSun extends TileEntitySpecialRenderer {
	
	public RenderBlockSun() {
		
	}
	
	//private static final ResourceLocation modelImage = new ResourceLocation(ModInformation.ID.toLowerCase(), "textures/models/star.png");
	private static final ResourceLocation innerImage = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/blocks/star.png");
	private static final ResourceLocation outerImage = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/blocks/star_outer.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tickTime) {
		TileEntityStarLight te = (TileEntityStarLight)tileEntity;
		
		/*
		Minecraft.getMinecraft().getTextureManager().bindTexture(modelImage);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glScalef(3f, 3f, 3f);
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glRotatef(te.starRotation, 0, 1, 1);
		ClientProxy.modelStar.render(tileEntity);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		*/
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(outerImage);
		drawBillboard(tileEntity, x, y, z, te.starRotation);
		//Minecraft.getMinecraft().getTextureManager().bindTexture(innerImage);
		//drawBillboard(tileEntity, x, y, z, -te.starRotation);
		
	}
	
	private void drawBillboard(TileEntity tileEntity, double x, double y, double z, float rotation) {
		TileEntityStarLight te = (TileEntityStarLight)tileEntity;
		RenderManager renderManager = RenderManager.instance;
		Tessellator tessellator = Tessellator.instance;
		
		float scale = (float)te.type.getLightRadius() / 16 / 2;		
		
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		
        //GL11.glRotatef((float) -RenderManager.instance.viewerPosY, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		
		GL11.glRotatef(rotation, 0, 0, 1);
		GL11.glScalef(scale, scale, scale);
		
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(1, 1, 1);
        tessellator.setBrightness(15728880);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        
		tessellator.addVertexWithUV(-1, -1, 0, 0, 0);
		tessellator.addVertexWithUV(-1, 1, 0, 1, 0);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(1, -1, 0, 1, 0);
		tessellator.draw();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}
