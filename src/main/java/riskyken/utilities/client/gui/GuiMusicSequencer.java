package riskyken.utilities.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.InstrumentType;
import riskyken.utilities.common.inventory.ContainerMusicSequencer;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageButton;
import riskyken.utilities.common.tileentities.TileEntityMusicSequencer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMusicSequencer extends ModGui {

	private TileEntityMusicSequencer musicSequencer;
	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/music-sequencer.png");
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiScrollbar(0, this.screenLeft + 20, this.screenTop + 19, 216, 10, "pie", true));
		buttonList.add(new GuiScrollbar(0, this.screenLeft + 9, this.screenTop + 30, 10, 110, "pie", false));
	}
	
	public GuiMusicSequencer(InventoryPlayer invPlayer, TileEntityMusicSequencer musicSequencer) {
		super(new ContainerMusicSequencer(invPlayer, musicSequencer));
		this.musicSequencer = musicSequencer;
		setGuiSize(256, 239);
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        int k = (this.screenWidth - this.screenXSize) / 2;
        int l = (this.screenHeight - this.screenYSize) / 2;
        
        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.screenXSize, musicSequencer.getInventoryName());
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.screenYSize - 96 + 2, 4210752);
        
        InstrumentType instrumentType = musicSequencer.instrumentType;
        
		int yOffset = ((GuiScrollbar)buttonList.get(1)).sliderValue;
        
        for (int i = 0; i < instrumentType.getChannelCount(); i++) {
        	this.fontRendererObj.drawString(instrumentType.getChannelName(i), 22, -yOffset + 32 + 12 * i, 4210752);
        }
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(this.screenLeft, this.screenTop, 0, 0, this.screenXSize, this.screenYSize);
		
		GuiRectangle[] guiNodes = new GuiRectangle[144];
		boolean[] musicNodes = musicSequencer.getNodesData();
		
		int xOffset = ((GuiScrollbar)buttonList.get(0)).sliderValue;
		int yOffset = ((GuiScrollbar)buttonList.get(1)).sliderValue;
		
		for (int ix = 0; ix < 16; ix++) {
			for (int iy = 0; iy < 9; iy++) {
				int recSrcLeft = 0;
				int recSrcTop = 239;
				int recLeft = -xOffset + 56 + ix * 12;
				int recTop = -yOffset + 31 + iy * 12;
				guiNodes[ix + (iy * 16)] = new GuiRectangle(recLeft, recTop, recSrcLeft, recSrcTop, 8, 8);
			}
		}
		
		
		for (int i = 0; i < guiNodes.length; i++) {
			//beats[i].DrawRectangle(this, guiLeft, guiTop, x, y, musicSequencer.beats[i], 20 + musicSequencer.collumCount * 12);
			guiNodes[i].DrawRectangle(this, this.screenLeft, this.screenTop, x, y, musicNodes[i], 0);
		}
		
		for (int ix = 0; ix < 3; ix++) {
			for (int iy = 0; iy < 6; iy++) {
				//drawTexturedModalRect(guiLeft + 75 + ix * 48, guiTop + 40 + iy * 20, 8, 239 - 100, 2, 8);
			}
		}
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int par3) {
		super.mouseMovedOrUp(x, y, par3);
		if (par3==0) {
			((GuiScrollbar)buttonList.get(0)).mouseReleased(x, y);
			((GuiScrollbar)buttonList.get(1)).mouseReleased(x, y);
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		
		GuiRectangle[] guiNodes = new GuiRectangle[144];
		//boolean[] musicNodes = musicSequencer.getNodesData();
		
		int xOffset = ((GuiScrollbar)buttonList.get(0)).sliderValue;
		int yOffset = ((GuiScrollbar)buttonList.get(1)).sliderValue;
		
		for (int ix = 0; ix < 16; ix++) {
			for (int iy = 0; iy < 9; iy++) {
				int recSrcLeft = 0;
				int recSrcTop = 239;
				int recLeft = -xOffset + 56 + ix * 12;
				int recTop = -yOffset + 31 + iy * 12;
				guiNodes[ix + (iy * 16)] = new GuiRectangle(recLeft, recTop, recSrcLeft, recSrcTop, 8, 8);
			}
		}
		
		
		for (int i = 0; i < guiNodes.length; i++) {
			if (button == 0 && guiNodes[i].IsInRectangle(x, y, this.screenLeft, this.screenTop)) {
				PacketHandler.networkWrapper.sendToServer(new MessageButton((short)i));
				break;
			}
		}
		
	}
}
