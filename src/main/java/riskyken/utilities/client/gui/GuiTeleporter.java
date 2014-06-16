package riskyken.utilities.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import riskyken.utilities.common.inventory.ContainerTeleporter;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import riskyken.utilities.utils.Utils;

@SideOnly(Side.CLIENT)
public class GuiTeleporter extends GuiContainer {

	private TileEntityDeviceTeleporter deviceTeleporter;
	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/teleporter.png");
	
	public GuiTeleporter(InventoryPlayer invPlayer, TileEntityDeviceTeleporter deviceTeleporter) {
		super(new ContainerTeleporter(invPlayer, deviceTeleporter));
		this.deviceTeleporter = deviceTeleporter;
		xSize = 176;
		ySize = 166;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.xSize, deviceTeleporter.getInventoryName());
        String status = "Status: " + deviceTeleporter.getStatusText();
        this.fontRendererObj.drawString(status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2, 16, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        
        //System.out.println("draw power level " + this.deviceTeleporter.powerLevel);
        if (x - k >= 160 & x - k <= 168) {
        	if (y - l >= 7 & y - l <= 78) {
        		drawOverlayText(String.format("%.0f",this.deviceTeleporter.powerLevel) + "/" + String.format("%.0f",this.deviceTeleporter.getMaxPowerLevel()), x - k, y - l);
        	}
        }
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        int i1 = 0;
        i1 = Utils.getScaledValue(72, (int)this.deviceTeleporter.powerLevel, (int)deviceTeleporter.getMaxPowerLevel());
        this.drawTexturedModalRect(k + 160, l + 7 + (72 - i1), 176,(72 - i1), 8, 72 + i1);
	}
	
    private void drawOverlayText(String text, int x, int y)
    {
        List list = new ArrayList();
        
        list.add(text);

        for (int k = 0; k < list.size(); ++k)
        {
            if (k == 0)
            {
                //list.set(k, "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + (String)list.get(k));
            }
            else
            {
                list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
            }
        }
        drawHoveringText(list, x, y, fontRendererObj);
        //drawHoveringTextOverlay(list, x, y, (fontRendererObj));
    }
}
