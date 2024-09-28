package main.Exceptions;

/**
 * Exception thrown if the player enters an invalid name.
*/

public class InvalidNameException extends Exception{

    /**
     * Constructor.
    */
    public InvalidNameException(){
        super();
    }

    /**
     * Constructor.
     * @param message       Error message.
    */
    public InvalidNameException(String message){
        super(message);
    }

    /**
     * Constructor.
     * @param cause         Error cause.
     * @param message       Error message.
    */
    public InvalidNameException(Throwable cause, String message){
        super(message, cause);
    }
}
