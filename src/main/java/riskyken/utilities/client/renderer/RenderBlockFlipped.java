package riskyken.utilities.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBlockFlipped implements ISimpleBlockRenderingHandler {

	private int id;
	
	public RenderBlockFlipped() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		block.setBlockBoundsForItemRender();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, metadata));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, metadata));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, metadata));
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
		int lightValue = block.getMixedBrightnessForBlock(world, x, y, z);
		//tessellator.setBrightness(lightValue);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		//tessellator.setBrightness(983055);

		block.setBlockBounds(0, 0, 0, 1, 1, 1);
		renderer.setRenderBoundsFromBlock(block);
		


        renderer.renderFaceYNeg(block, x, y, z, block.getIcon(world, x, y, z, 0));
        renderer.renderFaceYPos(block, x, y, z, block.getIcon(world, x, y, z, 1));
        
        renderer.renderFaceZNeg(block, x, y, z, block.getIcon(world, x, y, z, 2));
        renderer.renderFaceZPos(block, x, y, z, block.getIcon(world, x, y, z, 3));
        
        renderer.renderFaceXNeg(block, x, y, z, block.getIcon(world, x, y, z, 4));
        renderer.renderFaceXPos(block, x, y, z, block.getIcon(world, x, y, z, 5));
        
        
        
        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        renderer.setRenderBoundsFromBlock(block);
        
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}

}
