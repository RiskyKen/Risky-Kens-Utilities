package riskyken.utilities.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemGift implements IItemRenderer  {

	private static final RenderBlocks renderer = new RenderBlocks();
	//private static final ItemRenderer renderer = new ItemRenderer(par1Minecraft)
	//Minecraft
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		// TODO Auto-generated method stub
		Tessellator tessellator = Tessellator.instance;
		
		//System.out.println("render gift");
		
		Block block = Block.getBlockFromItem(item.getItem());
		
        //block.setBlockBoundsForItemRender();
        
        GL11.glPushMatrix();
        //GL11.glScalef(2, 2, 2);
        GL11.glTranslatef(0F, -0.5F, 0F);
		GL11.glRotatef(180, 0, 1, 0);
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(1, 1, 1);
		tessellator.addVertexWithUV(0, 0, 0, 0, 0);
		tessellator.addVertexWithUV(0, 1, 0, 1, 0);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(1, 0, 0, 1, 0);
		tessellator.draw();
		
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glTranslatef(-0.5F, 0.0F, 0F);
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(1, 1, 1);
		tessellator.addVertexWithUV(0, 0, 0, 0, 0);
		tessellator.addVertexWithUV(0, 1, 0, 1, 0);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(1, 0, 0, 1, 0);
		tessellator.draw();
		
		
        GL11.glPopMatrix();
        /*
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, 0));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, 0));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, 0));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, 0));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, 0));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, 0));
        tessellator.draw();
        */
        
        /*
        tessellator.startDrawingQuads();
        //tessellator.setNormal(0.0F, -1F, 0.0F);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)0, (double)0);
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)1, (double)0);
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)1, (double)1);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)0, (double)1);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        //tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)0, (double)0);
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)1, (double)0);
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)1, (double)1);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)0, (double)1);
        tessellator.draw();
         */
		
		
	}

}
