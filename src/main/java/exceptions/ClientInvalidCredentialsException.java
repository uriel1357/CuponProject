package exceptions;

public class ClientInvalidCredentialsException extends Exception {
    public ClientInvalidCredentialsException(String message){
        super(message);
    }
}
