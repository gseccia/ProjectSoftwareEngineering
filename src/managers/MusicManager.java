package managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import managers.observers.Observer;
import managers.observers.Subject;

public class MusicManager extends Observer{
	private static MusicManager instance;
	private Music menuMusic;
	private List<Music> gameMusic = new ArrayList<>();
	private Sound levelCompletedSound, ripSound;
	private Subject subject;
	public final float DEFAULT_VOLUME = 0.3f;
	
	private Music currentMusic;
	private Sound currentSound;
	private int indexLevel;
	private float volume;
	
	
	private MusicManager(Subject s) {
		this.subject = s;
		this.subject.attach(this);
		try {
			levelCompletedSound = new Sound(System.getProperty("user.dir") + "/resource/audio/transitions/levelCompleted.ogg");
			ripSound = new Sound(System.getProperty("user.dir") + "/resource/audio/transitions/dead.ogg");
			menuMusic = (new Music(System.getProperty("user.dir") + "/resource/audio/menu/HorseSteppin.ogg", true));
//			Files.list(Paths.get(System.getProperty("user.dir") + "/resource/audio/oth/"))
//	        	.filter(Files::isRegularFile)
//	        	.forEach(music->{
//					try {
//						gameMusic.add(new Music(music.toString()));
//					} catch (SlickException e) {
//						e.printStackTrace();
//					}
//				});
//			gameMusic.add(new Music(System.getProperty("user.dir") + "/resource/audio/menu/HorseSteppin.ogg", true));
		} catch (SlickException  e) {
			e.printStackTrace();
		}
		// Init class utility fields
		this.currentMusic = menuMusic;
		this.currentSound = levelCompletedSound;
		this.indexLevel = 0;
		this.volume = SoundStore.get().getMusicVolume() * DEFAULT_VOLUME;
		loadMusic();
	}
	
	public void loadMusic() {
		Thread loader = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Files.list(Paths.get(System.getProperty("user.dir") + "/resource/audio/oth/"))
					.filter(Files::isRegularFile)
					.forEach(music->{
						try {
							gameMusic.add(new Music(music.toString()));
						} catch (SlickException e) {
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}); 
		loader.start();
	}

	public static MusicManager getInstance(Subject s) {
		if(instance == null)
			instance = new MusicManager(s);
		return instance;
	}

	float pos;
	@Override
	public void update() {
		// TODO PEZZA PAUSA
		if(this.subject.getState()==-1) {
			if (currentMusic.playing()) {
				pos = currentMusic.getPosition();
				currentMusic.pause();
			}
			else {
				currentMusic.setPosition(pos);
				currentMusic.loop(1.0f,  this.volume);
			}
			return;
		}
		if (currentMusic.playing()) currentMusic.stop();
		if (currentSound.playing()) currentSound.stop();
		switch(this.subject.getState()) {
			case 0:
//				menu
				this.menuMusic.loop(1.0f,  this.volume);
				this.currentMusic = this.menuMusic;
				break;
			case 1:
//				new level
				this.gameMusic.get(indexLevel).loop(1.0f, this.volume);
				this.currentMusic = this.gameMusic.get(indexLevel);
				break;
			case 2:
//				level completed
				this.levelCompletedSound.loop(1.0f,  this.volume);
				this.currentSound = this.levelCompletedSound;
				if (this.indexLevel < gameMusic.size()) this.indexLevel += 1;
				else this.indexLevel = 0;
				break;
			case 3:
//				rip
				this.ripSound.loop(1.0f, this.volume);
				this.currentSound = this.ripSound;
				this.indexLevel = 0;
				break;
			default:
				break;
		}
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
		if (currentMusic.playing()) currentMusic.setVolume(volume);
	}
	

}
