import org.junit.*;

import org.newdawn.slick.*;
import org.lwjgl.LWJGLUtil;

public class GameMain {
	
	
	
    public static void main(String args[]){

        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/native/"+LWJGLUtil.getPlatformName());
        System.setProperty("net.java.games.input.librarypath",System.getProperty("user.dir")+"/native/"+LWJGLUtil.getPlatformName());
       
        
		try {
			 AppGameContainer app = new AppGameContainer(new Game("UNI:ZA"));
			 app.setDisplayMode(800, 600, false);
		     app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }
}
