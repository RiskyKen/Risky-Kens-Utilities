package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class ModelOdango extends ModelHairBase
{
	ModelRenderer bunLeft;
	ModelRenderer bunRight;
	ModelRenderer bobbleLeft;
	ModelRenderer bobbleRight;
	ModelRenderer tailLeft;
	ModelRenderer tailRight;
    
	public ModelOdango()
	{
		super("hair");
		textureWidth = 64;
		textureHeight = 32;

		bunLeft = new ModelRenderer(this, 8, 0);
		bunLeft.addBox(3F, -9F, 3F, 2, 2, 2);
		bunLeft.setRotationPoint(0F, 0F, 0F);
		bunLeft.setTextureSize(64, 32);
		bunLeft.mirror = true;
		setRotation(bunLeft, 0F, 0F, 0F);
		
		bunRight = new ModelRenderer(this, 8, 0);
		bunRight.addBox(-5F, -9F, 3F, 2, 2, 2);
		bunRight.setRotationPoint(0F, 0F, 0F);
		bunRight.setTextureSize(64, 32);
		bunRight.mirror = true;
		setRotation(bunRight, 0F, 0F, 0F);
		
		bobbleLeft = new ModelRenderer(this, 8, 0);
		bobbleLeft.addBox(3.5F, -8.5F, 5F, 1, 1, 1);
		bobbleLeft.setRotationPoint(0F, 0F, 0F);
		bobbleLeft.setTextureSize(64, 32);
		bobbleLeft.mirror = true;
		setRotation(bobbleLeft, 0F, 0F, 0F);
		
		bobbleRight = new ModelRenderer(this, 8, 0);
		bobbleRight.addBox(-4.5F, -8.5F, 5F, 1, 1, 1);
		bobbleRight.setRotationPoint(0F, 0F, 0F);
		bobbleRight.setTextureSize(64, 32);
		bobbleRight.mirror = true;
		setRotation(bobbleRight, 0F, 0F, 0F);
		
		tailLeft = new ModelRenderer(this, 8, 0);
		tailLeft.addBox(3F, -9F, 6F, 2, 8, 2);
		tailLeft.setRotationPoint(0F, 0F, 0F);
		tailLeft.setTextureSize(64, 32);
		tailLeft.mirror = true;
		setRotation(tailLeft, 0F, 0F, 0F);
		
		tailRight = new ModelRenderer(this, 8, 0);
		tailRight.addBox(-5F, -9F, 6F, 2, 8, 2);
		tailRight.setRotationPoint(0F, 0F, 0F);
		tailRight.setTextureSize(64, 32);
		tailRight.mirror = true;
		setRotation(tailRight, 0F, 0F, 0F);
	}
	
	@Override
	public void renderHair(EntityPlayer player, int colour) {
		float mult = 0.0625F;
		
        float colourRed = (colour >> 16 & 0xff) / 255F;
        float colourGreen = (colour >> 8 & 0xff) / 255F;
        float colourBlue = (colour & 0xff) / 255F;
		
		bindPlayerTexture(player);
		
		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
	    bunLeft.render(mult);
	    bunRight.render(mult);
	    tailLeft.render(mult);
	    tailRight.render(mult);
		bindHairTexture();
		GL11.glColor3f(colourRed, colourGreen, colourBlue);
	    bobbleLeft.render(mult);
	    bobbleRight.render(mult);
		GL11.glPopMatrix();
	}
}
