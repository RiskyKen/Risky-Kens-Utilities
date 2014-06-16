package riskyken.utilities.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class ModelPlayerHead extends ModelBase{

	ModelRenderer head;
	ModelRenderer overlay;
	
	public ModelPlayerHead() {
		textureHeight = 64;
		textureHeight = 32;
		
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(64, 32);
		
		overlay = new ModelRenderer(this, 32, 0);
		//overlay = new ModelRenderer(this, 8, 0);
		overlay.addBox(-4F, -8F, -4F, 8, 8, 8);
		overlay.setRotationPoint(0F, 0F, 0F);
		overlay.setTextureSize(64, 32);
	}
	
	public void renderHead(EntityPlayer player, float rotation) {
		float mult = 0.0625F;
		
		if (player instanceof AbstractClientPlayer) {
			AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
			Minecraft.getMinecraft().getTextureManager().bindTexture(clientPlayer.getLocationSkin());
		}

		//setRotation(bun, renderer.modelBipedMain.bipedHead);
		
		GL11.glPushMatrix();
		

		
		//GL11.glRotatef(45, 1, 0, 0);
		//GL11.glRotatef(45, 0, 1, 0);
		//GL11.glColor3f(1, 1, 1);
		//GL11.glTranslated(-50, -50, -50);
		head.render(mult);
		float scale = 1.02F;
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0, 0.01f, 0);
		overlay.render(mult);
		GL11.glPopMatrix();
	}
}
