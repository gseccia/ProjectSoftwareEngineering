package managers.command;

import main.ResourceManager;
import managers.MusicManager;

public class ResetVolumeCommand implements CommandInterface{
	private static ResetVolumeCommand instance = null;
	private MusicManager musicManager;
	
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
