package riskyken.utilities.common.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;
import riskyken.utilities.utils.ModLogger;

public class WorldGenDungeon extends WorldGenerator {

	public static final int ROOM_HEIGHT = 7;
	
	@Override
	public boolean generate(World world, Random rnd, int x, int y, int z) {
		
		int dungeonSize = (int) (rnd.nextFloat() * 3) + 1;
		int roomSize  = (int) (rnd.nextFloat() * dungeonSize) + 8;
		ArrayList<Rectangle> roomList = new ArrayList<Rectangle>();
		
		
		if (validSpawn(world, x, y, z, roomSize, ROOM_HEIGHT + 1) & validSpawn(world, x, y - ROOM_HEIGHT + 1, z, 4, ROOM_HEIGHT)) {
			
			ModLogger.log("Spawning dungeon of size " + dungeonSize + " at x:" + x + " y:" + y + " z:" + z);
			generateRoom(world, rnd, null, x, y - ROOM_HEIGHT + 1, z, 3, ROOM_HEIGHT, 1);
			spawnRoom(world, rnd, roomList, x, y, z, dungeonSize, roomSize, ROOM_HEIGHT + 1, 0);
			
			return true;
		}
		
		roomList.clear();
		return false;
	}
	
	private boolean validSpawn(World world, int x, int y, int z, int size, int height) {
		int airBlocks = 0;
		int blockCount = 0;
		
		for (int iy = 1; iy <= height; iy++) {
			for (int ix = -size; ix <= size; ix++) {
				for (int iz = -size; iz <= size; iz++) {
					if (world.isAirBlock(x + ix, y + iy, z + iz)) {
						airBlocks++;
					} else {
						//return false;
					}
					blockCount++;
				}
			}
		}
		
		if (airBlocks > blockCount * 0.05) {
			//ModLogger.logger.info("Air blocks " + airBlocks + " total blocks " + blockCount);
			//return true;
			return false;
		}
		
		return true;
	}
	
	private boolean intersectsExistingRoom(ArrayList<Rectangle> roomList, Rectangle room) {
		for(Rectangle iRoom : roomList){
		    if (iRoom.intersects(room)) { return true; }
		}
		return false;
	}
	
	private void spawnRoom(World world, Random rnd, ArrayList<Rectangle> roomList, int x, int y, int z, int level, int size, int height, int roomId) {
		
		//System.out.println("spawning room x:" + x + " y:" + y + " z:" + z + " size:" + size + " level:" + level);
		
		generateRoom(world, rnd, roomList, x, y, z, size, height, roomId);
		
		//System.out.println("rooms " + roomList.size() + " level " + level);
		
		if (level < 1) {
			return;
		}
		
		if (roomList.size() > 50) {
			//return;
		}
		
		for (int ix = -1; ix <= 1; ix++) {
			for (int iz = -1; iz <= 1; iz++) {
				if (ix == 0 | iz == 0) {
					if (ix != iz) {
						int roomSize  = (int) (rnd.nextFloat() * 3) + 3;
						int roomX = x + (roomSize + size + 2) * ix;
						int roomZ = z + (roomSize + size + 2) * iz;
						
						if (!intersectsExistingRoom(roomList, new Rectangle(roomX + -roomSize, roomZ + -roomSize, roomSize * 2 + 1, roomSize * 2 + 1))) {
							spawnRoom(world, rnd, roomList, x + (roomSize + size + 2) * ix, y, z + (roomSize + size + 2) * iz, level - 1, roomSize, ROOM_HEIGHT, 2);
							generateDoor(world, rnd, x + (size + 1) * ix, y, z + (size + 1) * iz, 1, ROOM_HEIGHT - 2, ix, iz);
						}
					}
				}
			}
		}
	}
	
