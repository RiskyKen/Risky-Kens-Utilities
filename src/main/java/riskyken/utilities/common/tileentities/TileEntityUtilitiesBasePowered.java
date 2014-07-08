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
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Optional.InterfaceList({
	@Optional.Interface(iface="buildcraft.api.power.IPowerReceptor", modid="BuildCraft|Core", striprefs = false),
	@Optional.Interface(iface="ic2.api.energy.tile.IEnergySink", modid="IC2", striprefs = false)
	})
public abstract class TileEntityUtilitiesBasePowered extends TileEntityUtilitiesBase implements IPowerReceptor, IEnergySink {

	private static final String TAG_POWER_LEVEL = "powerLevel";
	private static final String TAG_MJ_POWER_HANDLER = "mjPowerHandler";
	
	public double powerLevel;
	private final double maxPowerLevel;
	
	private final double workPowerLevel;
	
	private PowerHandler powerHandler;
	
	public TileEntityUtilitiesBasePowered(int workPowerLevel, int maxPowerLevel) {
		super();
		this.maxPowerLevel = maxPowerLevel;
		this.workPowerLevel = workPowerLevel;
		if (Loader.isModLoaded("BuildCraft|Core")) {
			setupPowerHandler();
		}
	}
	
	private void addPower(double amount) {
		powerLevel += amount;
		//System.out.println("total power " + powerLevel);
		if (powerLevel > maxPowerLevel) { powerLevel = maxPowerLevel; }
	}
	
	@Optional.Method(modid="BuildCraft|Core")
	private void setupPowerHandler() {
		powerHandler = new PowerHandler(this, Type.MACHINE);
		powerHandler.configure(2, 100, 1, 100);
		powerHandler.configurePowerPerdition(0, 0);
	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (Loader.isModLoaded("BuildCraft|Core")) {
			writePowerHandlerNBT(compound);
		}
		compound.setDouble(TAG_POWER_LEVEL, powerLevel);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (Loader.isModLoaded("BuildCraft|Core")) {
			readPowerHandlerNBT(compound);
		}
		powerLevel = compound.getDouble(TAG_POWER_LEVEL);
	}
	
	@Optional.Method(modid="BuildCraft|Core")
	private void readPowerHandlerNBT(NBTTagCompound compound) {
		powerHandler.readFromNBT(compound, TAG_MJ_POWER_HANDLER);
	}
	
	@Optional.Method(modid="BuildCraft|Core")
	private void writePowerHandlerNBT(NBTTagCompound compound) {
		powerHandler.writeToNBT(compound, TAG_MJ_POWER_HANDLER);
	}
	
	public void useWorkPower() {
		powerLevel -= workPowerLevel;
	}
	
	public boolean havePowerToWork() {
		return powerLevel >= workPowerLevel;
	}
	
	@Optional.Method(modid="BuildCraft|Core")
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}
	
	@Optional.Method(modid="BuildCraft|Core")
	@Override
	public void doWork(PowerHandler workProvider) {
		if (powerLevel == maxPowerLevel) { return; }
		if (powerHandler.getEnergyStored() < maxPowerLevel - powerLevel) {
			//System.out.println("input amount mj " + powerHandler.getEnergyStored());
			addPower(powerHandler.getEnergyStored() * ConfigHandler.conversionRateMj);
			
			//System.out.println("Adding power" + powerHandler.getEnergyStored());
			powerHandler.useEnergy(powerHandler.getEnergyStored(), powerHandler.getEnergyStored(), true);
		}
		else {
			powerHandler.useEnergy(maxPowerLevel - powerLevel, maxPowerLevel - powerLevel, true);
			powerLevel = maxPowerLevel;
		}
	}
	
	@Override
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
	
	@Optional.Method(modid="IC2")
	private void EnergyTileLoad() {
		if (!eNetSetup & !worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			eNetSetup = true;
		}
	}
	
	@Optional.Method(modid="IC2")
	private void EnergyTileUnload() {
		if (!worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
	}
	
	@Optional.Method(modid="IC2")
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return true;
	}
	
	@Optional.Method(modid="IC2")
	@Override
	public double getDemandedEnergy() {
		return maxPowerLevel - powerLevel;
	}
	
	@Optional.Method(modid="IC2")
	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		addPower(amount * ConfigHandler.conversionRateEu);
		//System.out.println("input amount eu " + amount);
		if (powerLevel > maxPowerLevel) { powerLevel = maxPowerLevel; }
		return amount;
	}
	
	@Optional.Method(modid="IC2")
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
		//System.out.println("new power level " + powerLevel);
		this.powerLevel = powerLevel;
		//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		//this.markDirty();
		//worldObj.ma
	}
}
