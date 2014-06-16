package riskyken.utilities.utils;

import java.util.logging.Logger;

import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.common.FMLLog;

public class ModLogger {

	public static final Logger logger = Logger.getLogger(LibModInfo.NAME);
	
	public static void init() {
		//logger = Logger.getLogger(LibModInfo.NAME);
		//logger.setParent(FMLLog.getLogger());
		//logger.setParent(FMLLog.getLogger());
		logger.info("Loading " + LibModInfo.NAME + " " + LibModInfo.VERSION);
	}
}
