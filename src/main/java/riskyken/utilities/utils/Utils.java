package riskyken.utilities.utils;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class Utils {
	
	public static int getMinecraftColor(int meta) {
		if (meta == 0) { return 16777215; }
		if (meta == 1) { return 14188339; }
		if (meta == 2) { return 11685080; }
		if (meta == 3) { return 6724056; }
		if (meta == 4) { return 15066419; }
		if (meta == 5) { return 8375321; }
		if (meta == 6) { return 15892389; }
		if (meta == 7) { return 5000268; }
		if (meta == 8) { return 10066329; }
		if (meta == 9) { return 5013401; }
		if (meta == 10) { return 8339378; }
		if (meta == 11) { return 3361970; }
		if (meta == 12) { return 6704179; }
		if (meta == 13) { return 6717235; }
		if (meta == 14) { return 10040115; }
		if (meta == 15) { return 1644825; }
		return 0;
	}
	
	public static String getMinecraftColorName(int meta) {
		if (meta == 0) { return "White"; }
		if (meta == 1) { return "Orange"; }
		if (meta == 2) { return "Magenta"; }
		if (meta == 3) { return "Light blue"; }
		if (meta == 4) { return "Yellow"; }
		if (meta == 5) { return "Lime"; }
		if (meta == 6) { return "Pink"; }
		if (meta == 7) { return "Gray"; }
		if (meta == 8) { return "Light gray"; }
		if (meta == 9) { return "Cyan"; }
		if (meta == 10) { return "Purple"; }
		if (meta == 11) { return "Blue"; }
		if (meta == 12) { return "Brown"; }
		if (meta == 13) { return "Green"; }
		if (meta == 14) { return "Red"; }
		if (meta == 15) { return "Black"; }
		return "?";
	}
	
	public static String getMinecraftColorOreName(int meta) {
		if (meta == 0) { return "dyeWhite"; }
		if (meta == 1) { return "dyeOrange"; }
		if (meta == 2) { return "dyeMagenta"; }
		if (meta == 3) { return "dyeLightBlue"; }
		if (meta == 4) { return "dyeYellow"; }
		if (meta == 5) { return "dyeLime"; }
		if (meta == 6) { return "dyePink"; }
		if (meta == 7) { return "dyeGray"; }
		if (meta == 8) { return "dyeLightGray"; }
		if (meta == 9) { return "dyeCyan"; }
		if (meta == 10) { return "dyePurple"; }
		if (meta == 11) { return "dyeBlue"; }
		if (meta == 12) { return "dyeBrown"; }
		if (meta == 13) { return "dyeGreen"; }
		if (meta == 14) { return "dyeRed"; }
		if (meta == 15) { return "dyeBlack"; }
		return "?";
	}
	
    public static int getScaledValue(int scale, int current, int max) {
        return (int)Math.ceil(current * scale / max);
    }
    
    public static double getDistance(int x1, int y1, int z1, int x2, int y2, int z2) {
    	return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) + (z1-z2)*(z1-z2));
    }
    
    public static ArrayList getBlockAroundBlock(Vector3 blockCoord) {
		ArrayList<Vector3> result = new ArrayList<Vector3>();
		result.add(new Vector3(blockCoord.x, blockCoord.y + 1, blockCoord.z));
		result.add(new Vector3(blockCoord.x, blockCoord.y - 1, blockCoord.z));
		result.add(new Vector3(blockCoord.x + 1, blockCoord.y, blockCoord.z));
		result.add(new Vector3(blockCoord.x - 1, blockCoord.y, blockCoord.z));
		result.add(new Vector3(blockCoord.x, blockCoord.y, blockCoord.z + 1));
		result.add(new Vector3(blockCoord.x, blockCoord.y, blockCoord.z - 1));
		return result;
	}
    
    public static void travelToDimensionLocation(Entity player, int dimensionId, double x, double y ,double z)
    {
        if (!player.worldObj.isRemote && !player.isDead)
        {
        	player.worldObj.theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int j = player.dimension;
            WorldServer worldserver = minecraftserver.worldServerForDimension(j);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
            player.dimension = dimensionId;

            if (j == 1 && dimensionId == 1)
            {
                worldserver1 = minecraftserver.worldServerForDimension(0);
                player.dimension = 0;
            }

            player.worldObj.removeEntity(player);
            player.isDead = false;
            player.worldObj.theProfiler.startSection("reposition");
            minecraftserver.getConfigurationManager().transferEntityToWorld(player, j, worldserver, worldserver1);
            player.worldObj.theProfiler.endStartSection("reloading");
            Entity entity = EntityList.createEntityByName(EntityList.getEntityString(player), worldserver1);

            if (entity != null)
            {
                entity.copyDataFrom(player, true);

                //if (j == 1 && dimensionId == 1)
                //{
                    //ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
                    //chunkcoordinates.posY = player.worldObj.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                    
                    //entity.setLocationAndAngles((double)chunkcoordinates.posX, (double)chunkcoordinates.posY, (double)chunkcoordinates.posZ, entity.rotationYaw, entity.rotationPitch);
               // }
                
                entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);

                worldserver1.spawnEntityInWorld(entity);
            }

            player.isDead = true;
            player.worldObj.theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            player.worldObj.theProfiler.endSection();
        }
    }
    
    public static void transferEntityToWorld(Entity entity, int dimensionId, double x, double y, double z)
    {
    	MinecraftServer minecraftserver = MinecraftServer.getServer();
        int oldDimensionId = entity.dimension;
        WorldServer pOld = minecraftserver.worldServerForDimension(oldDimensionId);
        WorldServer pNew = minecraftserver.worldServerForDimension(dimensionId);
    	
        //double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
        double d0 = x;
        double d1 = z;
        
        //double d3 = par1Entity.posX;
        double d4 = y;
        //double d5 = par1Entity.posZ;
        
        float f = entity.rotationYaw;
        
        pOld.theProfiler.startSection("moving");

//        if (entity.dimension == 1)
//        {
//            ChunkCoordinates chunkcoordinates;
//
//            if (par2 == 1)
//            {
//                chunkcoordinates = pNew.getSpawnPoint();
//            }
//            else
//            {
//                chunkcoordinates = pNew.getEntrancePortalLocation();
//            }
//
//            d0 = (double)chunkcoordinates.posX;
//            entity.posY = (double)chunkcoordinates.posY;
//            d1 = (double)chunkcoordinates.posZ;
//            entity.setLocationAndAngles(d0, entity.posY, d1, 90.0F, 0.0F);
//
//            if (entity.isEntityAlive())
//            {
//            	pOld.updateEntityWithOptionalForce(entity, false);
//            }
//        }

        pOld.theProfiler.endSection();

        //if (par2 != 1)
        //{
        	pOld.theProfiler.startSection("placing");
            d0 = (double)MathHelper.clamp_int((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.clamp_int((int)d1, -29999872, 29999872);

            if (entity.isEntityAlive())
            {
            	pNew.spawnEntityInWorld(entity);
            	entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
                pNew.updateEntityWithOptionalForce(entity, false);
                //teleporter.placeInPortal(entity, d3, d4, d5, f);
            }

            pOld.theProfiler.endSection();
        //}

        entity.setWorld(pNew);
    }
}
