package main.java;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameMain{
	public final static int MENU = 0;
	
	
    public static void main(String args[]){

        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/lib/native/"+LWJGLUtil.getPlatformName());
        System.setProperty("net.java.games.input.librarypath",System.getProperty("user.dir")+"/lib/native/"+LWJGLUtil.getPlatformName());
       
		try {
			 AppGameContainer app = new AppGameContainer(new Level("UNI:ZA","guntan",2));
			 app.setDisplayMode(800, 700, false);
			 app.setShowFPS(true);
			 app.setTargetFrameRate(19);
		     app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }


}
