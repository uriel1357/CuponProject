package exceptions;

public class CompanyAlreadyExistsException extends Exception {
    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
