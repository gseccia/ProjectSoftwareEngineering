package elements;

/**
 * Signals that a value is negative but it should be positive.
 */
public class NotPositiveValueException extends Exception{
	
    public NotPositiveValueException(String message){
        super(message);
    }

}
