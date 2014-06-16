package riskyken.utilities.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRectangle {

	int x;
	int y;
	int width;
	int height;
	int sourceX;
	int sourceY;
	
	public GuiRectangle(int x, int y, int sourceX, int sourceY, int width, int height) {
		this.x = x;
		this.y = y;
		this.sourceX = sourceX;
		this.sourceY = sourceY;
		this.width = width;
		this.height = height;
	}
	
	public void DrawRectangle(GuiContainer gui, int guiLeft, int guiTop, int mouseX, int mouseY, boolean active, int collum) {
		if (!active) {
			gui.drawTexturedModalRect(guiLeft + x, guiTop + y, sourceX, sourceY, width, height);
		}
		else
		{
			gui.drawTexturedModalRect(guiLeft + x, guiTop + y, sourceX, sourceY + 8, width, height);
		}
		
		if (collum == x) {
			gui.drawTexturedModalRect(guiLeft + x, guiTop + y, sourceX + 16, sourceY + 8, width, height);
		}
		
		if (IsInRectangle(mouseX, mouseY, guiLeft, guiTop)) {
			gui.drawTexturedModalRect(guiLeft + x, guiTop + y, sourceX + 8, sourceY + 8, width, height);
		}

	}
	
	public boolean IsInRectangle(int xPoint, int yPoint, int guiLeft, int guiTop) {
		if (xPoint >= guiLeft + x && xPoint <= guiLeft + x + width &&
				yPoint >= guiTop + y && yPoint <= guiTop + y + height) {
			return true;
		}
		return false;
	}
}
