package lllr.test.breast.util.exception;

public class StringException extends Exception {

    public StringException() {
        super("Unknown Exception!");
    }

    public StringException(String message) {
        super(message);
    }
}
