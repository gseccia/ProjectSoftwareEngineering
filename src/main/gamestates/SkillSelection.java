package main.gamestates;

import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import managers.ResourceManager;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkillSelection extends BasicGameState {

    private final int ID = GameStates.SKILL_SELECTION.getState();
    private final String TITLE = "Choose your skill";

    private int index = 0;
    private List<Image> icons = new ArrayList<>();
    private List<String> titles = new ArrayList<>(), descriptions = new ArrayList<>(), IDs = new ArrayList<>();
    private List<Color> filters = new ArrayList<>();
    private Image background;
    private ResourceManager rs;
    private String currentTitle;
    private String currentDesc;
    private UnicodeFont uniFont, titleFont, descFont;

    private Color titleBorder = new Color(0, 255, 255);
    private Color color = new Color(0, 0, 0);
    private Color border = new Color(0, 255, 128);

    public SkillSelection(ResourceManager rs) {
        this.rs = rs;
        SpecialAttackConfiguration conf = SpecialAttackConfiguration.getInstance();
        try{
            for(String s : conf.getSpecialAttackNames()){
                icons.add(conf.getIcon(s));
                titles.add(conf.getSpecialAttackName(s));
                descriptions.add(conf.getSpecialAttackDescription(s));
                filters.add(Color.gray);
                IDs.add(s);
            }
            filters.set(0, Color.white);
            currentTitle = titles.get(0);
            currentDesc = descriptions.get(0);

        } catch (NoSuchElementInConfigurationException | SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        try {
            background = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/charSelect.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        uniFont = StatesUtils.initFont();
        uniFont = StatesUtils.changeSizeAndStyle(uniFont, 45f, java.awt.Font.PLAIN);

        titleFont = StatesUtils.initFont();
        titleFont = StatesUtils.changeSizeAndStyle(uniFont, 60f, java.awt.Font.PLAIN);

        descFont = StatesUtils.initFont();
        descFont = StatesUtils.changeSizeAndStyle(uniFont, 30f, java.awt.Font.PLAIN);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        StatesUtils.applyBorder(uniFont, TITLE, 10, 10, titleBorder);
        uniFont.drawString(10, 10, TITLE, color);
        float baseX = 10, baseY = 80;
        for(int i = 0; i<icons.size(); i++){
            icons.get(i).draw(baseX, baseY, filters.get(i));
            baseX += icons.get(i).getWidth() + 15;
        }

        StatesUtils.applyBorder(titleFont, currentTitle, 10, 130, border);
        titleFont.drawString(10, 130, currentTitle, color);

        StatesUtils.applyBorder(descFont, currentDesc, 10, 230, border);
        descFont.drawString(10, 230, currentDesc, color);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input in = gameContainer.getInput();
        if(in.isKeyPressed(Input.KEY_LEFT) && index > 0){
            filters.set(index, Color.gray);
            index--;
            filters.set(index, Color.white);
            currentTitle = titles.get(index);
            currentDesc = descriptions.get(index);
        }
        else if(in.isKeyPressed(Input.KEY_RIGHT) && index < icons.size()-1){
            filters.set(index, Color.gray);
            index++;
            filters.set(index, Color.white);
            currentTitle = titles.get(index);
            currentDesc = descriptions.get(index);
        }
        else if(in.isKeyPressed(Input.KEY_ENTER)) {
            rs.setState(1);
            ((main.Game)stateBasedGame).setUltra(IDs.get(index));
            ((main.Game)stateBasedGame).resetDifficulty();
            stateBasedGame.init(gameContainer);
            stateBasedGame.enterState(GameStates.STARTING_POINT.getState());
        }
        else if(in.isKeyPressed(Input.KEY_ESCAPE)) {
            stateBasedGame.enterState(GameStates.CHAR_SELECTION.getState());
        }
    }

    @Override
    public int getID() {
        return ID;
    }


}
