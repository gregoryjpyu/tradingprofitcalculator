package exceptions;

// represents an exception thrown when response is invalid
public class InvalidResponseException extends Exception {
    public InvalidResponseException(String msg) {
        super(msg);
    }
}
