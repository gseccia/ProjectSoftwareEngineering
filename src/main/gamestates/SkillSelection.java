package main.gamestates;

import managers.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;

import java.io.IOException;

public class SkillSelection extends BasicGameState {
	private final int ID = GameStates.SKILL_SELECTION.getState();
    
	private static final int NOCHOICES = 3;
    private int playersChoice = 0;
    private UnicodeFont uniFont;
    private String utly1Desc, utly2Desc, utly3Desc;
    private String name1, name2 , name3;
    private Image background, ulty1Img, ulty2Img, ulty3Img;
    private ResourceManager rs;
    private Rectangle ulty1, ulty1Border;
    private Rectangle ulty2, ulty2Border;
    private Rectangle ulty3, ulty3Border;
    private Rectangle description, descriptionBorder;
    
    private Color selectedBorder;
	private Color selectedText;
	
    
    public SkillSelection(ResourceManager rs) {
        this.rs = rs;
        this.ulty1 = new Rectangle(50, 153, 200, 100);
        this.ulty2 = new Rectangle(300, 153, 200, 100);
        this.ulty3 = new Rectangle(550, 153, 200, 100);
        this.ulty1Border = new Rectangle(47, 150, 206, 106);
        this.ulty2Border = new Rectangle(297, 150, 206, 106);
        this.ulty3Border = new Rectangle(547, 150, 206, 106);
        this.description = new Rectangle(50, 303, 700, 350);
        this.descriptionBorder = new Rectangle(47, 300, 706, 356);
        this.utly1Desc = "Summon a warrior spirit \nthat will defeat all the \nenemies near you.\n\n\nRange: short\nDamage: exteme\nCooldown: 15 s";
        this.utly2Desc = "Fire a powerful laser \nthat annihilates \neverything in it's way.\n\n\nRange: linear\nDamage: 300\nCooldown : 10 s";
        this.utly3Desc = "INFINITY UNLIMITED FLAME!\nOpen the gates of hell \nand burn anything that\nstands in your way.\n\nRange: total\nDamage: 100\nCooldown: 7 s";
        this.name1 = "horahora";
        this.name2 = "sparagmos";
        this.name3 = "iuf";
        this.selectedBorder = new Color(0, 255, 255);
        this.selectedText = new Color(0, 0, 0);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        try {
            this.background = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/ultySelect.png");
            this.ulty1Img = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/attack/horahora/icon.png");
            this.ulty2Img = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/attack/sparagmos/icon.png");
            this.ulty3Img = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/attack/iuf/icon.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        uniFont = StatesUtils.initFont();
        uniFont = StatesUtils.changeSizeAndStyle(uniFont, 34f, java.awt.Font.PLAIN);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if(stateBasedGame.getCurrentStateID() == this.ID) {
        	background.draw(0,0);
        	graphics.setColor(new Color(105,2,2));
        	graphics.draw(ulty1Border);
        	graphics.fillRect(ulty1Border.getX(), ulty1Border.getY(), ulty1Border.getWidth(), ulty1Border.getHeight());
        	graphics.draw(ulty2Border);
        	graphics.fillRect(ulty2Border.getX(), ulty2Border.getY(), ulty2Border.getWidth(), ulty2Border.getHeight());
        	graphics.draw(ulty3Border);
        	graphics.fillRect(ulty3Border.getX(), ulty3Border.getY(), ulty3Border.getWidth(), ulty3Border.getHeight());
			graphics.draw(descriptionBorder);
        	graphics.fillRect(descriptionBorder.getX(), descriptionBorder.getY(), descriptionBorder.getWidth(), descriptionBorder.getHeight());
        	graphics.setColor(new Color(201, 2, 2));
        	graphics.draw(ulty1);
        	graphics.fillRect(ulty1.getX(), ulty1.getY(), ulty1.getWidth(), ulty1.getHeight());
        	graphics.draw(ulty2);
        	graphics.fillRect(ulty2.getX(), ulty2.getY(), ulty2.getWidth(), ulty2.getHeight());
        	graphics.draw(ulty3);
        	graphics.fillRect(ulty3.getX(), ulty3.getY(), ulty3.getWidth(), ulty3.getHeight());
			graphics.draw(description);
        	graphics.fillRect(description.getX(), description.getY(), description.getWidth(), description.getHeight());
        	this.ulty1Img.draw(this.ulty1.getX() + this.ulty1.getWidth()/2 -this.ulty1Img.getWidth()/2,183);
        	this.ulty2Img.draw(this.ulty2.getX() + this.ulty2.getWidth()/2 -this.ulty2Img.getWidth()/2,183);
        	this.ulty3Img.draw(this.ulty3.getX() + this.ulty3.getWidth()/2 -this.ulty3Img.getWidth()/2,183);
        	renderultySelected(gameContainer, graphics);
        }
        else {
        	uniFont.destroy();
        }
    }
    
    private void renderultySelected(GameContainer gc, Graphics g) {
    	if (playersChoice == 0) {
    		g.setColor(selectedBorder);
    		g.draw(ulty1Border);
    		g.fillRect(ulty1Border.getX(), ulty1Border.getY(), ulty1Border.getWidth(), ulty1Border.getHeight());
    		g.setColor(Color.black);
    		g.draw(ulty1);
    		g.fillRect(ulty1.getX(), ulty1.getY(), ulty1.getWidth(), ulty1.getHeight());
    		this.ulty1Img.draw(this.ulty1.getX() + this.ulty1.getWidth()/2 -this.ulty1Img.getWidth()/2, this.ulty1.getY() + this.ulty1.getHeight()/2 -this.ulty1Img.getHeight()/2);
    		uniFont.drawString(50, 303, utly1Desc, selectedText);
    	}else if (playersChoice == 1) {
    		g.setColor(selectedBorder);
    		g.draw(ulty2Border);
    		g.fillRect(ulty2Border.getX(), ulty2Border.getY(), ulty2Border.getWidth(), ulty2Border.getHeight());
    		g.setColor(Color.black);
    		g.draw(ulty2);
    		g.fillRect(ulty2.getX(), ulty2.getY(), ulty2.getWidth(), ulty2.getHeight());
    		this.ulty2Img.draw(this.ulty2.getX() + this.ulty2.getWidth()/2 -this.ulty2Img.getWidth()/2, this.ulty1.getY() + this.ulty1.getHeight()/2 -this.ulty1Img.getHeight()/2);
    		uniFont.drawString(50, 303, utly2Desc, selectedText);
    	}else {
    		g.setColor(selectedBorder);
    		g.draw(ulty3Border);
    		g.fillRect(ulty3Border.getX(), ulty3Border.getY(), ulty3Border.getWidth(), ulty3Border.getHeight());
    		g.setColor(Color.black);
    		g.draw(ulty3);
    		g.fillRect(ulty3.getX(), ulty3.getY(), ulty3.getWidth(), ulty3.getHeight());
    		this.ulty3Img.draw(this.ulty3.getX() + this.ulty3.getWidth()/2 -this.ulty3Img.getWidth()/2, this.ulty1.getY() + this.ulty1.getHeight()/2 -this.ulty1Img.getHeight()/2);
    		uniFont.drawString(50, 303, utly3Desc, selectedText);
    	}
    }
    
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		Input input = gameContainer.getInput();
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
        	if (playersChoice == (NOCHOICES - 1)){
				playersChoice = 0;
			}else{
				playersChoice++;
			}
        }
        if (input.isKeyPressed(Input.KEY_LEFT)) {
        	if (playersChoice == 0){
				playersChoice = NOCHOICES - 1;
			}else{
				playersChoice--;
			}
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
        	rs.setState(1);
            ((main.Game)stateBasedGame).setUltra(getUlty(playersChoice));
            ((main.Game)stateBasedGame).resetDifficulty();
            stateBasedGame.init(gameContainer);
            stateBasedGame.enterState(GameStates.STARTING_POINT.getState());
        }
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
        	stateBasedGame.enterState(GameStates.CHAR_SELECTION.getState());
        }
    }

    private String getUlty(int playersChoice) {
    	if (playersChoice == 0) {
    		return this.name1;
    	}else if (playersChoice == 1) {
    		return this.name2;
    	}else {
    		return this.name3;
    	}
    }

    @Override
    public int getID() {
        return ID;
    }


}