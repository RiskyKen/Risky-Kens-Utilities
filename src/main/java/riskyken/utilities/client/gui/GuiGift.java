package riskyken.utilities.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.inventory.ContainerGift;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityGift;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGift extends ModGui{

	private TileEntityGift tileEntityGift;
	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/gift.png");
	
	public GuiGift(InventoryPlayer invPlayer, TileEntityGift tileEntityGift) {
		super(new ContainerGift(invPlayer, tileEntityGift));
		this.tileEntityGift = tileEntityGift;
		setGuiSize(176, 166);
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.screenXSize, tileEntityGift.getInventoryName());
		
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.screenYSize - 96 + 2, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(this.screenLeft, this.screenTop, 0, 0, this.screenXSize, this.screenYSize);
	}
}
