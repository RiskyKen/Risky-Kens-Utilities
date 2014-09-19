package riskyken.utilities.common.tileentities;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import riskyken.utilities.common.config.ConfigHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Optional.InterfaceList({
    @Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2", striprefs = false)
    })
public abstract class TileEntityUtilitiesBasePowered extends TileEntityUtilitiesBase implements IEnergySink {

    private static final String TAG_POWER_LEVEL = "powerLevel";

    public double powerLevel;
    private final double maxPowerLevel;

    private final double workPowerLevel;

    public TileEntityUtilitiesBasePowered(int workPowerLevel, int maxPowerLevel) {
        super();
        this.maxPowerLevel = maxPowerLevel;
        this.workPowerLevel = workPowerLevel;
    }

    private void addPower(double amount) {
        powerLevel += amount;
        // System.out.println("total power " + powerLevel);
        if (powerLevel > maxPowerLevel) {
            powerLevel = maxPowerLevel;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setDouble(TAG_POWER_LEVEL, powerLevel);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        powerLevel = compound.getDouble(TAG_POWER_LEVEL);
    }

    public void useWorkPower() {
        powerLevel -= workPowerLevel;
    }

    public boolean havePowerToWork() {
        return powerLevel >= workPowerLevel;
    }

    public World getWorld() {
        return worldObj;
    }

    @Override
    public void updateEntity() {
        if (Loader.isModLoaded("IC2")) {
            EnergyTileLoad();
        }
        super.updateEntity();
    }

    @Override
    public void invalidate() {
        if (Loader.isModLoaded("IC2")) {
            EnergyTileUnload();
        }
        super.invalidate();
    }

    private boolean eNetSetup = false;

    @Optional.Method(modid = "IC2")
    private void EnergyTileLoad() {
        if (!eNetSetup & !worldObj.isRemote) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            eNetSetup = true;
        }
    }

    @Optional.Method(modid = "IC2")
    private void EnergyTileUnload() {
        if (!worldObj.isRemote) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        }
    }

    @Optional.Method(modid = "IC2")
    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter,
            ForgeDirection direction) {
        return true;
    }

    @Optional.Method(modid = "IC2")
    @Override
    public double getDemandedEnergy() {
        return maxPowerLevel - powerLevel;
    }

    @Optional.Method(modid = "IC2")
    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        addPower(amount * ConfigHandler.conversionRateEu);
        // System.out.println("input amount eu " + amount);
        if (powerLevel > maxPowerLevel) {
            powerLevel = maxPowerLevel;
        }
        return amount;
    }

    @Optional.Method(modid = "IC2")
    @Override
    public int getSinkTier() {
        return 4;
    }

    public double getPowerLevel() {
        return powerLevel;
    }

    public double getMaxPowerLevel() {
        return maxPowerLevel;
    }

    public double getWorkPowerLevel() {
        return workPowerLevel;
    }

    @SideOnly(Side.CLIENT)
    public void updatePowerLevel(double powerLevel) {
        // System.out.println("new power level " + powerLevel);
        this.powerLevel = powerLevel;
        // worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        // this.markDirty();
        // worldObj.ma
    }
}
