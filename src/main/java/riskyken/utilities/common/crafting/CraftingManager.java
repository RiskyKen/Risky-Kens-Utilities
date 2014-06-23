package riskyken.utilities.common.crafting;

import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public final class CraftingManager {

	public static void init() {
		if (ConfigHandler.disableRecipes) { return; }
		ModBlockRecipes.init();
		ModItemRecipes.init();
	}
	
	public static void addShapelessRecipe(ItemStack result, Object[] recipe) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(result, recipe));
	}
	
	public static void addShapedRecipe(ItemStack result, Object[] recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(result, recipe));
	}
}
