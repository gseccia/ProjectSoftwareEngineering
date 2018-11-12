/**
 * This class represent all the mobile objects in the game, that have at least four different animations
 * (for the four directions) a HP value and an attack value
 */
public class Mob extends InteractiveElement{

    /**
     * hp are the current hp, maxHP the total hp
     */
    private int hp, maxHp, attackDamage;
    private HashMap<String, Animation> faces;

    protected Mob(int hp, int attackDamage, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown) {
        super(faceUp);
        this.hp = hp;
        this.maxHp = hp
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown);
    }

    protected Mob(int hp, int attackDamage, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, int width, int height) {
        super(faceUp, width, height);
        this.hp = hp;
        this.maxHp = hp;
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown);
    }

    /**
     * Creates a map with the animations. Subclasses can override to add more faces
     * @param faceLeft
     * @param faceRight
     * @param faceUp
     * @param faceDown
     */
    protected void generateMap(Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown){
        faces.put("left", faceLeft);
        faces.put("right", faceRight);
        faces.put("up", faceUp);
        faces.put("down", faceDown);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Reduces the current hp value of the client by an amount
     * @param amount
     */
    public void damage(int amount){
        this.hp -= amount;
    }

    /**
     * Changes the current animation with the up one
     */
    public void goUp(void){
        setCurrent(faces.get("up"));
    }

    /**
     * Changes the current animation with the down one
     */
    public void goDown(void){
        setCurrent(faces.get("down"));
    }

    /**
     * Changes the current animation with the right one
     */
    public void goRight(void){
        setCurrent(faces.get("right"));
    }

    /**
     * Changes the current animation with the left one
     */
    public void goLeft(void){
        setCurrent(faces.get("left"));
    }
}