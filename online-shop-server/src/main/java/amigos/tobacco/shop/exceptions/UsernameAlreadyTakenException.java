package amigos.tobacco.shop.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {

    public UsernameAlreadyTakenException(final String message) {
        super(message);
    }
}
