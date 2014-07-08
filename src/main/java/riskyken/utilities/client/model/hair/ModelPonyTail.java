package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModelPonyTail extends ModelHairBase
{
	ModelRenderer base;
	ModelRenderer bobble;
	ModelRenderer tail;
  
	public ModelPonyTail()
	{
		super("hair-tail");
		textureWidth = 64;
		textureHeight = 32;
		
		base = new ModelRenderer(this, 0, 8);
		base.addBox(-1F, -5F, 4F, 2, 2, 2);
		base.setRotationPoint(0F, 0F, 0F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		
		bobble = new ModelRenderer(this, 0, 0);
		bobble.addBox(-0.5F, -4.5F, 6F, 1, 1, 1);
		bobble.setRotationPoint(0F, 0F, 0F);
		bobble.setTextureSize(64, 32);
		bobble.mirror = true;
		setRotation(bobble, 0F, 0F, 0F);
		
		tail = new ModelRenderer(this, 0, 0);
		tail.addBox(-1F, -5F, 7F, 2, 6, 2);
		tail.setRotationPoint(0F, 0F, 0F);
		tail.setTextureSize(64, 32);
		tail.mirror = true;
		setRotation(tail, 0F, 0F, 0F);
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
		base.render(mult);
		tail.render(mult);
		bindAccessoriesTexture();
		GL11.glColor3f(1, 1, 1);
		bobble.render(mult);
		GL11.glPopMatrix();
	}
}
