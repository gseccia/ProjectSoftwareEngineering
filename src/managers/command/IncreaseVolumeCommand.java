package managers.command;

import managers.MusicManager;
import managers.ResourceManager;

/*
 * Concrete Command
 * Implements the command to increase the volume of the game of a unit
 * that is 0.01 on a scale from 0 to 1
 */
public class IncreaseVolumeCommand implements CommandInterface{
	private static IncreaseVolumeCommand instance = null;
	private MusicManager musicManager;
	
	/*
	 * Singleton since the command must be unique for the game
	 */
	private IncreaseVolumeCommand() {
		musicManager = MusicManager.getInstance(ResourceManager.getInstance());
	}
	
	public static IncreaseVolumeCommand getInstance() {
		if (instance == null) instance = new IncreaseVolumeCommand();
		return instance;
	}
	
	@Override
	public void execute() {
		float updatedValue = musicManager.getVolume() + 0.01f;
		if (updatedValue >= 1.0f) musicManager.setVolume(1f);
		else musicManager.setVolume(updatedValue);
//		System.out.println(updatedValue);
	}

}
