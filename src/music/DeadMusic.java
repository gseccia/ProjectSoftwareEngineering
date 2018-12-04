package music;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class DeadMusic {

    private static Sound track;

    public static Sound getDeadMusic() throws SlickException {
        if(track == null){
            track = new Sound(System.getProperty("user.dir") + "/resource/audio/transitions/dead.ogg");
        }
        return track;
    }


}
