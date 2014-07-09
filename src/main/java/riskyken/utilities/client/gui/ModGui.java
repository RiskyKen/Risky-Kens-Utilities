package riskyken.utilities.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

@SideOnly(Side.CLIENT)
public abstract class ModGui extends GuiContainer {

	protected int screenLeft;
	protected int screenTop;
	
	protected int screenWidth;
	protected int screenHeight;
	
	protected int screenXSize;
	protected int screenYSize;
	
	public ModGui(Container container) {
		super(container);
	}
	
	protected void setGuiSize(int screenX, int screenY) {
		screenXSize = screenX;
		screenYSize = screenY;
		xSize = screenX;
		ySize = screenY;
	}
	
	@Override
	public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
		
		this.screenWidth = width;
		this.screenHeight = height;
		super.setWorldAndResolution(minecraft, width, height);
	}

	@Override
	public void initGui() {
		super.initGui();
        this.screenLeft = (this.screenWidth - this.screenXSize) / 2;
        this.screenTop = (this.screenHeight - this.screenYSize) / 2;
	}
	
}
