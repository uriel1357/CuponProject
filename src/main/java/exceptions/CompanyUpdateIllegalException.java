package exceptions;

public class CompanyUpdateIllegalException extends NotExistsException {
    public CompanyUpdateIllegalException(String message){
        super(message);
    }
}
