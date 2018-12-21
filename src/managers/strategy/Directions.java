package managers.strategy;

import org.newdawn.slick.Input;


/**
 * Provide an interface between command and keys
 */
public interface Directions {

    int UP = Input.KEY_W;
    int DOWN = Input.KEY_S;
    int LEFT = Input.KEY_A;
    int RIGHT = Input.KEY_D;
    int KEY_M = Input.KEY_M;
    int ATTACK1_M = Input.KEY_M;
    int ATTACK1_Z = Input.KEY_Z;
    int ATTACK2_SPACE = Input.KEY_SPACE;
    int ATTACK2_X = Input.KEY_X;
    int UP_ARROW = Input.KEY_UP;
    int DOWN_ARROW = Input.KEY_DOWN;
    int LEFT_ARROW = Input.KEY_LEFT;
    int RIGHT_ARROW = Input.KEY_RIGHT;
}
