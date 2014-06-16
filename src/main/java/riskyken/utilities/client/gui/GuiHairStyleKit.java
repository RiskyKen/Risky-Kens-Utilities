package riskyken.utilities.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.hair.HairAccessoryType;
import riskyken.utilities.common.hair.HairStyleType;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.inventory.ContainerHairStyleKit;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageButton;
import riskyken.utilities.proxies.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHairStyleKit extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/hair-styler.png");
	private EntityPlayer player;
	PlayerHairStyleData props;
	private float rotation;
	
	public GuiHairStyleKit(EntityPlayer player) {
		super(new ContainerHairStyleKit(player));
		this.player = player;
		xSize = 256;
		ySize = 166;
		props = PlayerHairStyleData.get(player);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiScrollbar(0, guiLeft + 73, guiTop + 20, 10, 136, "", false));
		buttonList.add(new GuiScrollbar(1, guiLeft + 236, guiTop + 20, 10, 136, "", false));
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int button) {
		if (button == 0) {
			PacketHandler.networkWrapper.sendToServer(new MessageButton((short)0));
		}
		super.mouseMovedOrUp(x, y, button);
		
		
		if (button==0) {
			((GuiScrollbar)buttonList.get(0)).mouseReleased(x, y);
			((GuiScrollbar)buttonList.get(1)).mouseReleased(x, y);
			//((GuiScrollbar)buttonList.get(2)).mouseReleased(x, y);
			//((GuiScrollbar)buttonList.get(3)).mouseReleased(x, y);
			//updateWingColour();
		}
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			//RiskyKensUtilities.packetPipeline.sendToServer(new PacketButton((short)button.id));
		} else {
			//updateWingColour();
		}
		
		
	}
	
	private void updateWingColour() {
		float red = (float)((GuiScrollbar)buttonList.get(1)).getValue() / 100;
		float green = (float)((GuiScrollbar)buttonList.get(2)).getValue() / 100;
		float blue = (float)((GuiScrollbar)buttonList.get(3)).getValue() / 100;
		//RiskyKensUtilities.packetPipeline.sendToServer(new PacketPlayerClothingData(player, red, green, blue));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		
		float scale = 50F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft + 128, guiTop + 70, 100);
		GL11.glScalef(-scale, scale, scale);
		
		rotation += 3.0F;
		
		if (rotation > 360F) {
			rotation -= 360F;
		}
		
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glRotatef(rotation, 0, 1, 0);
		GL11.glRotatef(20, 1, 0, 0);
		
		ClientProxy.playerHeadModel.renderHead(player, 0F);
		ClientProxy.hairRenderManager.renderHairOnPlayer(player, null);
		
		GL11.glPopMatrix();
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.xSize, LibItemNames.HAIR_STYLE_KIT);
        
        for (int i = 0; i < HairStyleType.values().length; i++) {
        	HairStyleType type = HairStyleType.values()[i];
        	String typeName = "???";
        	if (props.getHasHairStyle(type)) {
        		typeName = type.getName();
        	}
        	this.fontRendererObj.drawString(typeName, 12, 22 + 9 * i, 4210752);
        }
        
        for (int i = 0; i < HairAccessoryType.values().length; i++) {
        	HairAccessoryType type = HairAccessoryType.values()[i];
        	String typeName = "???";
        	if (props.getHasAccessory(type)) {
        		typeName = type.getName();
        	}
        	this.fontRendererObj.drawString(typeName, 175, 22 + 9 * i, 4210752);
        }
        
	}

}
