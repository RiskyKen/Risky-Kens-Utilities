package riskyken.utilities.common.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.utils.Utils;
import cpw.mods.fml.common.registry.GameRegistry;

public final class ModBlockRecipes {
	
	public static void init() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.deviceHollower, 1, 0), new Object[]
				{"sps","hia","srs",
			's',Blocks.stone,
			'i',Blocks.iron_block,
			'r',Items.redstone,
			'h',Items.golden_shovel,
			'a',Items.golden_axe,
			'p',Items.golden_pickaxe});
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.deviceTeleporter, 1, 0), new Object[]
				{"sys","eie","srs",
			's',Blocks.stone,
			'r',Items.redstone,
			'e',Items.ender_pearl,
			'i',Blocks.iron_block,
			'y',new ItemStack(Items.dye, 1, 11)});
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.transporterBeacon, 1, 0), new Object[]
				{"sbs","eie","srs",
			's',Blocks.stone,
			'r',Items.redstone,
			'e',Items.ender_pearl,
			'i',Blocks.iron_block,
			'b',new ItemStack(Items.dye, 1, 4)});
		
		//depth gauge
		GameRegistry.addRecipe(new ItemStack(ModBlocks.deviceBlock, 1, 0), new Object[]
				{"sss","sis","sks",
			's',Blocks.stone,
			'i',Blocks.iron_block,
			'k',Items.string});
		
		//water drain
		GameRegistry.addRecipe(new ItemStack(ModBlocks.deviceBlock, 1, 1), new Object[]
				{"srs","rir","sbs",
			's',Blocks.stone,
			'r',Items.redstone,
			'i',Blocks.iron_block,
			'b',Items.bucket});
		
		//flood lights
		ItemStack lightCratingTiers[] = {new ItemStack(Items.coal), new ItemStack(Blocks.coal_block),
				new ItemStack(Blocks.glowstone), new ItemStack(ModBlocks.starMultiBlock, 1, 6),
				new ItemStack(ModBlocks.starMultiBlock, 1, 7)};
		
		for (int i = 0; i < 5; i++) {
			CraftingManager.addShapedRecipe(new ItemStack(ModBlocks.floodLight, 1, i), new Object[]
					{"wpw","igi","wiw",
				'i',Items.iron_ingot,
				'w',"plankWood",
				'g',lightCratingTiers[i],
				'p',Blocks.glass_pane});
			
			//Flood light upgrades
			for (int j = 0; j < 5; j++) {
				if (j != i) {
					CraftingManager.addShapelessRecipe(new ItemStack(ModBlocks.floodLight, 1, i), new Object[]
							{new ItemStack(ModBlocks.floodLight, 1, j), lightCratingTiers[i]});
					
					CraftingManager.addShapelessRecipe(new ItemStack(ModBlocks.floodLight, 1, i + 5), new Object[]
							{new ItemStack(ModBlocks.floodLight, 1, j + 5), lightCratingTiers[i]});
				}
			}
			
			//Flood light phased upgrades/down grades
			CraftingManager.addShapedRecipe(new ItemStack(ModBlocks.floodLight, 1, i + 5), new Object[]
					{" p ","plp"," p ",
				'l',new ItemStack(ModBlocks.floodLight, 1, i),
				'p',Items.ender_pearl});
			
			CraftingManager.addShapedRecipe(new ItemStack(ModBlocks.floodLight, 1, i), new Object[]
					{" r ","rlr"," r ",
				'l',new ItemStack(ModBlocks.floodLight, 1, i + 5),
				'r',Items.redstone});
		}
		
		//Phased Light Sponge
		for (int i = 0; i < 16; i++) {
			CraftingManager.addShapedRecipe(new ItemStack(ModBlocks.phasedLightSponge, 1), new Object[]
					{" p ","pcp"," p ",
				'c',new ItemStack(ModBlocks.paperCube, 1, i),
				'p',Items.ender_pearl});
		}
		
		//Paper cube colours
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (i != j) {
					CraftingManager.addShapelessRecipe(new ItemStack(ModBlocks.paperCube, 1, i), new Object[]
						{new ItemStack(ModBlocks.paperCube, 1, j), Utils.getMinecraftColorOreName(i)});
				}
			}
		}
		
		//Leaf Catcher
		CraftingManager.addShapedRecipe(new ItemStack(ModBlocks.leafCatcher, 1, 0), new Object[]
				{"w w","ici","wiw",
			'i',Items.iron_ingot,
			'w',"plankWood",
			'c',Blocks.chest});
		
		//Gift
		for (int i = 0; i < 16; i++) {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.gift, 1, 0), new Object[]
					{" r ","rpr"," r ",
				'r',new ItemStack(ModItems.craftingMats,1,0),
				'p',new ItemStack(ModBlocks.paperCube, 1, i)});
		}
		
		//Music Sequencer
		/*
		GameRegistry.addRecipe(new ItemStack(ModBlocks.musicSequencer, 1, 0), new Object[]
				{"dnd","rsr","ini",
			'd',Items.diamond,
			'n',Items.gold_ingot,
			'r',Blocks.redstone_block,
			's',new ItemStack(ModItems.craftingMats,1,1),
			'i',Items.iron_ingot});
		*/
		//star shell 1
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 6), new Object[]
				{"ccc","cmc","ccc",
			'c',Blocks.coal_block,
			'm',new ItemStack(ModBlocks.meteor, 1, 0)});
		
		//star shell 2
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 7), new Object[]
				{"ccc","cmc","ccc",
			'c',Blocks.coal_block,
			'm',new ItemStack(ModBlocks.meteor, 1, 1)});
		/*
		//star igniter
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 4), new Object[]
				{"scs","cmc","scs",
			'c',Blocks.coal_block,
			's',new ItemStack(ModBlocks.meteor, 1, 0),
			'm',new ItemStack(ModBlocks.meteor, 1, 1)});
		
		//star t1
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 0), new Object[]
				{"scs","cgc","scs",
			'c',Blocks.coal_block,
			's',new ItemStack(ModBlocks.starMultiBlock, 1, 7),
			'g',Blocks.glowstone});
		
		//star t2
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 1), new Object[]
				{"scs","cgc","scs",
			'c',new ItemStack(ModBlocks.starMultiBlock, 1, 7),
			's',new ItemStack(ModBlocks.starMultiBlock, 1, 6),
			'g',new ItemStack(ModBlocks.starMultiBlock, 1, 0)});
		
		//star t3
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 2), new Object[]
				{"sns","ncn","sns",
			'c',new ItemStack(ModBlocks.starMultiBlock, 1, 1),
			's',new ItemStack(ModBlocks.starMultiBlock, 1, 6),
			'n',new ItemStack(ModBlocks.starMultiBlock, 1, 0)});
		
		//star t4
		GameRegistry.addRecipe(new ItemStack(ModBlocks.starMultiBlock, 1, 3), new Object[]
				{"sns","ncn","sns",
			'c',new ItemStack(ModBlocks.starMultiBlock, 1, 2),
			's',new ItemStack(ModBlocks.starMultiBlock, 1, 6),
			'n',Items.nether_star});
		*/
	}
}
