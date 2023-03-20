package rateLimiterTest;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Test;

import pojo.User;
import utils.ApiGenerator;
import utils.AppProperty;
import utils.UserGenerator;

public class TestCases {

	// checks if the number of calls have exceeded
	// Note: assuming that the current time window is active
	@Test
	public void numberOfCallsExceeded() {
		int allowedNumberOfRequests = 10;
		int numberOfRequests = 50;
		User user1 = new User("gautam", ApiGenerator.setAvailableCallsForGet(allowedNumberOfRequests));
		UserGenerator.users.put("gautam", user1);
		String message = RateLimiterTest.executeCalls("gautam", "get", numberOfRequests);
		int actualCount = Integer.parseInt(message.split("/")[1]);
		assertEquals(allowedNumberOfRequests, actualCount);
	}

	// checks if the call is made in unscheduled time window
	@Test
	public void callMadeinUnscheduledWindow() {
		LocalTime currentTime = LocalTime.now();
		int allowedNumberOfRequests = 50;
		int numberOfRequests = 10;
		User user1 = new User("gautam", ApiGenerator.setCustomValueForGet(currentTime.plusMinutes(10),
				currentTime.plusHours(2), allowedNumberOfRequests));
		UserGenerator.users.put("gautam", user1);
		String message = RateLimiterTest.executeCalls("gautam", "get", numberOfRequests);
		int actualCount = Integer.parseInt(message.split("/")[1]);
		assertEquals(0, actualCount);

	}

	// user initially makes an API call outside the allowed time window, but eventually gains access.
	// however, the user access is blocked once they exhaust their available requests.
	@Test
	public void combo() {
		LocalTime currentTime = LocalTime.now();
		int allowedNumberOfRequests = 3;
		int numberOfRequests = 20;
		User user1 = new User("gautam", ApiGenerator.setCustomValueForGet(currentTime.plusSeconds(3),
				currentTime.plusMinutes(10), allowedNumberOfRequests));
		UserGenerator.users.put("gautam", user1);
		String message = RateLimiterTest.executeCallsWithDelay("gautam", "get", numberOfRequests);
		int actualCount = Integer.parseInt(message.split("/")[1]);
		assertEquals(allowedNumberOfRequests, actualCount);

	}

	// time getting expired when user is making requests
	@Test
	public void combo1() {
		LocalTime currentTime = LocalTime.now();
		int allowedNumberOfRequests = 10;
		int numberOfRequests = 20;
		User user1 = new User("gautam", ApiGenerator.setCustomValueForGet(currentTime.minusSeconds(10),
				currentTime.plusSeconds(5), allowedNumberOfRequests));
		UserGenerator.users.put("gautam", user1);
		String message = RateLimiterTest.executeCallsWithDelay1("gautam", "get", numberOfRequests);
		int actualCount = Integer.parseInt(message.split("/")[1]);
		assertEquals(4, actualCount); //expected number of valid requests is set as 4 since the expiry time is current time + 5sec in which only 4 requests can be made with an interval of 1 second each

	}

	// checks the default settings for service user
	@Test
	public void defaultSettings() {
		int numberOfRequests = 10;
		User user1 = new User("service");
		UserGenerator.users.put("service", user1);
		String message = RateLimiterTest.executeCalls("service", "get", numberOfRequests);
		int allowedNumberOfRequests = Integer.valueOf(AppProperty.getValue("default.maxCalls")) ;
		int actualCount = Integer.parseInt(message.split("/")[1]);
		assertEquals(allowedNumberOfRequests, actualCount);
	}

	// checks if non existing user scenario is handled properly
	@Test
	public void nonExistingUser() {
		String actualMessage = RateLimiterTest.executeCalls("userx", "post", 10);
		String expectedMessage = "User userx does not exist!/0";
		assertEquals(expectedMessage, actualMessage);

	}

}
