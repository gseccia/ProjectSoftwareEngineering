package managers.command;

import main.ResourceManager;
import managers.MusicManager;

public class DecreaseVolumeCommand implements CommandInterface {
	private static DecreaseVolumeCommand instance = null;
	private MusicManager musicManager;
	
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
