package riskyken.utilities.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.world.gen.tower.WorldGenTower;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class GenerationHandler implements IWorldGenerator {

	private WorldGenerator towerGen;
	private WorldGenerator dungeonGen;
	private WorldGenerator meteorGen;
	
	public GenerationHandler()
	{
		GameRegistry.registerWorldGenerator(this, 0);
		towerGen = new WorldGenTower();
		dungeonGen = new WorldGenDungeon();
		meteorGen = new WorldGenMeteor();
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		//generateTower(random, chunkX, chunkZ, world, towerGen, 70, 100);
		if (ConfigHandler.gen_dungeons) {
			generateDungeon(random, chunkX * 16, chunkZ * 16, world, dungeonGen, 8, 9);
		}
		generateMeteor(random, chunkX * 16, chunkZ * 16, world, 5, meteorGen, 75, 100);
	}
	
	private void generateTower(Random rand, int chunkX, int chunkZ, World world, WorldGenerator gen, int lowestY, int highestY){

		int x = chunkX * 16 + rand.nextInt(16);
		//int y = rand.nextInt(128);
		int y = rand.nextInt(highestY - lowestY) + lowestY;
		int z = chunkZ * 16 + rand.nextInt(16);
		//if (rand.nextFloat() * 1000 <= 2 )
		//{
			gen.generate(world, rand, x, y, z);
		//}
		
		
	}
	
	private void generateDungeon(Random rand, int chunkX, int chunkZ, World world, WorldGenerator gen, int lowestY, int highestY){
		int x = chunkX + rand.nextInt(16);
		int y = rand.nextInt(highestY - lowestY) + lowestY;
		int z = chunkZ + rand.nextInt(16);
		
		if ((chunkX % 160 == 0) && (chunkZ % 160 == 0)) {
			if (rand.nextFloat() * 100 <= 50 ) {
				gen.generate(world, rand, x, y, z);
			}
		}
	}
	
	private void generateMeteor(Random rand, int chunkX, int chunkZ, World world, int chance, WorldGenerator gen, int lowestY, int highestY){
		int x = chunkX + rand.nextInt(16);
		int y = rand.nextInt(highestY - lowestY) + lowestY;
		int z = chunkZ + rand.nextInt(16);
		int minChunkSpace = 10;
		
		if ((chunkX % (16 * minChunkSpace) == 0) && (chunkZ % (16 * minChunkSpace) == 0)) {
			if (rand.nextFloat() * 100 <= chance ) {
				gen.generate(world, rand, x, y, z);
			}
		}
	}
}
