package exceptions;

public class CompanyNotExistsException extends NotExistsException {
    public CompanyNotExistsException(String message) {
        super(message);
    }
}
