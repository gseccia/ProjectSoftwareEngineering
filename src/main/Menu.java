package main;

import java.awt.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
    private final int id = 0;
    private static Menu ourInstance = new Menu();

    private int playersChoice = 0;
    private static final int NOCHOICES = 4;
    private static final int START = 0;
    private static final int DEMO = 1;
    private static final int OPTIONS = 2;
    private static final int QUIT = 3;
    private String[] playersOptions = new String[NOCHOICES];
    private boolean exit = false;
    private Font font;
    private TrueTypeFont playersOptionsTTF, foo;
    private Color notChosen = new Color(153, 204, 255);


    public static Menu getInstance() {
        return ourInstance;
    }

    private Menu() {
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        font = new Font("TimesNewRoman", Font.BOLD, 40);
        playersOptionsTTF = new TrueTypeFont(font, true);
        font = new Font ("TimesNewRoman", Font.PLAIN, 20);
        foo = new TrueTypeFont(font, true);
        playersOptions[0] = "Start";
        playersOptions[1] = "Demo";
        playersOptions[2] = "Options";
        playersOptions[3] = "Quit";
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.renderPlayersOptions();
        if (exit) {
            gameContainer.exit();
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (playersChoice == (NOCHOICES - 1)) {
                playersChoice = 0;
            } else {
                playersChoice++;
            }
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            if (playersChoice == 0) {
                playersChoice = NOCHOICES - 1;
            } else {
                playersChoice--;
            }
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (playersChoice) {
                case QUIT:
                    exit = true;
                    break;
                case START:
                    stateBasedGame.enterState(1);
                    break;
                default:
                    break;

            }

        }
    }

    private void renderPlayersOptions(){
        for (int i = 0; i < NOCHOICES; i++) {
            if (playersChoice == i) {
                playersOptionsTTF.drawString(100, i * 50 + 200, playersOptions[i]);
            } else {
                playersOptionsTTF.drawString(100, i * 50 + 200, playersOptions[i], notChosen);
            }
        }
    }
}
