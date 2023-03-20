package services;

import java.time.LocalTime;

import customExceptions.LimitException;
import customExceptions.UserDoesNotExistException;
import pojo.Api;
import pojo.User;
import utils.UserGenerator;

public class RateLimiterService {

	/*
	 * This method gets the userObj from the passed userId and retrieves the
	 * mentioned API properties (availableCalls and the time window). 1) Checks if
	 * the number of requests has not exceeded the available number of calls. if
	 * false, an error message is set mentioning that the number of requests have
	 * exceeded. 2) Checks if the request is taking place in the specified time
	 * window. if false, an error message is set mentioning that the request cannot
	 * be made at this time. The number of calls made is set to 0 since the next
	 * window is assumed to be scheduled after 23 hours. 3) If both are true, the
	 * status is set to true and the number of requests made for the specific API is
	 * increased by 1. if either of the above condition fails, an
	 * exception(LimitException) is thrown with the appropriate error message. 4) If
	 * the user is not present in the system, UserNotFoundException is thrown.
	 */	
	public static boolean validateUserRequest(String userId, String api)
			throws LimitException, UserDoesNotExistException {
		
		boolean status = false;
		String message = "";
		User userObj = UserGenerator.users.get(userId);

		if (userObj != null) {
			Api apiObj = userObj.getApiData().get(api);
			LocalTime currentTime = LocalTime.now();

			// request exceed check
			boolean callsExpired = true;
			if (apiObj.getCallsMade() < apiObj.getAvailableCalls()) {
				callsExpired = false;
			} else {
				message = "Number of requests exceeded for " + userId;
			}

			// time window check
			boolean timeExpired = true;
			if (currentTime.isAfter(apiObj.getStartTime()) && currentTime.isBefore(apiObj.getEndTime())) {
				timeExpired = false;
			} else {
				apiObj.setCallsMade(0);
				message = "Cannot process request at this time for " + userId;
			}

			// overall check
			if (!callsExpired && !timeExpired) {
				apiObj.setCallsMade(apiObj.getCallsMade() + 1);
				status = true;
			}
			
			if (!status) {
				throw new LimitException(message);
			}
		} else {
			throw new UserDoesNotExistException(userId);
		}
		return status;
	}
}
