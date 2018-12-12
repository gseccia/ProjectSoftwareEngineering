package main;

public class ResourceManager extends managers.observers.scoreboard.Subject{
	private static ResourceManager instance;
	
	private ResourceManager() {
	}
	
	public static ResourceManager getInstance() {
		if(instance == null) instance = new ResourceManager();
		return instance;
	}
	
	
}
