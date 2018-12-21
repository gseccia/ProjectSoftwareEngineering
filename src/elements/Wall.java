package elements;

import org.newdawn.slick.geom.Rectangle;

/**
 * Gives a represntation of a wall
 */
public class Wall extends Rectangle {

    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
    }


    public boolean intersects(Rectangle r){
        //System.out.println(x + " " + (r.getX()+r.getWidth()));
        //System.out.println((x+width)+ " " + r.getX());
        if(super.intersects(r)){
            if(r.getY() == (y+height)){
                return false;
            }
            else if((r.getY()+r.getHeight()) == y){
                return false;
            }
            else if(r.getX() == (x+width)){
                return false;
            }
            else if((r.getX()+r.getWidth()) == x){
                return false;
            }
            else{
                return true;
            }
        }
        return false;
    }
}
