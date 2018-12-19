package main;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import managers.MusicManager;
import utils.Constants;

public class GameMain{
	public final static int MENU = 0;
	
    public static void main(String args[]){

        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/lib/native/"+LWJGLUtil.getPlatformName());
        System.setProperty("net.java.games.input.librarypath",System.getProperty("user.dir")+"/lib/native/"+LWJGLUtil.getPlatformName());
        
        
		try {
			AppGameContainer app = new AppGameContainer(new Game("UNI:ZA","vegeta"));
			app.setDisplayMode(800, 700, false);
			app.setShowFPS(false);
			app.setTargetFrameRate(Constants.framerate);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }


}
