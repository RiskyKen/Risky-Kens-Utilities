package riskyken.utilities.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import riskyken.utilities.common.tileentities.TileEntityFloodLight;
import riskyken.utilities.common.tileentities.TileEntityGift;
import riskyken.utilities.common.tileentities.TileEntityLeafCatcher;
import riskyken.utilities.common.tileentities.TileEntityMusicSequencer;
import riskyken.utilities.common.tileentities.TileEntityOreProcessor;
import riskyken.utilities.common.tileentities.TileEntityStarLight;
import riskyken.utilities.common.tileentities.TileEntityStarMultiBlock;
import riskyken.utilities.common.tileentities.TileEntityTransporterBeacon;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static BlockDevice deviceBlock;
	public static Block deviceHollower;
	public static BlockTeleporter deviceTeleporter;
	public static BlockTransporterBeacon transporterBeacon;
	public static BlockFloodLight floodLight;
	public static Block utilitieLight;
	public static Block utilitieSolid;
	public static Block musicSequencer;
	public static Block leafCatcher;
	public static Block oreProcessor;
	public static BlockStarLight starLight;
	public static Block starMultiBlock;
	public static Block phasedLightSponge;
	//public static Block houseBuilder;
	//public static Block farm;
	public static BlockGift gift;
	public static Block paperCube;
	public static Block meteor;
	
	public static void init()
	{
		deviceBlock = new BlockDevice();
		deviceHollower = new BlockHollower();
		deviceTeleporter = new BlockTeleporter();
		transporterBeacon = new BlockTransporterBeacon();
		floodLight = new BlockFloodLight();
		utilitieLight = new BlockUtilitieLight();
		utilitieSolid = new BlockUtilitieSolid();
		musicSequencer = new BlockMusicSequencer();
		leafCatcher = new BlockLeafCatcher();
		oreProcessor = new BlockOreProcessor();
		starLight = new BlockStarLight();
		starMultiBlock = new BlockStarMultiBlock();
		phasedLightSponge = new BlockPhasedLightSponge();
		//farm = new BlockFarm("farm");
		//houseBuilder = new BlockHouseBuilder("houseBuilder");
		gift = new BlockGift();
		paperCube = new BlockPaperCube();
		meteor = new BlockMeteor();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileEntityDeviceHollower.class, LibBlockNames.HOLLOWER);
		registerTileEntity(TileEntityDeviceTeleporter.class, LibBlockNames.TELEPORTER);
		registerTileEntity(TileEntityTransporterBeacon.class, LibBlockNames.TRANSPORTER_BEACON);
		registerTileEntity(TileEntityGift.class, LibBlockNames.GIFT);
		registerTileEntity(TileEntityMusicSequencer.class, LibBlockNames.MUSIC_SEQUENCER);
		registerTileEntity(TileEntityFloodLight.class, LibBlockNames.FLOOD_LIGHT);
		registerTileEntity(TileEntityLeafCatcher.class, LibBlockNames.LEAF_CATCHER);
		registerTileEntity(TileEntityOreProcessor.class, LibBlockNames.ORE_PROCESSOR);
		//registerTileEntity(TileEntityHouseBuilder.class, LibBlockNames.FARM);
		//registerTileEntity(TileEntityHouseBuilder.class, LibBlockNames.HOUSE_BUILDER);
		registerTileEntity(TileEntityStarLight.class, LibBlockNames.STAR_LIGHT);
		registerTileEntity(TileEntityStarMultiBlock.class, LibBlockNames.STAR_MULTI_BLOCK);
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, "te." + id);
	}
}
