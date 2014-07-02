package riskyken.utilities.common.config;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.config.Configuration;
import riskyken.utilities.common.UpdateCheck;
import riskyken.utilities.utils.ModLogger;

public class ConfigHandler {

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
		Configuration config = new Configuration(file);
		
		config.load();
		
		ModLogger.log("Loading Config");
		
		//recipe
		saddle_recipe = config.get("recipe", "Saddle Recipe", true, "Enable saddle recipe using 5 leather and 1 iron ingot.").getBoolean(true);
		disableRecipes = config.get("recipe", "Disable Recipes", false, "Disable all mod recipes. Use if you want to manually add recipes for a mod pack.").getBoolean(false);
		
		//power conversion
		conversionRateEu = config.get("power conversion", "Conversion Rate Eu", 1.0D).getDouble(1.0D);
		conversionRateMj = config.get("power conversion", "Conversion Rate Mj", 2.52D).getDouble(2.52D);
		conversionRateRf = config.get("power conversion", "Conversion Rate Rf", 1.0D).getDouble(1.0D);
		conversionRateUe = config.get("power conversion", "Conversion Rate Ue", 1.0D).getDouble(1.0D);
		
		//world gen
		gen_dungeons = config.get("world generation", "generate dungeons", true, "Enable dungeon world generation.").getBoolean(true);
		
		//other
		hollowerBlockSearchMax = config.get("other", "Hollower Max Block Scan", 80000, "Max number of blocks the hollower can scan.").getInt(8000);
		hollowerBlockSearchPerTick = config.get("other", "Hollower Blocks Scanned Per Tick", 100, "Number of blocks the hollower scans every tick.").getInt(10);
		UpdateCheck.shouldCheckForUpdates = config.get("other", "Check for updates", true).getBoolean(true);
		
		config.save();
	}
	
	
}
