package managers.command;

import managers.MusicManager;
import managers.ResourceManager;

public class IncreaseVolumeCommand implements CommandInterface{
	private static IncreaseVolumeCommand instance = null;
	private MusicManager musicManager;
	
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
