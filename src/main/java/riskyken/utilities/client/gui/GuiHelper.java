package riskyken.utilities.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiHelper {
	public static void renderLocalizedGuiName(FontRenderer fontRenderer, int xSize, String name) {
		String unlocalizedName = "container.inventory." + name + ".name";
		String localizedName = StatCollector.translateToLocal(unlocalizedName);
		String renderText = unlocalizedName;
		if (!unlocalizedName.equals(localizedName)){
			renderText = localizedName;
		}
		fontRenderer.drawString(renderText, xSize / 2 - fontRenderer.getStringWidth(renderText) / 2, 6, 4210752);
	}
}
