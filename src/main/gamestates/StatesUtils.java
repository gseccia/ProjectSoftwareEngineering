package main.gamestates;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

/**
 * 
 * @author JBFourierous
 * This class represents a set of utilities for fonts managing in the various game states
 */

public class StatesUtils {
    @SuppressWarnings("unchecked")
    public static UnicodeFont initFont() {
    	java.awt.Font UIFont1;
    	org.newdawn.slick.UnicodeFont uniFont = null;
    	try{
    		UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
    				org.newdawn.slick.util.ResourceLoader.getResourceAsStream(
    						System.getProperty("user.dir") + "/resource/font/joystix_monospace.ttf"
    						));
    		UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 30.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 30.f is the size of your font

    		uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
    		uniFont.addAsciiGlyphs();
    		uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
    		uniFont.addAsciiGlyphs();
    		uniFont.loadGlyphs();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return uniFont;
    }
    
    public static void applyBorder(UnicodeFont uniFont, String s, int x, int y, Color c) {    	
    	//    	uniFont.drawString(x, y, text, col);
    	uniFont.drawString(ShiftWest(x, 2), ShiftNorth(y, 2), s, c);
    	uniFont.drawString(ShiftWest(x, 2), ShiftSouth(y, 2), s, c);
    	uniFont.drawString(ShiftEast(x, 2), ShiftNorth(y, 2), s, c);
    	uniFont.drawString(ShiftEast(x, 2), ShiftSouth(y, 2), s, c);
    }

    public static int ShiftNorth(int p, int distance) {
    	return (p - distance);
    }
    private static int ShiftSouth(int p, int distance) {
    	return (p + distance);
    }
    private static int ShiftEast(int p, int distance) {
    	return (p + distance);
    }
    private static int ShiftWest(int p, int distance) {
    	return (p - distance);
    }
    
    public static Image loadImage(String path) throws IOException
	{
	    BufferedImage bufferedImage = ImageIO.read(new File(path));
	    Texture texture = BufferedImageUtil.getTexture("", bufferedImage);
	    Image image = null;
		try {
			image = new Image(texture.getImageWidth(), texture.getImageHeight());
		    image.setTexture(texture);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return image; 
	}
}
