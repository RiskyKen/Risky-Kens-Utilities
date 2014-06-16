package riskyken.utilities.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import riskyken.utilities.common.blocks.ModBlocks;

public class WorldGenMeteor extends WorldGenerator
{
	
	private static final int METEOR_MAX_SIZE = 2;
	private static final int METEOR_MIN_SIZE = 4;
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		if (world.provider.hasNoSky) { return true;}
		
		for (int i = 255; i > 40; i--)
		{
			if (!canPlaceOn(world, x, i, z))
			{
				if (isValidBlock(world, x, i, z))
				{
					spawnMeteor(world, random, (int)(random.nextFloat() * METEOR_MAX_SIZE) + METEOR_MIN_SIZE, x, i, z);
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}
	
	private void spawnMeteor(World world, Random random, int size, int x, int y, int z)
	{
		int waterLevel = findWaterLevel(world, size, x, y, z);
		for (int ix = -size; ix <= size; ix++)
		{
			for (int iz = -size; iz <= size; iz++)
			{
				for (int iy = -size; iy <= size; iy++)
				{
					double distance = Math.sqrt((x-x-ix)*(x-x-ix) + (z-z - iz)*(z-z-iz) + (y-y - iy)*(y-y-iy));
					buildShell(world, random, distance, size, x + ix, y + iy, z + iz);
					clearCenter(world, random, distance, size, waterLevel, x + ix, y + iy, z + iz);
					makeCenter(world, size, distance, x + ix, y + iy - 1, z + iz);
				}
			}
		}
		
		for (int ix = -size; ix <= size; ix++)
		{
			for (int iz = -size; iz <= size; iz++)
			{
				for (int iy = -size; iy <= size; iy++)
				{
					//addNoise(world, random, x + ix, y + iy, z + iz);
					spawnFire(world, random, x + ix, y + iy, z + iz);
					checkCanStay(world, random, x + ix, y + iy, z + iz);
				}
			}
		}
	}
	
	private void buildShell(World world, Random random, double distance, int size, int x, int y, int z)
	{
		if (distance > size - 1 && distance <= size) {
			if (isValidBlock(world, x, y, z)) {
				if (random.nextFloat() * 100 <= 50) {
					world.setBlock(x, y, z, ModBlocks.meteor, 0, 2);
				}
			}
		}
	}
	
	private int findWaterLevel(World world, int size, int x, int y, int z)
	{
		int waterLevel = 0;
		for (int ix = -size; ix <= size; ix++)
		{
			for (int iz = -size; iz <= size; iz++)
			{
				for (int iy = -size; iy <= size; iy++)
				{
					double distance = Math.sqrt((x-x-ix)*(x-x-ix) + (z-z - iz)*(z-z-iz) + (y-y - iy)*(y-y-iy));
					if (distance > size - 1 && distance <= size)
					{
						Block block = world.getBlock(x + ix, y + iy, z + iz);
						if(block == Blocks.water | block == Blocks.flowing_water)
						{
							if (iy + y > waterLevel) {waterLevel = iy + y;}
						}
					}
				}
			}
		}
		return waterLevel;
	}
	
	private void clearCenter(World world, Random random, double distance, int size, int waterLevel, int x, int y, int z)
	{
		if (distance <=  size - 1)
		{
			if (world.getBlock(x, y, z) != Blocks.flowing_water && world.getBlock(x, y, z) != Blocks.water)
			{
				if (waterLevel >= y)
				{
					world.setBlock(x, y, z, Blocks.water, 0, 2);
				}
				else
				{
					world.setBlockToAir(x, y, z);
				}
			}
		}
	}
	
	private void makeCenter(World world, int size, double distance, int x, int y, int z)
	{
		if (distance <=  size / 2 - 1)
		{
			world.setBlock(x, y - size / 2, z, ModBlocks.meteor, 1, 2);
		}
	}
	
	private void addNoise(World world, Random random, int x, int y, int z)
	{
		
	}
	
	private void spawnFire(World world, Random random, int x, int y, int z)
	{
		if (world.getBlock(x, y, z) == ModBlocks.meteor && world.isAirBlock(x, y + 1, z))
		{
			if (random.nextFloat() * 100 <= 60)
			{
				world.setBlock(x, y + 1, z, Blocks.fire, 0, 2);
			}
		}
	}
	
	private void checkCanStay(World world, Random random, int x, int y, int z)
	{
		if (!world.isAirBlock(x, y, z))
		{
			if (!world.getBlock(x, y, z).canBlockStay(world, x, y, z))
			{
				world.setBlockToAir(x, y, z);
			}
		}
	}
	
	private void spawnTestPiller(World world, int x, int y, int z)
	{
		for (int i = y; i < 128; i++)
		{
			world.setBlock(x, i, z, Blocks.bedrock, 0, 2);
		}
	}
	
	private boolean canPlaceOn(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y, z)) { return true;}
		if (world.getBlock(x, y, z) == Blocks.snow ) { return true;}
		if (world.getBlock(x, y, z) == Blocks.tallgrass ) { return true;}
		if (world.getBlock(x, y, z) == Blocks.water ) { return true;}
		if (world.getBlock(x, y, z) == Blocks.flowing_water ) { return true;}
		if (world.getBlock(x, y, z) == Blocks.ice ) { return true;}
		
		return false;
	}
	
	private boolean isValidBlock(World world, int x, int y, int z)
	{
		Block block =  world.getBlock(x, y, z);
		
		if (block == Blocks.grass) {return true;}
		if (block == Blocks.sand) {return true;}
		if (block == Blocks.stone) {return true;}
		if (block == Blocks.dirt) {return true;}
		if (block == Blocks.snow) {return true;}
		if (block == Blocks.gravel) {return true;}
		if (block == Blocks.sandstone) {return true;}
		
		return false;
	}

}
