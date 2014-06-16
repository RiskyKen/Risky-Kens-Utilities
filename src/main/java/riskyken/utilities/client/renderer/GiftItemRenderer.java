package riskyken.utilities.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class GiftItemRenderer implements IItemRenderer {

	private static RenderItem renderItem = new RenderItem();
	private static RenderBlocks renderBlock = new RenderBlocks();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return false;
		//return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		helper = ItemRendererHelper.BLOCK_3D;
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		renderGiftItem(type, item, data);
	}
	
	public void renderGiftItem(ItemRenderType type, ItemStack item, Object... data) {
        Tessellator tessellator = Tessellator.instance;
        //tessellator.
        //renderer.
        Block block = Block.getBlockFromItem(item.getItem());
        
        int rawColors = 0;
        float blockColorRed = (rawColors >> 16 & 0xff) / 255F;
        float blockColorGreen = (rawColors >> 8 & 0xff) / 255F;
        float blockColorBlue = (rawColors & 0xff) / 255F;
        //System.out.println("render");
        GL11.glColor3f(blockColorRed, blockColorGreen, blockColorBlue);
        
        
        block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderBlock.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderBlock.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, 0));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderBlock.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderBlock.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderBlock.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderBlock.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, 0));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

}
