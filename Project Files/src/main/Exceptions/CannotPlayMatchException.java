package main.Exceptions;

import javax.naming.directory.InvalidAttributesException;

/**
 * Exception thrown if a match cannot be played.
*/

public class CannotPlayMatchException extends InvalidAttributesException{

    /**
     * Constructor.
    */
    public CannotPlayMatchException(){
        super();
    }

    /**
     * Constructor.
     * @param message   Error message.
    */
    public CannotPlayMatchException(String message){
        super(message);
    }
}
