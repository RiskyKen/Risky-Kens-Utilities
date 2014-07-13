package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class ModelLongHair extends ModelHairBase {

	ModelRenderer right1;
	ModelRenderer right2;
	ModelRenderer right3;
	ModelRenderer left1;
	ModelRenderer left2;
	ModelRenderer left3;
	ModelRenderer center;
	
	public ModelLongHair() {
		super("hair");
		
		textureWidth = 64;
		textureHeight = 32;
		
		right1 = new ModelRenderer(this, 0, 0);
		right1.addBox(0F, 0F, 0F, 1, 1, 1);
		right1.setRotationPoint(-4F, 0F, 3F);
		right1.setTextureSize(64, 32);
		right1.mirror = true;
		setRotation(right1, 0F, 0F, 0F);
		right2 = new ModelRenderer(this, 0, 0);
		right2.addBox(0F, 0F, 0F, 1, 2, 1);
		right2.setRotationPoint(-3F, 0F, 3F);
		right2.setTextureSize(64, 32);
		right2.mirror = true;
		setRotation(right2, 0F, 0F, 0F);
		right3 = new ModelRenderer(this, 0, 0);
		right3.addBox(0F, 0F, 0F, 1, 3, 1);
		right3.setRotationPoint(-2F, 0F, 3F);
		right3.setTextureSize(64, 32);
		right3.mirror = true;
		setRotation(right3, 0F, 0F, 0F);
		left1 = new ModelRenderer(this, 0, 0);
		left1.addBox(0F, 0F, 0F, 1, 1, 1);
		left1.setRotationPoint(3F, 0F, 3F);
		left1.setTextureSize(64, 32);
		left1.mirror = true;
		setRotation(left1, 0F, 0F, 0F);
		left2 = new ModelRenderer(this, 0, 0);
		left2.addBox(0F, 0F, 0F, 1, 2, 1);
		left2.setRotationPoint(2F, 0F, 3F);
		left2.setTextureSize(64, 32);
		left2.mirror = true;
		setRotation(left2, 0F, 0F, 0F);
		left3 = new ModelRenderer(this, 0, 0);
		left3.addBox(0F, 0F, 0F, 1, 3, 1);
		left3.setRotationPoint(1F, 0F, 3F);
		left3.setTextureSize(64, 32);
		left3.mirror = true;
		setRotation(left3, 0F, 0F, 0F);
		center = new ModelRenderer(this, 0, 0);
		center.addBox(0F, 0F, 0F, 2, 4, 1);
		center.setRotationPoint(-1F, 0F, 3F);
		center.setTextureSize(64, 32);
		center.mirror = true;
		setRotation(center, 0F, 0F, 0F);
	}
	
	@Override
	public void renderHair(EntityPlayer player, int colour) {
		float mult = 0.0625F;
        float colourRed = (colour >> 16 & 0xff) / 255F;
        float colourGreen = (colour >> 8 & 0xff) / 255F;
        float colourBlue = (colour & 0xff) / 255F;
        
        GL11.glPushMatrix();
		bindHairTexture();
		GL11.glColor3f(colourRed, colourGreen, colourBlue);
		right1.render(mult);
		right2.render(mult);
		right3.render(mult);
		left1.render(mult);
		left2.render(mult);
		left3.render(mult);
		center.render(mult);
		GL11.glPopMatrix();
	}
}
