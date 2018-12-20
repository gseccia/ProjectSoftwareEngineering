package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.Set;

/**
 * Configuration class for the mobs.
 * Implemented as Singleton
 */
public abstract class MobConfiguration extends Configuration{
    private JsonObject configuration;

    /**
     * Constructor
     * @param filename the filename of the configuration file
     */
    MobConfiguration(String filename){
        this.configuration = super.uploadConfiguration(filename);
    }

    /**
     * @param id the mob id
     * @return the mob hp
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getHp(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("hp") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("hp").getAsInt();
    }

    /**
     * @param id the mob id
     * @return the mob height
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getHeight(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("height") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("height").getAsInt();
    }

    /**
     * @param id the mob id
     * @return the mob width
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getWidth(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("width") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("width").getAsInt();
    }

    /**
     * @param id the mob id
     * @return the mob attack
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getAttack(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("attack") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("attack").getAsInt();
    }

    /**
     * @param id the mob id
     * @return the walk up animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceUp(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"up");
    }

    /**
     * @param id the mob id
     * @return the walk down animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceDown(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"down");
    }

    /**
     * @param id the mob id
     * @return the walk left animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceLeft(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"left");
    }

    /**
     * @param id the mob id
     * @return the walk right animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceRight(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"right");
    }


    @Override
    protected JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException {
        if(configuration.get(id) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return this.configuration.getAsJsonObject(id);
    }

    /**
     * @param id the mob id
     * @return the still animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceStill(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"still");
    }

    /**
     * @param id the mob id
     * @return the still up animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceStillUp(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillUp");
    }

    /**
     * @param id the mob id
     * @return the still down animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceStillDown(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillDown");
    }

    /**
     * @param id the mob id
     * @return the still left animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceStillLeft(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillLeft");
    }

    /**
     * @param id the mob id
     * @return the still right animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getFaceStillRight(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillRight");
    }

    /**
     * @param id the mob id
     * @return the attack left animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getAttackLeft(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackLeft");
    }

    /**
     * @param id the mob id
     * @return the attack right animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getAttackRight(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackRight");
    }

    /**
     * @param id the mob id
     * @return the attack up animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getAttackUp(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackUp");
    }

    /**
     * @param id the mob id
     * @return the attack down animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getAttackDown(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackDown");
    }

    /**
     * @return a set containing all the mob's ids
     */
    Set<String> getIdSet(){
        return configuration.keySet();
    }
}
