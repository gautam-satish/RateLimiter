package customExceptions;

public class UserDoesNotExistException extends Exception {
	public UserDoesNotExistException(String userId) {
        super("User " + userId + " does not exist!");
    }
}
