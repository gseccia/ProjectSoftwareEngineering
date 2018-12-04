package music;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class BgMusic {

    private static Music bgMusic;

    public static Music getBgMusic() throws SlickException {
        if(bgMusic == null){
            bgMusic = new Music(System.getProperty("user.dir") + "/resource/audio/bg/bg.ogg");
        }
        return bgMusic;
    }

}
