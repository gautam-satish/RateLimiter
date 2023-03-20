package main;

import customExceptions.LimitException;
import customExceptions.UserDoesNotExistException;
import services.RateLimiterService;
import utils.UserGenerator;

public class Main {
	
	/*
	 * 1) UserGenerator class is used to create user Objects to mimic the database containing userData.
	 * 2) A set of users are created by UserGenerator.createUsers() method. 
	 * 3) The API specific properties can be set in UserGenerator.createUsers() method which creates objects using the custom properties
	 * 		such as the available number of requests that can be made to a specific API by a specific user and the window time.
	 * 4) executeCalls() method is used to mimic the API calls made which is initially passed through the RateLimiter.
	 * 		A request is made in every one second by the executeCalls() method.
	 * 5) An application.properties file is used to set the default parameters which can be found in "(src/main/resources)".	 *  
	 */
	
	/*
	 * Output
	 * The RateLimiter returns true for every valid request made and prints the corresponding error for an invalid request.
	 */
	
	public static void main(String[] args) {		

		UserGenerator.createUsers();	//UserGenerator class is used to create user Objects to mimic the database containing userData.
		int numberOfcalls = 15;			//number of requests to be made by the client.
		
		
		/*1) This user initially makes an API call outside the allowed time window, but eventually gains access after 4 seconds.However, the user access is blocked once they exhaust their available requests.
			availablecalls = 8
			timewindow = (currentTime + 4sec) to (currentTime + 1 hour)  */
		executeCalls("testCombo1", "get", numberOfcalls);
		System.out.println("---------------------------------\n");
		
		
		
		/*2) Time gets expired in 4 seconds when this user is making requests even though he hasn't exhausted his available number of requests.
			availablecalls = 10
			timewindow = (currentTime - 10sec) to (currentTime + 5sec)	*/
		executeCalls("testCombo2", "get", numberOfcalls);
		System.out.println("---------------------------------\n");
		
		
		
		/*3) The available number of calls set for this user is 7(default value  set in properties file) but the user tries to make 15 calls within the scheduled window.
			availablecalls = 7
			timewindow = whole day	*/
		executeCalls("service","get",numberOfcalls);   //service user
		System.out.println("---------------------------------\n");
		
			
		
		/*4) This user is trying to make calls during non-scheduled window.
			availablecalls = 10
			timewindow = whole day	 */
		executeCalls("gautam","get",numberOfcalls);  //custom properies //navigate to UserGenerator.createUsers() method to set custom properties
		System.out.println("---------------------------------\n");
		
		
		
		//5) This user does not exist 
		executeCalls("userx","get",numberOfcalls); //wrong user
		System.out.println("---------------------------------\n");
		
		
		
		
	}	
	
	/*
	 * The executeCalls method is used to initiate API calls by the client and accepts three parameters: userId, method, and the number of API calls to be made.
	 * To mimic the actual scenario of a client making requests, the RateLimiter is executed in a loop using the provided input.
	 * The RateLimiterService.validateUserRequest is called in every one second.
	 */
	public static void executeCalls(String user, String method, int calls) {
		Boolean status = null;
		for(int i=0; i<=calls; i++) {
			wait(1000);
			try {
				status = RateLimiterService.validateUserRequest(user, method);
				System.out.println("request " + i + " for " + user + " :" +status);
			} catch (LimitException | UserDoesNotExistException e) {
				System.out.println("request " + i + " for " + user + " :" + e.getMessage());
			}			
		}		
	}
	
	public static void wait(int ms){
	    try{
	        Thread.sleep(ms);
	    }catch(InterruptedException ex){
	        Thread.currentThread().interrupt();
	    }
	}
	
}
