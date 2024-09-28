package main.Exceptions;

/**
 * An exception thrown if the player is out of money and cannot make a selected purchase.
*/

public class OutOfMoneyException extends IndexOutOfBoundsException{

    /**
     * Constructor.
    */
    public OutOfMoneyException(){
        super();
    }

    /**
     * Constructor with error message.
     * @param errorMessage      Error message.
    */
    public OutOfMoneyException(String errorMessage){
        super(errorMessage);
    }
}
