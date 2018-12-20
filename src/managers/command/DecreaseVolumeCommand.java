package managers.command;

import managers.MusicManager;
import managers.ResourceManager;

/**
 * Concrete Command
 * Implements the command to decrease the volume of the game of a unit
 * that is 0.01 on a scale from 0 to 1
 * 
 * @author Ilaria
 */
public class DecreaseVolumeCommand implements CommandInterface {
	private static DecreaseVolumeCommand instance = null;
	private MusicManager musicManager;
	
	/*
	 * Singleton since the command must be unique for the game
	 */
	private DecreaseVolumeCommand() {
		musicManager = MusicManager.getInstance(ResourceManager.getInstance());
	}
	
	public static DecreaseVolumeCommand getInstance() {
		if (instance == null) instance = new DecreaseVolumeCommand();
		return instance;
	}
	
	@Override
	public void execute() {
		float updatedValue = musicManager.getVolume() - 0.01f;
		if (updatedValue <= 0.0f) musicManager.setVolume(0.0f);
		else musicManager.setVolume(updatedValue);
//		System.out.println(updatedValue);
	}
}
