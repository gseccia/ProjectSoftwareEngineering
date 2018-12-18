package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpecialAttackConfiguration extends AttackConfiguration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/specialattack.conf";
    private static SpecialAttackConfiguration instance = null;

    public SpecialAttackConfiguration(String filename) {
        super(filename);
    }

    public static SpecialAttackConfiguration getInstance() {
        if (instance == null) {
            instance = new SpecialAttackConfiguration(filename);
        }
        return instance;
    }

    public Animation getIntro(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"intro");
    }

    public Image getIcon(String id) throws NoSuchElementInConfigurationException, SlickException {
        String basepath = getConfiguration(id).get("base_folder").getAsString();
        String iconname = getConfiguration(id).get("icon").getAsString();
        return new Image(basepath+iconname);
    }
}
