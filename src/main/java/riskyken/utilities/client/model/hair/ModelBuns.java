package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class ModelBuns extends ModelHairBase
{
	ModelRenderer rightBun;
	ModelRenderer leftBun;
	
	public ModelBuns()
	{
		super("hair");
		textureWidth = 64;
		textureHeight = 32;
		
		rightBun = new ModelRenderer(this, 8, 0);
		rightBun.addBox(-5F, -9F, 2F, 3, 3, 3);
		rightBun.setRotationPoint(0F, 0F, 0F);
		rightBun.setTextureSize(64, 32);
		rightBun.mirror = true;
		setRotation(rightBun, 0F, 0F, 0F);
		
		leftBun = new ModelRenderer(this, 8, 0);
		leftBun.addBox(2F, -9F, 2F, 3, 3, 3);
		leftBun.setRotationPoint(0F, 0F, 0F);
		leftBun.setTextureSize(64, 32);
		leftBun.mirror = true;
		setRotation(leftBun, 0F, 0F, 0F);
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
		rightBun.render(mult);
		leftBun.render(mult);
		GL11.glPopMatrix();
	}
}
