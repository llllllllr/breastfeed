package lllr.test.breast.util.exception;

public class StringException extends Exception {
    private String message;

    public StringException(){
        super();
        message = "Unknown Exception!";
    }

    public StringException(String message){this.message = message;}
}
