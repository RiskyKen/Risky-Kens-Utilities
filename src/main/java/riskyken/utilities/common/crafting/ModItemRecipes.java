package riskyken.utilities.common.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public final class ModItemRecipes {
	
	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hissingStick,1,0), new Object[]
				{" h","s ",
				'h',new ItemStack(Items.skull,1,4),
				's',"stickWood"}));

		
		if (ConfigHandler.saddle_recipe) {
			GameRegistry.addRecipe(new ItemStack(Items.saddle,1,0), new Object[] {"lll","lil",
				'i',Items.iron_ingot,
				'l',Items.leather});
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrades, 1, 0), new Object[]
				{"rrr","rpr","rsr",
				'r',Items.redstone,
				'p',Items.potato,
				's',"stickWood"}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrades, 1, 1), new Object[]
				{" u ","uiu"," u ",
				'u',new ItemStack(ModItems.upgrades, 1, 0),
				'i',Items.iron_ingot}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrades, 1, 2), new Object[]
				{" u ","uiu"," u ",
				'u',new ItemStack(ModItems.upgrades, 1, 1),
				'i',Items.gold_ingot}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrades, 1, 3), new Object[]
				{"uuu","uiu","uuu",
				'u',new ItemStack(ModItems.upgrades, 1, 2),
				'i',Blocks.diamond_block}));
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.transportDevice, 1, 0), new Object[]
				{"isi","iei"," i ",
				'i',Items.iron_ingot,
				'e',Items.ender_pearl,
				's',"stickWood"}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.linkCard, 1, 0), new Object[]
				{Items.paper,
				"dyeYellow",
				"dyeBlack"}));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.linkCard,1,0), new Object[]
				{new ItemStack(ModItems.linkCard,1,0)});
		
		//ribbon
		GameRegistry.addRecipe(new ItemStack(ModItems.craftingMats, 2, 0), new Object[] {"ss","ss","ss",
			's',Items.string});
		
		//super note block
		GameRegistry.addRecipe(new ItemStack(ModItems.craftingMats, 1, 1), new Object[] {"rnr","ncn","pnp",
			'r',Items.redstone,
			'n',Blocks.noteblock,
			'c',Blocks.chest,
			'p',Blocks.glass_pane});
	}
}
