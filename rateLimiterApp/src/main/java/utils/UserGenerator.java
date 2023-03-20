package utils;

import java.time.LocalTime;
import java.util.Hashtable;

import pojo.User;

public class UserGenerator {
	
	public static Hashtable<String, User> users = new Hashtable<>();
	
	public static Hashtable<String, User> createUsers() {		
		/*User class has 2 overloaded constructors
		 * Constructor 1 --> Accepts only userId as input. API Objects are created with default properties that are set in the Api constructor.
		 * Constructor 2 --> ! Accepts userId and a HashTable containing API objects as input.
		 * 					 ! The API objects are to be created and added in HashTable first
		 * 					 ! During creation of API objects, custom properties can be set				 	
		 */
		
		LocalTime currentTime = LocalTime.now();
		
		User gautam = new User("gautam", ApiGenerator.setCustomValueForGet(LocalTime.of(9, 03),LocalTime.of(14, 40), 10));
		User serviceUser = new User("service");
		User testUser = new User("test", ApiGenerator.setCustomValueForPost(LocalTime.of(22, 0), LocalTime.of(23, 0), 10));
		User testCombo1 = new User("testCombo1", ApiGenerator.setCustomValueForGet(currentTime.plusSeconds(5),currentTime.plusMinutes(10), 8));
		User testCombo2 = new User("testCombo2", ApiGenerator.setCustomValueForGet(currentTime.minusSeconds(10),currentTime.plusSeconds(20), 10));
		
		users.put(gautam.getUserId(), gautam);
		users.put(serviceUser.getUserId(), serviceUser);
		users.put(testUser.getUserId(), testUser);
		users.put(testCombo1.getUserId(), testCombo1);
		users.put(testCombo2.getUserId(), testCombo2);
		return users;
	}
}