	private void generateRoom(World world, Random rnd, ArrayList<Rectangle> roomList, int x, int y, int z, int size, int height, int roomId) {
		
		if (roomList != null) {
			Rectangle rec = new Rectangle(x + -size, z + -size, size * 2 + 1, size * 2 + 1);
			roomList.add(rec);
			//System.out.println("adding room to list x:" + rec.toString() );
		}
		
		
		for (int iy = 1; iy <= height; iy++) {
			for (int ix = -size; ix <= size; ix++) {
				for (int iz = -size; iz <= size; iz++) {
					
					if (iy == 1 | iy == height | ix == -size | ix == size | iz == -size | iz == size) {
						if (roomId == 1) {
							world.setBlock(x + ix, y + iy, z + iz, Blocks.obsidian);
						} else {
							if (iy == 1) {
								placeRandomCobbleBlock(world, rnd, x + ix, y + iy, z + iz);
							} else {
								world.setBlock(x + ix, y + iy, z + iz, Blocks.cobblestone);
							}

						}

						
						if (roomId == 0) {
							if (iy == 1 & ix > -3 & iz > -3 & ix < 3 & iz < 3) {
								if (ix == iz) {
									world.setBlock(x + ix, y + iy, z + iz, Blocks.wool, 14, 2);
								}
								else if (-ix == iz) {
									world.setBlock(x + ix, y + iy, z + iz, Blocks.wool, 14, 2);
								}
							}
						}
						
						if (size > 5) {
							if (ix == iz | -ix == iz) {
								if (iy == 1 & ix == -3 | ix ==3 | iz == -3 | iz == 3) {
									generatePiller(world, rnd, x + ix, y + 2, z + iz, height - 2);
								}
							}
						}

						
					} else {
						world.setBlockToAir(x + ix, y + iy, z + iz);
					}
					
				}
			}
		}
		
		if (roomId != 1) {
			
			if (rnd.nextFloat() * 100 >= 50) {
				placeModSpawner(world, rnd, x, y + 3, z);
				placeLootChest(world, rnd, x, y + 2, z, ChestGenHooks.DUNGEON_CHEST);
			} else {
				placeModSpawner(world, rnd, x, y + 2, z);
			}
		} else {
			world.setBlock(x, y + 2, z, Blocks.torch, 0, 2);
			placeLootChest(world, rnd, x + 2, y + 2, z, ChestGenHooks.VILLAGE_BLACKSMITH);
			placeLootChest(world, rnd, x - 2, y + 2, z, ChestGenHooks.PYRAMID_DESERT_CHEST);
			placeLootChest(world, rnd, x, y + 2, z + 2, ChestGenHooks.PYRAMID_JUNGLE_CHEST);
			placeLootChest(world, rnd, x, y + 2, z - 2, ChestGenHooks.VILLAGE_BLACKSMITH);
			
			world.setBlock(x + 2, y + 2, z + 2, getRandomLootBlock(rnd), 0, 2);
			world.setBlock(x + 2, y + 2, z - 2, getRandomLootBlock(rnd), 0, 2);
			world.setBlock(x - 2, y + 2, z + 2, getRandomLootBlock(rnd), 0, 2);
			world.setBlock(x - 2, y + 2, z - 2, getRandomLootBlock(rnd), 0, 2);
		}
	}
	
	private Block getRandomLootBlock(Random rnd) {
		int luck = (int) (rnd.nextFloat() * 100);
		Block targetBlock;
		if (luck > 95) {
			targetBlock = Blocks.diamond_block;
		}
		else if (luck > 75) {
			targetBlock = Blocks.gold_block;
		}
		else if (luck > 50) {
			targetBlock = Blocks.lapis_block;
		}
		else if (luck > 25) {
			targetBlock = Blocks.iron_block;
		}
		else {
			targetBlock = Blocks.coal_block;
		}
		return targetBlock;
	}
	
	private void generatePiller(World world, Random rnd, int x, int y, int z, int height) {
		for (int iy = 0; iy < height; iy++){
			//world.setBlock(x , y + iy, z , Blocks.wool, 6, 2);
			placeRandomCobbleBlock(world, rnd, x, y + iy, z);
		}
	}
	
