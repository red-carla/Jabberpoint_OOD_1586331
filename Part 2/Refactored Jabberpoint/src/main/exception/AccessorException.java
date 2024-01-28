package main.exception;

/**
 * Exception thrown when an error occurs in the Accessor class
 *
 * @version 1.61 2024/01/10 Carla Redmond
 */
public class AccessorException extends Exception {
    /**
     * Constructor for AccessorException
     * @param message error message to display
     */
    public AccessorException(String message) {
        super(message);
    }

}
