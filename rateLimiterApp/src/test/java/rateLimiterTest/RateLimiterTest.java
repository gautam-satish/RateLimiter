package rateLimiterTest;

import customExceptions.LimitException;
import customExceptions.UserDoesNotExistException;
import main.Main;
import services.RateLimiterService;

public class RateLimiterTest {

	public int sum(int a, int b) {
		return a + b;
	}
	
	//returns if any custom Exceptions are thrown or if the loop is complete
	public static String executeCalls(String user, String method, int calls) {
		String response = "Success/";
		int index = 0;
		try {
			for (int i = 0; i <= calls; i++) {
				index = i;
				RateLimiterService.validateUserRequest(user, method);
			}
		} catch (LimitException | UserDoesNotExistException e) {
			return e.getMessage() + "/" + index;
		}
		return response + index;
	}

	// returns only if the loop is complete or the validateUserRequest throws
	// LimitException with "Number of requests exceeded for" as error message
	public static String executeCallsWithDelay(String user, String method, int calls) {
		String response = "Success/";
		int index = 0;

		for (int i = 0; i <= calls; i++) {
			Main.wait(1000);
			try {
				RateLimiterService.validateUserRequest(user, method);
				index++;
			} catch (LimitException | UserDoesNotExistException e) {
				if (e.getMessage().contains("Number of requests exceeded for")) {
					return e.getMessage() + "/" + index;
				}
			}
		}
		return response + index;
	}

	// returns only if the loop is complete or the validateUserRequest throws
	// LimitException with "Cannot process request at this time for" as error message
	public static String executeCallsWithDelay1(String user, String method, int calls) {
		String response = "Success/";
		int index = 0;
		for (int i = 0; i <= calls; i++) {
			Main.wait(1000);
			try {
				RateLimiterService.validateUserRequest(user, method);
				index++;
			} catch (LimitException | UserDoesNotExistException e) {
				if (e.getMessage().contains("Cannot process request at this time for")) {
					return e.getMessage() + "/" + index;
				}
			}
		}
		return response + index;
	}
}
