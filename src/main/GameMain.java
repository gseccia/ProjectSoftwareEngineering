package main;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import utils.Constants;

public class GameMain{

    public static void main(String[] args){

        System.setProperty("org.lwjgl.librarypath",
        		System.getProperty("user.dir")+File.separator+
        		"lib"+File.separator+"native"+File.separator
        		+LWJGLUtil.getPlatformName());
        
        System.setProperty("net.java.games.input.librarypath",
        		System.getProperty("user.dir")+File.separator+
        		"lib"+File.separator+"native"+File.separator+LWJGLUtil.getPlatformName());
//        System.setProperty("java.library.path",
//        		System.getProperty("user.dir")+
//        		"\\Lib\\native\\"+LWJGLUtil.getPlatformName());
        System.out.println(System.getProperty("org.lwjgl.librarypath"));
        
		try {
			AppGameContainer app = new AppGameContainer(new Game("UNI:ZA"));
			app.setDisplayMode(800, 700, false);
			app.setShowFPS(false);
			app.setTargetFrameRate(Constants.framerate);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }


}
