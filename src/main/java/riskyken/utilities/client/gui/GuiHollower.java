package riskyken.utilities.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.inventory.ContainerHollower;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageButton;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHollower extends GuiContainer {

	private TileEntityDeviceHollower deviceHollower;
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiButton(0, guiLeft + 36, guiTop + 28, 50, 20, "Scan"));
		buttonList.add(new GuiButton(1, guiLeft + 96, guiTop + 28, 50, 20, "Start"));
		buttonList.add(new GuiCheckBox(2, guiLeft + 7, guiTop + 64, "check", deviceHollower.isIgnoreMeta()));
		buttonList.add(new GuiCheckBox(3, guiLeft + 96, guiTop + 64, "check", deviceHollower.isLeaveWalls()));
	}
	
	public GuiHollower(InventoryPlayer invPlayer, TileEntityDeviceHollower deviceHollower) {
		super(new ContainerHollower(invPlayer, deviceHollower));
		this.deviceHollower = deviceHollower;
		xSize = 176;
		ySize = 182;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		PacketHandler.networkWrapper.sendToServer(new MessageButton((short)button.id));
	}
	
	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/hollower.png");

	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.xSize, deviceHollower.getInventoryName());
        String status = "Status: " + deviceHollower.getStatusText();
        this.fontRendererObj.drawString(status, this.xSize / 2 - this.fontRendererObj.getStringWidth(status) / 2, 16, 4210752);
        //this.fontRendererObj.drawString("Target Block", 8, 26, 4210752);
        this.fontRendererObj.drawString("Ignore Meta", 9, 54, 4210752);
      	this.fontRendererObj.drawString("Leave Walls", 98, 54, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        if (x - k >= 160 & x - k <= 168) {
        	if (y - l >= 7 & y - l <= 78) {
        		drawOverlayText(String.format("%.0f",this.deviceHollower.powerLevel) + "/" + String.format("%.0f",this.deviceHollower.getMaxPowerLevel()), x - k, y - l);
        	}
        }
        
        ((GuiCheckBox)buttonList.get(2)).setChecked(deviceHollower.isIgnoreMeta());
        ((GuiCheckBox)buttonList.get(3)).setChecked(deviceHollower.isLeaveWalls());
    }
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        int i1 = 0;
        i1 = Utils.getScaledValue(72, (int)this.deviceHollower.powerLevel, (int)deviceHollower.getMaxPowerLevel());
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
