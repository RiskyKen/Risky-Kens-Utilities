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
		head.addBox(-4F, -8F, -4F, 8, 8, 8, 0F);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(64, 32);
		
		overlay = new ModelRenderer(this, 32, 0);
		overlay.addBox(-4F, -8F, -4F, 8, 8, 8, 0.5F);
		overlay.setRotationPoint(0F, 0F, 0F);
		overlay.setTextureSize(64, 32);
	}
	
	public void renderHead(EntityPlayer player, float rotation) {
		float mult = 0.0625F;
		
		if (player instanceof AbstractClientPlayer) {
			AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
			Minecraft.getMinecraft().getTextureManager().bindTexture(clientPlayer.getLocationSkin());
		}
		
		head.render(mult);
		overlay.render(mult);
	}
}
