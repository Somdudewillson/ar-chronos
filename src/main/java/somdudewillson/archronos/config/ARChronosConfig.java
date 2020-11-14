package somdudewillson.archronos.config;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import somdudewillson.archronos.ARChronos;

@Config(modid = ARChronos.MODID, name = "ARChronos", category = "general")
public class ARChronosConfig {
	@Config.Comment({"Specify the desired orbital period, in ticks.",
	"If set to -1, the orbital period will be calculated realistically from orbital distance."})
	public static Map<String, Integer> orbital_period_override = new HashMap<>();

	
	@Mod.EventBusSubscriber(modid = ARChronos.MODID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(ARChronos.MODID)) {
				ConfigManager.sync(ARChronos.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
