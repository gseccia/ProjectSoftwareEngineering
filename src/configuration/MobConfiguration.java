package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.Set;


public abstract class MobConfiguration extends Configuration{
    private JsonObject configuration;

    protected MobConfiguration(String filename){
        this.configuration = super.uploadConfiguration(filename);
    }

    public int getHp(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("hp") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("hp").getAsInt();
    }

    public int getHeight(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("height") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("height").getAsInt();
    }

    public int getWidth(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("width") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("width").getAsInt();
    }

    public int getAttack(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("attack") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("attack").getAsInt();
    }

    public Animation getFaceUp(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"up");
    }

    public Animation getFaceDown(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"down");
    }

    public Animation getFaceLeft(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"left");
    }

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

    public Animation getFaceStill(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"still");
    }
    
    public Animation getFaceStillUp(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillUp");
    }
    
    public Animation getFaceStillDown(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillDown");
    }

    public Animation getFaceStillLeft(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillLeft");
    }
    
    public Animation getFaceStillRight(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"stillRight");
    }
    
    public Animation getAttackLeft(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackLeft");
    }
    
    public Animation getAttackRight(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackRight");
    }
    
    public Animation getAttackUp(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackUp");
    }
    
    public Animation getAttackDown(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"attackDown");
    }

    protected Set<String> getIdSet(){
        return configuration.keySet();
    }
}
