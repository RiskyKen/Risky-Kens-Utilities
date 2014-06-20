package riskyken.utilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import riskyken.utilities.common.GuiHandler;
import riskyken.utilities.common.ModTickHandler;
import riskyken.utilities.common.UpdateCheck;
import riskyken.utilities.common.ModEventHandler;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.creativetab.CreativeTabRiskyKensUtilities;
import riskyken.utilities.common.entities.Entities;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.network.PacketHandler;
import riskyken.utilities.common.world.GenerationHandler;
import riskyken.utilities.proxies.CommonProxy;
import riskyken.utilities.utils.ModLogger;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibModInfo.ID, name = LibModInfo.NAME, version = LibModInfo.VERSION)
//@NetworkMod(channels = {ModInformation.CHANNEL}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class RiskyKensUtilities {

	@Mod.Instance(LibModInfo.ID)
	public static RiskyKensUtilities instance;
	
	@SidedProxy(clientSide = "riskyken.utilities.proxies.ClientProxy", serverSide = "riskyken.utilities.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabRiskyKensUtilities tabRiskyKensUtilities = new CreativeTabRiskyKensUtilities(CreativeTabs.getNextID(),LibModInfo.ID);
	
	@Mod.EventHandler
	public void perInit(FMLPreInitializationEvent event) {
		ModLogger.init();
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		
		UpdateCheck.checkForUpdates();
		
		ModItems.init();
		ModBlocks.init();
		
		proxy.init();
		proxy.initRenderers();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event){
		if (!ConfigHandler.disableRecipes) {
			ModItems.registerRecipes();
			ModBlocks.registerRecipes();
		}
		
		ModItems.registerDungeonLoot();
		
		ModBlocks.registerTileEntities();
		Entities.init();
		
		new GenerationHandler();
		new GuiHandler();
		
	    PacketHandler.init();
	    proxy.postInit();
	    
	    MinecraftForge.EVENT_BUS.register(new ModEventHandler());
	    FMLCommonHandler.instance().bus().register(new ModTickHandler());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
