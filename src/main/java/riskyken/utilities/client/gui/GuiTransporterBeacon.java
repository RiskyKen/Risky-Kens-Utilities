package riskyken.utilities.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.inventory.ContainerTransporterBeacon;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityTransporterBeacon;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTransporterBeacon extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/transporter.png");
	private TileEntityTransporterBeacon transporterBeacon;
	
	@Override
	public void initGui() {
		super.initGui();
		//buttonList.clear();
		//buttonList.add(new GuiButton(0, guiLeft + 84, guiTop + 20, 70, 20, "Link"));
	}
	
	public GuiTransporterBeacon(InventoryPlayer invPlayer, TileEntityTransporterBeacon transporterBeacon) {
		super(new ContainerTransporterBeacon(invPlayer, transporterBeacon));
		this.transporterBeacon = transporterBeacon;
		xSize = 176;
		ySize = 166;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0)
		{
			//PacketHandler.sendButtonPacket((byte)0);
		}
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.xSize, transporterBeacon.getInventoryName());
        
        String upgrades = "Upgrades";
        int rangeInt = transporterBeacon.getMaxRange();
        String range = "";
        if (rangeInt != -1)
        {
        	range = "Range: " + transporterBeacon.getMaxRange() + " blocks";
        }
        else
        {
        	range = "Range: Infinite";
        }
        
        this.fontRendererObj.drawString(upgrades, 8, 36, 4210752);
        this.fontRendererObj.drawString(range, 8, 22, 4210752);
        //this.fontRendererObj.drawString("Target Block", 26, 30, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
        if (x - k >= 160 & x - k <= 168) {
        	if (y - l >= 7 & y - l <= 78) {
        		drawOverlayText(String.format("%.0f",this.transporterBeacon.powerLevel) + "/" + String.format("%.0f",this.transporterBeacon.getMaxPowerLevel()), x - k, y - l);
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
        i1 = Utils.getScaledValue(72, (int)this.transporterBeacon.powerLevel, (int)transporterBeacon.getMaxPowerLevel());
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
