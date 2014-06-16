package riskyken.utilities.client.renderer;

import javax.swing.Icon;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemMagicStaff implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		//return false;
		return type == ItemRenderType.EQUIPPED;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == ItemRenderType.EQUIPPED)
		{
			
		     GL11.glPushMatrix();
		     
		     GL11.glTranslatef(-0.1F, 0.7F, -1.4F);
		     //GL11.glTranslatef(-21.2F, 0.7F, -70.4F);
		     
		     GL11.glRotatef(-78f, 0.0f, 1.0f, 0.0f);
		     GL11.glRotatef( 15f, 1.0f, 0.0f, 0.0f);
		     
		     GL11.glRotatef( -55f, 0.0f, 0.0f, 1.0f);
		     
		     float scale = 2.0f;
		     GL11.glScalef(scale, scale, scale);
		     //GL11.glRotatef(4f, 1, 10, 6);
		     
		     
		     IIcon icon = item.getItem().getIcon(item, 0);
		     
		     ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		     
		     GL11.glPopMatrix();
		     
		}
		
	}

}
