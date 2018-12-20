package configuration;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Set;

/**
 * Configuration class for the special attacks.
 * Implemented as Singleton
 */
public class SpecialAttackConfiguration extends AttackConfiguration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/specialattack.conf";
    private static SpecialAttackConfiguration instance = null;

    /**
     * Constructor
     * @param filename the filename of the configuration file
     */
    private SpecialAttackConfiguration(String filename) {
        super(filename);
    }

    /**
     * @return the instance of SpecialAttackConfiguration
     */
    public static SpecialAttackConfiguration getInstance() {
        if (instance == null) {
            instance = new SpecialAttackConfiguration(filename);
        }
        return instance;
    }

    /**
     * @param id the special attack id
     * @return the intro Animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getIntro(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"intro");
    }

    /**
     * @param id the special attack id
     * @return the icon Image
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Image getIcon(String id) throws NoSuchElementInConfigurationException, SlickException {
        String basepath = getConfiguration(id).get("base_folder").getAsString();
        String iconname = getConfiguration(id).get("icon").getAsString();
        return new Image(basepath+iconname);
    }

    /**
     * @return a set containing all the special attack ids
     */
    public Set<String> getSpecialAttackNames(){
        return getKeySet();
    }

    /**
     * @param id the special attack id
     * @return the special attack name
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public String getSpecialAttackName(String id) throws NoSuchElementInConfigurationException {
        return getConfiguration(id).get("name").getAsString();
    }

    /**
     * @param id the special attack id
     * @return the special attack description
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public String getSpecialAttackDescription(String id) throws NoSuchElementInConfigurationException {
        return getConfiguration(id).get("description").getAsString();
    }
}
