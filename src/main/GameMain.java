package main;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


import utils.Constants;

public class GameMain{
	public final static int MENU = 0;
	
	
    public static void main(String args[]){

        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/lib/native/"+LWJGLUtil.getPlatformName());
        System.setProperty("net.java.games.input.librarypath",System.getProperty("user.dir")+"/lib/native/"+LWJGLUtil.getPlatformName());
       
		try {
			//			 AppGameContainer app = new AppGameContainer(main.Game.getGameInstance("UNI:ZA","guntan"));
//			System.out.println("prima game");
			AppGameContainer app = new AppGameContainer(new Game("UNI:ZA","guntan"));
//			System.out.println("dopo game");
			app.setDisplayMode(800, 700, false);
			app.setShowFPS(true);
			app.setTargetFrameRate(Constants.framerate);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }


}
