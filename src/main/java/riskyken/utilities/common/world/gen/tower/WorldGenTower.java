package riskyken.utilities.common.world.gen.tower;

import java.util.Random;

import riskyken.utilities.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTower extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rnd, int x, int y, int z) {
		
		if (isValidSpawnArea(world, x, y, z)) {
			//generateTower(world, rnd, x, y - i, z);
			//return true;
		}
		
		for (int i = 0; i < 20; i++) {
			//world.setBlock(x, y - i, z, Blocks.bookshelf);
			if (!world.isAirBlock(x, y - i, z) && world.isAirBlock(x, y - i + 1, z)) {
				
				
				
			}
		}
		
		
		return false;
	}
	
	private boolean isValidSpawnArea(World world, int x, int y, int z) {
		for (int i = 0; i < 20; i++) {
			if (!world.isAirBlock(x, y - i, z) && world.isAirBlock(x, y - i + 1, z)) {
				return true;
			}
		}
		return false;
	}
	
	private void generateTower(World world, Random rnd, int x, int y, int z) {
		for (int iy = 0; iy < 20; iy++) {
			for (int ix = -10; ix <= 10; ix++) {
				for (int iz = -10; iz <= 10; iz++) {
					
					if (Utils.getDistance(x + ix, 1, z + iz, x, 1, z) >= 9 & Utils.getDistance(x + ix, 1, z + iz, x, 1, z) <= 10) {
						world.setBlock(x + ix, y + iy, z + iz, Blocks.stonebrick);
					}
					
				}
			}
		}
		
	}

}
