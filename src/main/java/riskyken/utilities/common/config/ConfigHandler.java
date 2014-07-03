package riskyken.utilities.common.config;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import riskyken.utilities.common.UpdateCheck;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.ModLogger;

public class ConfigHandler {

	public static String CATEGORY_RECIPE = "recipe";
	public static String CATEGORY_POWER_CONVERSION = "power conversion";
	public static String CATEGORY_WORLD_GENERATION = "world generation";
	public static String CATEGORY_MISC = "misc";
	
	public static Configuration config;
	
	public static boolean saddle_recipe;
	public static boolean disableRecipes;
	
	public static boolean gen_dungeons;
	
	//1 coal 4000 eu
	//1 coal 1584 mj direct
	//1 coal 1593 mj pipe
	
	public static double conversionRateEu;
	public static double conversionRateMj;
	public static double conversionRateRf;
	public static double conversionRateUe;
	
	public static int hollowerBlockSearchMax;
	public static int hollowerBlockSearchPerTick;
	
	public static void init(File file){
		if (config == null) {
			config = new Configuration(file);
			loadConfigFile();
		}
	}
	
	public static void loadConfigFile() {
		config.load();
		
		ModLogger.log("Loading Config");
		
		//recipe
		saddle_recipe = config.get(CATEGORY_RECIPE, "Saddle Recipe", true, "Enable saddle recipe using 5 leather and 1 iron ingot.").getBoolean(true);
		disableRecipes = config.get(CATEGORY_RECIPE, "Disable Recipes", false, "Disable all mod recipes. Use if you want to manually add recipes for a mod pack.").getBoolean(false);
		
		//power conversion
		conversionRateEu = config.get(CATEGORY_POWER_CONVERSION, "Conversion Rate Eu", 1.0D).getDouble(1.0D);
		conversionRateMj = config.get(CATEGORY_POWER_CONVERSION, "Conversion Rate Mj", 2.52D).getDouble(2.52D);
		conversionRateRf = config.get(CATEGORY_POWER_CONVERSION, "Conversion Rate Rf", 1.0D).getDouble(1.0D);
		conversionRateUe = config.get(CATEGORY_POWER_CONVERSION, "Conversion Rate Ue", 1.0D).getDouble(1.0D);
		
		//world gen
		gen_dungeons = config.get(CATEGORY_WORLD_GENERATION, "Generate dungeons", true, "Enable dungeon world generation.").getBoolean(true);
		
		//misc
		hollowerBlockSearchMax = config.get(CATEGORY_MISC, "Hollower Max Block Scan", 80000, "Max number of blocks the hollower can scan.").getInt(8000);
		hollowerBlockSearchPerTick = config.get(CATEGORY_MISC, "Hollower Blocks Scanned Per Tick", 100, "Number of blocks the hollower scans every tick.").getInt(10);
		UpdateCheck.shouldCheckForUpdates = config.get(CATEGORY_MISC, "Check for updates", true).getBoolean(true);
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}