	private void placeRandomCobbleBlock(World world, Random rnd, int x, int y, int z) {
		if (rnd.nextFloat() * 100 < 75) {
			world.setBlock(x, y, z, Blocks.mossy_cobblestone);
		} else {
			world.setBlock(x, y, z, Blocks.cobblestone);
		}
	}
	
	private void generateDoor(World world, Random rnd, int x, int y, int z, int size, int height, int xFlip, int zFlip) {
		
		for (int iy = 1; iy <= height; iy++) {
			for (int iSize = -size; iSize <= size; iSize++) {
				if (iy == 1 | iy == height) {
					if (iy == 1) {
						placeRandomCobbleBlock(world, rnd, x + (iSize * zFlip), y + iy, z + (iSize * xFlip));
					} else {
						world.setBlock(x + (iSize * zFlip), y + iy, z + (iSize * xFlip), Blocks.cobblestone);
					}
					
				} else {
					world.setBlockToAir(x + (iSize * xFlip), y + iy, z + (iSize * zFlip));
				}
				
				if (iSize == -size | iSize == size) {
					if (iy == 1) {
						placeRandomCobbleBlock(world, rnd, x + (iSize * zFlip), y + iy, z + (iSize * xFlip));
					} else {
						world.setBlock(x + (iSize * zFlip), y + iy, z + (iSize * xFlip), Blocks.cobblestone);
					}
				}
			}
		}
		
		generateDoorTrap(world, rnd, x, y + 2, z, xFlip, zFlip);
	}
	
