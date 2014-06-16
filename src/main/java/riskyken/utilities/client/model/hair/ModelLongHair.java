package riskyken.utilities.client.model.hair;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class ModelLongHair extends ModelHairBase {

	ModelRenderer longHair;
	
	public ModelLongHair() {
		super("hair");
		
		textureWidth = 64;
		textureHeight = 32;
		
		longHair = new ModelRenderer(this, 8, 0);
		longHair.addBox(-4F, -8F, 4F, 8, 10, 1);
		longHair.setRotationPoint(0F, 0F, 0F);
		longHair.setTextureSize(64, 32);
		longHair.mirror = true;
		setRotation(longHair, 0F, 0F, 0F);
	}
	
	@Override
	public void renderHair(EntityPlayer player, int colour) {
		float mult = 0.0625F;
		
		bindPlayerTexture(player);
		
		GL11.glColor3f(1, 1, 1);
		longHair.render(mult);
	}

}
