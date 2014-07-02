package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class ModelMohican extends ModelHairBase {
	ModelRenderer mohican;

	public ModelMohican() {
		super("hair");
		textureWidth = 64;
		textureHeight = 32;
		
		mohican = new ModelRenderer(this, 8, 0);
		mohican.addBox(-1F, -10F, -6F, 2, 4, 12);
		mohican.setRotationPoint(0F, 0F, 0F);
		mohican.setTextureSize(64, 32);
		mohican.mirror = true;
		setRotation(mohican, 0F, 0F, 0F);
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
		mohican.render(mult);
		GL11.glPopMatrix();
	}

}
