package blocks;

import main.GameStates;
import missions.Mission;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class Pause extends BasicGameState {

    private final int id = GameStates.PAUSE.getState();
    private Image background;

    private static int originState;
    private static Mission mission;
    private static Pause instance;

    public static Pause getInstance(){
        if(instance == null){
            instance = new Pause();
        }
        return instance;
    }

    private Pause() {
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.background = new Image(System.getProperty("user.dir") + "/resource/textures/screens/pause.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("GAME PAUSED", 240,10);
        background.draw(0, 45);
        graphics.setColor(Color.black);
        graphics.drawString(mission.toString(), 120, 60);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if(input.isKeyPressed(Input.KEY_P)){
            stateBasedGame.enterState(originState);
        }

    }

    @Override
    public int getID() {
        return id;
    }

    public static void setOriginState(int state){
        originState = state;
    }

    public static void setMission(Mission mission) {
        Pause.mission = mission;
    }
}
