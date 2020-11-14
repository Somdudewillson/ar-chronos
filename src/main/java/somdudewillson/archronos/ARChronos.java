package somdudewillson.archronos;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import somdudewillson.archronos.boxes.DimPropertyBox;
import somdudewillson.archronos.config.ARChronosConfig;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;

@Mod(modid = ARChronos.MODID, name = ARChronos.NAME, version = ARChronos.VERSION,
	dependencies = "required-after:advancedrocketry;")
public class ARChronos
{
    public static final String MODID = "archronos";
    public static final String NAME = "AR Chronos Addon";
    public static final String VERSION = "1.12.2-1.0.0.0";
    /* MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH
     * 		MCVERSION
	 * Always matches the Minecraft version the mod is for.
	 * 		MAJORMOD
	 * Removing items, blocks, tile entities, etc.
	 * Changing or removing previously existing mechanics.
	 * Updating to a new Minecraft version.
	 * 		MAJORAPI
	 * Changing the order or variables of enums.
	 * Changing return types of methods.
	 * Removing public methods altogether.
	 * 		MINOR
	 * Adding items, blocks, tile entities, etc.
	 * Adding new mechanics.
	 * Deprecating public methods. (This is not a MAJORAPI increment since it doesn't break an API.)
	 * 		PATCH
	 * Bugfixes.
     */

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }
    
    @EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
    	DimensionManager galaxy = DimensionManager.getInstance();
		
		logger.info("Patching Advanced Rocketry Dimensions...");
		for (Integer dimID : galaxy.getRegisteredDimensions()) {
			DimensionProperties prop = galaxy.getDimensionProperties(dimID);
			
			if (!ARChronosConfig.orbital_period_override.containsKey(prop.getName())) {
				ARChronosConfig.orbital_period_override.put(prop.getName(), -1);
				logger.info("Added Dimension: '"+prop.getName()+"' to config.");
			}
			galaxy.setDimProperties(dimID, 
					new DimPropertyBox(prop));
			
			logger.info("Patched Dimension: '"+prop.getName()+"'");
		}
		logger.info(galaxy.getRegisteredDimensions().length+" Advanced Rocketry Dimensions patched.");
    }
}
