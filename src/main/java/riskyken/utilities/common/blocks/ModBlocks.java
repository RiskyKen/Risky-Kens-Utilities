package riskyken.utilities.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import riskyken.utilities.common.tileentities.TileEntityFloodLight;
import riskyken.utilities.common.tileentities.TileEntityGift;
import riskyken.utilities.common.tileentities.TileEntityLeafCatcher;
import riskyken.utilities.common.tileentities.TileEntityStarMultiBlock;
import riskyken.utilities.common.tileentities.TileEntityTransporterBeacon;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static BlockDevice deviceBlock;
	public static Block deviceHollower;
	public static BlockTeleporter deviceTeleporter;
	public static BlockTransporterBeacon transporterBeacon;
	public static BlockFloodLight floodLight;
	public static Block utilitieLight;
	public static Block utilitieSolid;
	public static Block leafCatcher;
	public static Block phasedLightSponge;
	public static BlockGift gift;
	public static Block paperCube;
	public static Block meteor;
	
	public static BlockStarLight starLight;
	public static Block starMultiBlock;
	//public static Block musicSequencer;
	//public static Block oreProcessor;
	
	//public static Block houseBuilder;
	//public static Block farm;
	
	public static void init()
	{
		deviceBlock = new BlockDevice();
		deviceHollower = new BlockHollower();
		deviceTeleporter = new BlockTeleporter();
		transporterBeacon = new BlockTransporterBeacon();
		floodLight = new BlockFloodLight();
		utilitieLight = new BlockUtilitieLight();
		utilitieSolid = new BlockUtilitieSolid();
		leafCatcher = new BlockLeafCatcher();
		phasedLightSponge = new BlockPhasedLightSponge();
		gift = new BlockGift();
		paperCube = new BlockPaperCube();
		meteor = new BlockMeteor();
		//starLight = new BlockStarLight();
		starMultiBlock = new BlockStarMultiBlock();
		//musicSequencer = new BlockMusicSequencer();
		//oreProcessor = new BlockOreProcessor();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityDeviceHollower.class, LibBlockNames.HOLLOWER);
		registerTileEntity(TileEntityDeviceTeleporter.class, LibBlockNames.TELEPORTER);
		registerTileEntity(TileEntityTransporterBeacon.class, LibBlockNames.TRANSPORTER_BEACON);
		registerTileEntity(TileEntityGift.class, LibBlockNames.GIFT);
		registerTileEntity(TileEntityFloodLight.class, LibBlockNames.FLOOD_LIGHT);
		registerTileEntity(TileEntityLeafCatcher.class, LibBlockNames.LEAF_CATCHER);
		//registerTileEntity(TileEntityStarLight.class, LibBlockNames.STAR_LIGHT);
		registerTileEntity(TileEntityStarMultiBlock.class, LibBlockNames.STAR_MULTI_BLOCK);
		//registerTileEntity(TileEntityMusicSequencer.class, LibBlockNames.MUSIC_SEQUENCER);
		//registerTileEntity(TileEntityOreProcessor.class, LibBlockNames.ORE_PROCESSOR);
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, "te." + id);
	}
}
