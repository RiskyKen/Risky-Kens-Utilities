package riskyken.utilities.client.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.hair.HairStyleType;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.inventory.ContainerHairStyleKit;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.network.messages.MessageButton;
import riskyken.utilities.common.network.messages.MessageUpdatePlayerHairStyleData;
import riskyken.utilities.proxies.ClientProxy;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHairStyleKit extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/hair-styler.png");
	private EntityPlayer player;
	PlayerHairStyleData props;
	private float rotation;
	private byte activeStyle;
	
	public GuiHairStyleKit(EntityPlayer player) {
		super(new ContainerHairStyleKit(player));
		this.player = player;
		this.xSize = 256;
		this.ySize = 166;
		props = PlayerHairStyleData.get(player);
		activeStyle = props.getHairType();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiScrollbar(0, this.guiLeft + 92, this.guiTop + 20, 10, 136, "", false));
		buttonList.add(new GuiSilentButton(1, this.guiLeft + 190, this.guiTop + 136, 50, 20, "Apply"));
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int button) {
		if (button == 0) {
			//PacketHandler.networkWrapper.sendToServer(new MessageButton((short)0));
		}
		super.mouseMovedOrUp(x, y, button);
		
		
		if (button==0) {
			((GuiScrollbar)buttonList.get(0)).mouseReleased(x, y);
			//((GuiScrollbar)buttonList.get(1)).mouseReleased(x, y);
			//((GuiScrollbar)buttonList.get(2)).mouseReleased(x, y);
			//((GuiScrollbar)buttonList.get(3)).mouseReleased(x, y);
			//updateWingColour();
		}
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			PacketHandler.networkWrapper.sendToServer(new MessageButton((short)1));
			BufferedImage bufferedImage = getBufferedImageSkin((AbstractClientPlayer) player);
			
			int hairColour = getAvrageHairColour(bufferedImage);
			
			PacketHandler.networkWrapper.sendToServer(new MessageUpdatePlayerHairStyleData(2, hairColour));
			
			//RiskyKensUtilities.packetPipeline.sendToServer(new PacketButton((short)button.id));
		} else {
			//updateWingColour();
		}
		
		
	}
	
	private int getAvrageHairColour(BufferedImage bufferedImage) {
		if (bufferedImage == null) { return 0; }
		
		double redTotal = 0;
		double greenTotal = 0;
		double blueTotal = 0;
		
		for (int ix = 0; ix < 8; ix++) {
			for (int iy = 0; iy < 8; iy++) {
				int rgb = bufferedImage.getRGB(ix + 8, iy);
		        redTotal += (rgb >> 16 & 0xff) / 255F;
		        greenTotal += (rgb >> 8 & 0xff) / 255F;
		        blueTotal += (rgb & 0xff) / 255F;
			}
		}
		
		float red = (float) (redTotal / 64);
		float green = (float) (greenTotal / 64);
		float blue = (float) (blueTotal / 64);
		
		Color c = new Color(red, green, blue);
		
		System.out.println("red" + red + " green" + green + " blue" + blue);
		System.out.println("rgb" + c.getRGB());
		return c.getRGB();
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
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		
		float scale = 50F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(this.guiLeft + 198, this.guiTop + 70, 100);
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
		activeStyle = props.getHairType();
        for (int i = 0; i < HairStyleType.values().length; i++) {
        	HairStyleType type = HairStyleType.values()[i];
        	String typeName = "???";
        	if (props.getHasHairStyle(type)) {
        		typeName = type.getName();
        	}
        	
        	int textTop = 22 + 9 * i;
        	int textLeft = 12;
        	int renderColour = 4210752;
        	
        	if (i == activeStyle) {
        		renderColour = Utils.getMinecraftColor(0);
        	}
        	if (y >= textTop + this.guiTop & y <= textTop + this.guiTop + 6 & x >= textLeft + this.guiLeft - 1 & x <= textLeft + this.guiLeft + 78) {
        		renderColour = Utils.getMinecraftColor(4);
        	}
        	this.fontRendererObj.drawString(typeName, textLeft, textTop, renderColour);
        }

	}

	private BufferedImage getBufferedImageSkin(AbstractClientPlayer player) {
		BufferedImage bufferedImage = null;
		InputStream inputStream;

		if (player.func_152123_o()) {
			ThreadDownloadImageData imageData = AbstractClientPlayer.getDownloadImageSkin(player.getLocationSkin(), player.getCommandSenderName());
			bufferedImage = ReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, imageData, "bufferedImage");
		}
		return bufferedImage;
	}

}
