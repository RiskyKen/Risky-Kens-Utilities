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
	}
	
	public static void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(deviceHollower, 1, 0), new Object[] {"sps","hia","srs",
			's',Blocks.stone,
			'i',Blocks.iron_block,
			'r',Items.redstone,
			'h',Items.golden_shovel,
			'a',Items.golden_axe,
			'p',Items.golden_pickaxe});
		
		GameRegistry.addRecipe(new ItemStack(deviceTeleporter, 1, 0), new Object[] {"sys","eie","srs",
			's',Blocks.stone,
			'r',Items.redstone,
			'e',Items.ender_pearl,
			'i',Blocks.iron_block,
			'y',new ItemStack(Items.dye, 1, 11)});
		
		GameRegistry.addRecipe(new ItemStack(transporterBeacon, 1, 0), new Object[] {"sbs","eie","srs",
			's',Blocks.stone,
			'r',Items.redstone,
			'e',Items.ender_pearl,
			'i',Blocks.iron_block,
			'b',new ItemStack(Items.dye, 1, 4)});
		
		//depth gauge
		GameRegistry.addRecipe(new ItemStack(deviceBlock, 1, 0), new Object[] {"sss","sis","sks",
			's',Blocks.stone,
			'i',Blocks.iron_block,
			'k',Items.string});
		
		//water drain
		GameRegistry.addRecipe(new ItemStack(deviceBlock, 1, 1), new Object[] {"srs","rir","sbs",
			's',Blocks.stone,
			'r',Items.redstone,
			'i',Blocks.iron_block,
			'b',Items.bucket});
		
		//flood light
		addShapedRecipe(new ItemStack(floodLight, 1, 0), new Object[] {"wpw","igi","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'g',Blocks.torch,
			'p',Blocks.glass_pane});
		
		addShapedRecipe(new ItemStack(floodLight, 1, 1), new Object[] {"wpw","igi","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'g',Blocks.coal_block,
			'p',Blocks.glass_pane});
		
		addShapedRecipe(new ItemStack(floodLight, 1, 2), new Object[] {"wpw","igi","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'g',Items.glowstone_dust,
			'p',Blocks.glass_pane});
		
		addShapedRecipe(new ItemStack(floodLight, 1, 3), new Object[] {"wpw","igi","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'g',Blocks.glowstone,
			'p',Blocks.glass_pane});
		
		addShapedRecipe(new ItemStack(floodLight, 1, 4), new Object[] {"wpw","igi","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'g',Blocks.redstone_lamp,
			'p',Blocks.glass_pane});
		
		//Flood light upgrades
		for (int i = 0; i < 5; i++) {
			if (i != 0) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 0), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.torch, 1)});
			}

			if (i != 1) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 1), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.coal_block, 1)});
			}
			
			if (i != 2) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 2), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Items.glowstone_dust, 1)});
			}
			
			if (i != 3) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 3), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.glowstone, 1)});
			}
			
			if (i != 4) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 4), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.redstone_lamp, 1)});
			}
		}
		
		for (int i = 5; i < 10; i++) {
			if (i != 5) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 5), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.torch, 1)});
			}

			if (i != 6) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 6), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.coal_block, 1)});
			}
			
			if (i != 7) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 7), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Items.glowstone_dust, 1)});
			}
			
			if (i != 8) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 8), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.glowstone, 1)});
			}
			
			if (i != 9) {
				addShapelessRecipe(new ItemStack(floodLight, 1, 9), new Object[]
						{new ItemStack(floodLight, 1, i), new ItemStack(Blocks.redstone_lamp, 1)});
			}
		}
		
		//Flood light phased upgrades/down grades
		for (int i = 0; i < 5; i++) {
			addShapedRecipe(new ItemStack(floodLight, 1, i + 5), new Object[] {" p ","plp"," p ",
				'l',new ItemStack(floodLight, 1, i),
				'p',Items.ender_pearl});
			
			addShapedRecipe(new ItemStack(floodLight, 1, i), new Object[] {" r ","rlr"," r ",
				'l',new ItemStack(floodLight, 1, i + 5),
				'r',Items.redstone});
		}
		
		//Phased Light Sponge
		for (int i = 0; i < 16; i++) {
			addShapedRecipe(new ItemStack(phasedLightSponge, 1), new Object[] {" p ","pcp"," p ",
				'c',new ItemStack(paperCube, 1, i),
				'p',Items.ender_pearl});
		}
		
		//Paper cube colours
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (i != j) {
					addShapelessRecipe(new ItemStack(paperCube, 1, i), new Object[]
						{new ItemStack(paperCube, 1, j), Utils.getMinecraftColorOreName(i)});
				}
			}
		}
		
		//Leaf Catcher
		addShapedRecipe(new ItemStack(leafCatcher, 1, 0), new Object[] {"w w","ici","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'c',Blocks.chest});
		
		//Gift
		for (int i = 0; i < 16; i++) {
			GameRegistry.addRecipe(new ItemStack(gift, 1, 0), new Object[] {" r ","rpr"," r ",
				'r',new ItemStack(ModItems.craftingMats,1,0),
				'p',new ItemStack(paperCube, 1, i)});
		}
		
		//Music Sequencer
		GameRegistry.addRecipe(new ItemStack(musicSequencer, 1, 0), new Object[] {"dnd","rsr","ini",
			'd',Items.diamond,
			'n',Items.gold_ingot,
			'r',Blocks.redstone_block,
			's',new ItemStack(ModItems.craftingMats,1,1),
			'i',Items.iron_ingot});
		
		//star shell 1
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 6), new Object[] {"ccc","cmc","ccc",
			'c',Blocks.coal_block,
			'm',new ItemStack(meteor, 1, 0)});
		
		//star shell 2
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 7), new Object[] {"ccc","cmc","ccc",
			'c',Blocks.coal_block,
			'm',new ItemStack(meteor, 1, 1)});
		
		//star igniter
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 4), new Object[] {"scs","cmc","scs",
			'c',Blocks.coal_block,
			's',new ItemStack(meteor, 1, 0),
			'm',new ItemStack(meteor, 1, 1)});
		
		//star t1
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 0), new Object[] {"scs","cgc","scs",
			'c',Blocks.coal_block,
			's',new ItemStack(starMultiBlock, 1, 7),
			'g',Blocks.glowstone});
		
		//star t2
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 1), new Object[] {"scs","cgc","scs",
			'c',new ItemStack(starMultiBlock, 1, 7),
			's',new ItemStack(starMultiBlock, 1, 6),
			'g',new ItemStack(starMultiBlock, 1, 0)});
		
		//star t3
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 2), new Object[] {"sns","ncn","sns",
			'c',new ItemStack(starMultiBlock, 1, 1),
			's',new ItemStack(starMultiBlock, 1, 6),
			'n',new ItemStack(starMultiBlock, 1, 0)});
		
		//star t4
		GameRegistry.addRecipe(new ItemStack(starMultiBlock, 1, 3), new Object[] {"sns","ncn","sns",
			'c',new ItemStack(starMultiBlock, 1, 2),
			's',new ItemStack(starMultiBlock, 1, 6),
			'n',Items.nether_star});
	}
	
	private static void addShapelessRecipe(ItemStack result, Object[] recipe) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(result, recipe));
	}
	
	private static void addShapedRecipe(ItemStack result, Object[] recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(result, recipe));
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
		GameRegistry.registerTileEntity(tileEntityClass, "te." + id);
	}
}
