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
		mohican.addBox(-0.5F, -10F, -6F, 1, 4, 12);
		mohican.setRotationPoint(0F, 0F, 0F);
		mohican.setTextureSize(64, 32);
		mohican.mirror = true;
		setRotation(mohican, 0F, 0F, 0F);
	}
	
	@Override
	public void renderHair(EntityPlayer player, int colour) {
		float mult = 0.0625F;
		bindPlayerTexture(player);
		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		mohican.render(mult);
		GL11.glPopMatrix();
	}

}
