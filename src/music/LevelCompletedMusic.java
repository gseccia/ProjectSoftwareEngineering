package music;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class LevelCompletedMusic {

    private static Sound track;

    public static Sound getLevelCompletedMusic() throws SlickException {
        if(track == null){
            track = new Sound(System.getProperty("user.dir") + "/resource/audio/transitions/levelCompleted.ogg");
        }
        return track;
    }

}