	private void generateDoorTrap(World world, Random rnd, int x, int y, int z, int xFlip, int zFlip) {
		boolean trapped = rnd.nextFloat() * 100 >= 75;
		
		world.setBlock(x + (-1 * xFlip * 2), y, z + (-1 * zFlip * 2), Blocks.stone_pressure_plate);
		world.setBlock(x + (1 * xFlip * 2), y, z + (1 * zFlip * 2), Blocks.stone_pressure_plate);
		
		if (!trapped) { return; }
		
		world.setBlock(x + (-1 * xFlip * 2), y - 3, z + (-1 * zFlip * 2), Blocks.stone);
		world.setBlock(x + (1 * xFlip * 2), y - 3, z + (1 * zFlip * 2), Blocks.stone);
		
		world.setBlock(x + (-1 * xFlip * 2), y - 2, z + (-1 * zFlip * 2), Blocks.redstone_wire);
		world.setBlock(x + (1 * xFlip * 2), y - 2, z + (1 * zFlip * 2), Blocks.redstone_wire);
		
		
		if (xFlip == 1 | xFlip == -1) {
			world.setBlockToAir(x, y - 1, z + 1);
			
			world.setBlock(x, y - 3, z + 2, Blocks.stone, 0, 2);
			
			world.setBlockToAir(x, y - 2, z + 3);
			world.setBlockToAir(x, y - 2, z + 1);
			world.setBlockToAir(x + 1, y - 2, z + 2);
			world.setBlockToAir(x - 1, y - 2, z + 2);
			world.setBlock(x, y - 2, z + 2, Blocks.redstone_torch, 0, 2);
			world.setBlock(x, y - 1, z + 2, Blocks.sticky_piston, 2, 2);
			
			world.setBlock(x + 1, y - 4, z + 2, Blocks.stone, 0, 2);
			world.setBlock(x + 2, y - 4, z + 2, Blocks.stone, 0, 2);
			world.setBlock(x - 1, y - 4, z + 2, Blocks.stone, 0, 2);
			world.setBlock(x - 2, y - 4, z + 2, Blocks.stone, 0, 2);
			world.setBlock(x + 2, y - 4, z + 1, Blocks.stone, 0, 2);
			world.setBlock(x - 2, y - 4, z + 1, Blocks.stone, 0, 2);
			
			world.setBlockToAir(x + 2, y - 2, z + 1);
			world.setBlockToAir(x - 2, y - 2, z + 1);
			
			world.setBlock(x + 1, y - 3, z + 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 2, y - 3, z + 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x - 1, y - 3, z + 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x - 2, y - 3, z + 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 2, y - 3, z + 1, Blocks.redstone_wire, 0, 2);
			world.setBlock(x - 2, y - 3, z + 1, Blocks.redstone_wire, 0, 2);
		} 
		if (zFlip == 1 | zFlip == -1) {
			world.setBlockToAir(x + 1, y - 1, z);
			
			world.setBlock(x + 2, y - 3, z, Blocks.stone, 0, 2);
			
			world.setBlockToAir(x + 3, y - 2, z);
			world.setBlockToAir(x + 1, y - 2, z);
			world.setBlockToAir(x + 2, y - 2, z + 1);
			world.setBlockToAir(x + 2, y - 2, z - 1);
			world.setBlock(x + 2, y - 2, z, Blocks.redstone_torch, 0, 2);
			world.setBlock(x + 2, y - 1, z, Blocks.sticky_piston, 4, 2);
			
			world.setBlock(x + 2, y - 4, z + 1, Blocks.stone, 0, 2);
			world.setBlock(x + 2, y - 4, z + 2, Blocks.stone, 0, 2);
			world.setBlock(x + 2, y - 4, z - 1, Blocks.stone, 0, 2);
			world.setBlock(x + 2, y - 4, z - 2, Blocks.stone, 0, 2);
			world.setBlock(x + 1, y - 4, z + 2, Blocks.stone, 0, 2);
			world.setBlock(x + 1, y - 4, z - 2, Blocks.stone, 0, 2);
			
			world.setBlockToAir(x + 1, y - 2, z - 2);
			world.setBlockToAir(x + 1, y - 2, z + 2);
			
			world.setBlock(x + 2, y - 3, z + 1, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 2, y - 3, z + 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 2, y - 3, z - 1, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 2, y - 3, z - 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 1, y - 3, z - 2, Blocks.redstone_wire, 0, 2);
			world.setBlock(x + 1, y - 3, z + 2, Blocks.redstone_wire, 0, 2);

		} 
		
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = -1; ix <= 1; ix++) {
				for (int iz = -1; iz <= 1; iz++) {
					world.setBlock(x + ix, y - 4 + iy, z + iz, Blocks.stone);
				}
			}
		}
		world.setBlock(x, y - 3, z, Blocks.water);
		world.setBlock(x, y - 2, z, Blocks.water);
	}
	
	private void placeModSpawner(World world, Random rnd, int x, int y, int z) {
		world.setBlock(x , y, z, Blocks.mob_spawner, 0, 2);
		TileEntityMobSpawner te = (TileEntityMobSpawner)world.getTileEntity(x , y, z);
		if (te != null) {
			te.func_145881_a().setEntityName(pickMobSpawner(rnd));
		} else {
			ModLogger.log(Level.WARN, "Failed to fetch mob spawner entity at (" + x + ", " + y  + ", " + z + ")");
		}
	}
	
	private void placeLootChest(World world, Random rnd, int x, int y, int z, String type) {
		world.setBlock(x , y, z, Blocks.chest, 0, 2);
		TileEntityChest te = (TileEntityChest)world.getTileEntity(x, y, z);
		if (te != null) {
			WeightedRandomChestContent.generateChestContents(rnd, ChestGenHooks.getItems(type, rnd), te, ChestGenHooks.getCount(type, rnd));
		} else {
			ModLogger.log(Level.WARN, "Failed to fetch chest entity at (" + x + ", " + y  + ", " + z + ")");
		}
	}
	
    private String pickMobSpawner(Random rnd)
    {
        return DungeonHooks.getRandomDungeonMob(rnd);
    }
}
