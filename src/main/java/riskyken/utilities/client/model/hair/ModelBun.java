package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class ModelBun extends ModelHairBase
{
	ModelRenderer bun;
	
	public ModelBun()
	{
		super("hair");
		textureWidth = 64;
		textureHeight = 32;
		
		bun = new ModelRenderer(this, 8, 0);
		bun.addBox(-1.5F, -6F, 4F, 3, 3, 2);
		bun.setRotationPoint(0F, 0F, 0F);
		bun.setTextureSize(64, 32);
		bun.mirror = true;
		setRotation(bun, 0F, 0F, 0F);
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
		bun.render(mult);
		GL11.glPopMatrix();
	}
}
