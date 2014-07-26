package riskyken.utilities.common.items;

import net.minecraft.item.Item;

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
		//cassetteTape = new ItemCassetteTape();
		raisingHeart = new ItemRaisingHeart();
		//wings = new ItemArmorWings(4, 1);
		//hairStyleKit = new ItemHairStyleKit();
		//hairStyleUnlock = new ItemHairStyleUnlock();
	}
	
	public static void registerDungeonLoot() {
		/*
		for (int i = 1; i < HairStyleType.values().length; i++) {
			ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(hairStyleUnlock, 1, i), 1, 1, 5));
		}
		*/
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
