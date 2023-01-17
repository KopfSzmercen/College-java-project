package app.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
    public ForbiddenException() {
        super("You do not have permission to do this.");
    }
}
