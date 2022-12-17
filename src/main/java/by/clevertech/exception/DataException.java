package by.clevertech.exception;

public class DataException extends ClientException {

    public DataException() {
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}
