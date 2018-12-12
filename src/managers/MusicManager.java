package managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.LexerNoViableAltException;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import managers.observers.scoreboard.Observer;
import managers.observers.scoreboard.Subject;

public class MusicManager extends Observer{
	private static MusicManager instance;
	private Music menuMusic;
	private List<Music> gameMusic = new ArrayList<>();
	private Sound levelCompletedSound, ripSound;
	private Subject subject;
	
	private Music currentMusic;
	private Sound currentSound;
	private int indexLevel;
	
	
	private MusicManager(Subject s) {
		this.subject = s;
		this.subject.attach((MusicManager)this);
		try {
			levelCompletedSound = new Sound(System.getProperty("user.dir") + "/resource/audio/transitions/levelCompleted.ogg");
			ripSound = new Sound(System.getProperty("user.dir") + "/resource/audio/transitions/dead.ogg");
			menuMusic = (new Music(System.getProperty("user.dir") + "/resource/audio/menu/HorseSteppin.ogg"));
			Files.list(Paths.get(System.getProperty("user.dir") + "/resource/audio/oth/"))
	        	.filter(Files::isRegularFile)
	        	.forEach(music->{
					try {
						gameMusic.add(new Music(music.toString()));
					} catch (SlickException e) {
						e.printStackTrace();
					}
				});
//			gameMusic.add(new Music(System.getProperty("user.dir") + "/resource/audio/menu/HorseSteppin.ogg"));
		} catch (SlickException | IOException e) {
			e.printStackTrace();
		}
		// Init class utility fields
		this.currentMusic = menuMusic;
		this.currentSound = levelCompletedSound;
		this.indexLevel = 0;
	}

	public static MusicManager getInstance(Subject s) {
		if(instance == null)
			instance = new MusicManager(s);
		return instance;
	}

	@Override
	public void update() {
		switch(this.subject.getState()) {
			case 0:
//				menu
				if (currentMusic.playing()) currentMusic.stop();
				if (currentSound.playing()) currentSound.stop();
				this.menuMusic.loop(1.0f,  SoundStore.get().getMusicVolume() * 0.3f);
				this.currentMusic = this.menuMusic;
				break;
			case 1:
//				new level
				if (currentMusic.playing()) currentMusic.stop();
				if (currentSound.playing()) currentSound.stop();
				this.gameMusic.get(indexLevel).loop(1.0f,  SoundStore.get().getMusicVolume() * 0.3f);
				this.currentMusic = this.gameMusic.get(indexLevel);
				break;
			case 2:
//				level completed
				if (currentMusic.playing()) currentMusic.stop();
				if (currentSound.playing()) currentSound.stop();
				this.levelCompletedSound.loop(1.0f,  SoundStore.get().getMusicVolume() * 0.3f);
				this.currentSound = this.levelCompletedSound;
				if (this.indexLevel < gameMusic.size()) this.indexLevel += 1;
				else this.indexLevel = 0;
				break;
			case 3:
//				rip
				if (currentMusic.playing()) currentMusic.stop();
				if (currentSound.playing()) currentSound.stop();
				this.ripSound.loop(1.0f,  SoundStore.get().getMusicVolume() * 0.3f);
				this.currentSound = this.ripSound;
				this.indexLevel = 0;
				break;
			case 4:
//				pause enter
				System.out.println("Pause");
				if (currentMusic.playing()) currentMusic.pause();
				break;
			case 5:
//				pause resume
				System.out.println("Resume");
				if (!currentMusic.playing()) currentMusic.resume();
				break;
			default:
				break;
		}
	}

}
