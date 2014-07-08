package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class ModelTwinTails extends ModelHairBase
{
	ModelRenderer baseRight;
	ModelRenderer baseLeft;
	ModelRenderer bobbleRight;
	ModelRenderer bobbleLeft;
	ModelRenderer tailRight;
	ModelRenderer tailLeft;

	public ModelTwinTails()
	{
		super("hair-tail");
		textureWidth = 64;
		textureHeight = 32;
		
		baseRight = new ModelRenderer(this, 0, 8);
		baseRight.addBox(-4F, -5F, 4F, 2, 2, 2);
		baseRight.setRotationPoint(0F, 0F, 0F);
		baseRight.setTextureSize(64, 32);
		baseRight.mirror = true;
		setRotation(baseRight, 0F, 0F, 0F);
		
		baseLeft = new ModelRenderer(this, 0, 8);
		baseLeft.addBox(2F, -5F, 4F, 2, 2, 2);
		baseLeft.setRotationPoint(0F, 0F, 0F);
		baseLeft.setTextureSize(64, 32);
		baseLeft.mirror = true;
		setRotation(baseLeft, 0F, 0F, 0F);
		
		bobbleRight = new ModelRenderer(this, 0, 0);
		bobbleRight.addBox(-3.5F, -4.5F, 6F, 1, 1, 1);
		bobbleRight.setRotationPoint(0F, 0F, 0F);
		bobbleRight.setTextureSize(64, 32);
		bobbleRight.mirror = true;
		setRotation(bobbleRight, 0F, 0F, 0F);
		
		bobbleLeft = new ModelRenderer(this, 0, 0);
		bobbleLeft.addBox(2.5F, -4.5F, 6F, 1, 1, 1);
		bobbleLeft.setRotationPoint(0F, 0F, 0F);
		bobbleLeft.setTextureSize(64, 32);
		bobbleLeft.mirror = true;
		setRotation(bobbleLeft, 0F, 0F, 0F);
		
		tailRight = new ModelRenderer(this, 0, 0);
		tailRight.addBox(-4F, -5F, 7F, 2, 6, 2);
		tailRight.setRotationPoint(0F, 0F, 0F);
		tailRight.setTextureSize(64, 32);
		tailRight.mirror = true;
		setRotation(tailRight, 0F, 0F, 0F);
		
		tailLeft = new ModelRenderer(this, 0, 0);
		tailLeft.addBox(2F, -5F, 7F, 2, 6, 2);
		tailLeft.setRotationPoint(0F, 0F, 0F);
		tailLeft.setTextureSize(64, 32);
		tailLeft.mirror = true;
		setRotation(tailLeft, 0F, 0F, 0F);
	}

	@Override
	public void renderHair(EntityPlayer player, int colour) {
		float mult = 0.0625F;
        float colourRed = (colour >> 16 & 0xff) / 255F;
        float colourGreen = (colour >> 8 & 0xff) / 255F;
        float colourBlue = (colour & 0xff) / 255F;
		
        bindHairTexture();
		
		GL11.glPushMatrix();
		GL11.glColor3f(colourRed, colourGreen, colourBlue);
	    baseRight.render(mult);
	    baseLeft.render(mult);
	    tailRight.render(mult);
	    tailLeft.render(mult);
	    bindAccessoriesTexture();
		GL11.glColor3f(1, 1, 1);
	    bobbleRight.render(mult);
	    bobbleLeft.render(mult);
		GL11.glPopMatrix();
	}
}
