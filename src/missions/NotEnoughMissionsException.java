package missions;

/**
 * Raised if the number of possible missions are less than the one that the client asked to create
 */
public class NotEnoughMissionsException extends Exception{

    /**
     * Constructor
     * @param message a String
     */
    public NotEnoughMissionsException(String message){
        super(message);
    }
}
