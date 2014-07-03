package riskyken.utilities.client.gui;

import java.util.ArrayList;
import java.util.List;

import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class ModConfigGui extends GuiConfig {
	
	public ModConfigGui(GuiScreen parent) {
		super(parent, makeConfigScreens(), LibModInfo.ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
	}
	
	public static List<IConfigElement> makeConfigScreens() {
		List<IConfigElement> configs = new ArrayList<IConfigElement>();
		configs.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_RECIPE)).getChildElements());
		configs.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_POWER_CONVERSION)).getChildElements());
		configs.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_WORLD_GENERATION)).getChildElements());
		configs.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_MISC)).getChildElements());
		return configs;
	}
}
