package music;

import org.newdawn.slick.Music;
import org.newdawn.slick.openal.SoundStore;

public class MusicManager extends Thread
{
	private Music musicToPlay;
	
    public MusicManager(Music musicToPlay) {
		this.musicToPlay = musicToPlay;
	}

	@Override
    public void run()
    {
		if(!this.musicToPlay.playing()){
			this.musicToPlay.loop(1.0f, SoundStore.get().getMusicVolume() * 0.3f);
		}
        while(true) {
//           System.out.println("Music started");
        }
    }
	
	public void end() {
		if(this.musicToPlay.playing()){
			this.musicToPlay.stop();
		}
	}

}