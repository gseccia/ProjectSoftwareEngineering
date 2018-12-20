package managers.command;

import managers.MusicManager;
import managers.ResourceManager;

/*
 * Concrete Command
 * Performs the reset of the volume to the default value
 */
public class ResetVolumeCommand implements CommandInterface{
	private static ResetVolumeCommand instance = null;
	private MusicManager musicManager;
	
	/*
	 * Singleton since the command must be unique for the game
	 */
	private ResetVolumeCommand() {
		musicManager = MusicManager.getInstance(ResourceManager.getInstance());
	}
	
	public static ResetVolumeCommand getInstance() {
		if (instance == null) instance = new ResetVolumeCommand();
		return instance;
	}
	
	@Override
	public void execute() {
		musicManager.setVolume(musicManager.DEFAULT_VOLUME);
	}
}
