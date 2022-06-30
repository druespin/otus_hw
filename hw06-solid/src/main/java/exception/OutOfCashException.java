package exception;

public class OutOfCashException extends RuntimeException {

    public OutOfCashException(String message) {
        super(message);
    }
}
