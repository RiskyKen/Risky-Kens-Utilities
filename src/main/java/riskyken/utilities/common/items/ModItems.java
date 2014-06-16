package riskyken.utilities.common.items;

import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.hair.HairStyleType;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.asm.FMLSanityChecker;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.FMLInjectionData;

public class ModItems {

	public static Item hissingStick;
	public static Item linkCard;
	public static Item transportDevice;
	public static Item upgrades;
	public static Item craftingMats;
	public static Item droid;
	public static Item cassetteTape;
	public static Item raisingHeart;
	public static Item wings;
	public static Item hairStyleKit;
	public static Item hairStyleUnlock;
	
	public static void init()
	{
		hissingStick = new ItemHissingStick();
		linkCard = new ItemLinkCard();
		transportDevice = new ItemTransportDevice();
		upgrades = new ItemUpgrade();
		craftingMats = new ItemCraftingMats();
		cassetteTape = new ItemCassetteTape();
		raisingHeart = new ItemRaisingHeart();
		wings = new ItemArmorWings(4, 1);
		hairStyleKit = new ItemHairStyleKit();
		hairStyleUnlock = new ItemHairStyleUnlock();
	}
	
	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hissingStick,1,0), new Object[]
				{" h","s ",
				'h',new ItemStack(Items.skull,1,4),
				's',"stickWood"}));

		
		if (ConfigHandler.saddle_recipe) {
			GameRegistry.addRecipe(new ItemStack(Items.saddle,1,0), new Object[] {"lll","lil",
				'i',Items.iron_ingot,
				'l',Items.leather});
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrades, 1, 0), new Object[]
				{"rrr","rpr","rsr",
				'r',Items.redstone,
				'p',Items.potato,
				's',"stickWood"}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrades, 1, 1), new Object[]
				{" u ","uiu"," u ",
				'u',new ItemStack(upgrades, 1, 0),
				'i',Items.iron_ingot}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrades, 1, 2), new Object[]
				{" u ","uiu"," u ",
				'u',new ItemStack(upgrades, 1, 1),
				'i',Items.gold_ingot}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrades, 1, 3), new Object[]
				{"uuu","uiu","uuu",
				'u',new ItemStack(upgrades, 1, 2),
				'i',Blocks.diamond_block}));
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transportDevice, 1, 0), new Object[]
				{"isi","iei"," i ",
				'i',Items.iron_ingot,
				'e',Items.ender_pearl,
				's',"stickWood"}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(linkCard, 1, 0), new Object[]
				{Items.paper,
				"dyeYellow",
				"dyeBlack"}));
		
		GameRegistry.addShapelessRecipe(new ItemStack(linkCard,1,0), new Object[]
				{new ItemStack(linkCard,1,0)});
		
		//ribbon
		GameRegistry.addRecipe(new ItemStack(craftingMats, 2, 0), new Object[] {"ss","ss","ss",
			's',Items.string});
		
		//super note block
		GameRegistry.addRecipe(new ItemStack(craftingMats, 1, 1), new Object[] {"rnr","ncn","pnp",
			'r',Items.redstone,
			'n',Blocks.noteblock,
			'c',Blocks.chest,
			'p',Blocks.glass_pane});
	}
	
	public static void registerDungeonLoot() {
		for (int i = 1; i < HairStyleType.values().length; i++) {
			ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(hairStyleUnlock, 1, i), 1, 1, 5));
		}
		
		/*
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(cassetteTape), 1, 1, 5));
		*/
		//MinecraftForge.addGrassSeed(new ItemStack(linkCard,1,0), 20);
	}
}
