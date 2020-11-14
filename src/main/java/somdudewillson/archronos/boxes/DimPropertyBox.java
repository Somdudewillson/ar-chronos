package somdudewillson.archronos.boxes;

import net.minecraft.nbt.NBTTagCompound;
import somdudewillson.archronos.config.ARChronosConfig;
import zmaster587.advancedRocketry.AdvancedRocketry;
import zmaster587.advancedRocketry.dimension.DimensionProperties;

public class DimPropertyBox extends DimensionProperties {
	//1 "AR dist unit" = 1496000 km (If sun->planet)
	//1 "AR dist unit" = 2562.68 km (If planet->satellite)
	
	private static final int SOLAR_K = 76737600;//(sun->planet)
	private static final int LUNAR_K = 124416;//(planet->satellite)
	
	private double orbitThetaDeltaPerTick;
	
	public DimPropertyBox(DimensionProperties original) {
		super(original.getId(),original.getName());
		
		NBTTagCompound oldData = new NBTTagCompound();
		original.writeToNBT(oldData);
		super.readFromNBT(oldData);
		
		updateOrbitalDelta();
	}
	
	private void updateOrbitalDelta() {
		if (ARChronosConfig.orbital_period_override.containsKey(super.getName())
				&& ARChronosConfig.orbital_period_override.get(super.getName())>0) {
			orbitThetaDeltaPerTick = (2*Math.PI)/ARChronosConfig.orbital_period_override.get(super.getName());
		} else {
			//Calculate orbital delta from orbital period, which is calculated via Kepler's third law
			int k = super.isMoon() ? LUNAR_K : SOLAR_K;
			orbitThetaDeltaPerTick = (2*Math.PI)/(Math.pow(super.getOrbitalDist(), 3/2)*Math.sqrt(k));
		}
	}
	
	@Override
	public void updateOrbit() {
		super.prevOrbitalTheta = super.orbitTheta;
		super.orbitTheta = (AdvancedRocketry.proxy.getWorldTimeUniversal(0)*orbitThetaDeltaPerTick) % (2*Math.PI);
	}
}
