package exceptions;

public class CustomerUpdateIllegalException extends NotExistsException {
    public CustomerUpdateIllegalException(String message) {
        super(message);
    }
}
