package riskyken.utilities.client.model.hair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModelHairBase extends ModelBase {
	
	protected final ResourceLocation hairImage;
	
	public ModelHairBase(String imageName) {
		hairImage = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/hair/" + imageName + ".png");
	}
	
	protected void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	protected void setRotation(ModelRenderer targetModel, ModelRenderer sourceModel)
	{
		targetModel.rotateAngleX = sourceModel.rotateAngleX;
		targetModel.rotateAngleY = sourceModel.rotateAngleY;
		targetModel.rotateAngleZ = sourceModel.rotateAngleZ;
	}
	
	protected void bindPlayerTexture(EntityPlayer player) {
		if (player instanceof AbstractClientPlayer) {
			AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
			Minecraft.getMinecraft().getTextureManager().bindTexture(clientPlayer.getLocationSkin());
		}
	}
	
	protected void bindHairTexture() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(hairImage);
	}
	
	public void renderHair(EntityPlayer player, int colour) {
		
	}
	
}
