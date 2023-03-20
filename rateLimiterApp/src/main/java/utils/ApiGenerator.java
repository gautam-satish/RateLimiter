package utils;

import java.time.LocalTime;
import java.util.Hashtable;

import pojo.Api;

public class ApiGenerator {

	// Api(String name, int availableCalls, LocalTime startTime, LocalTime endTime )
	public static Hashtable<String, Api> setCustomValueForGet(LocalTime startTime, LocalTime endTime,
			int availableCalls) {
		Api get = new Api("get", availableCalls, startTime, endTime);
		Api post = new Api("post");
		Api patch = new Api("patch");
		Api delete = new Api("delete");
		Hashtable<String, Api> temp = new Hashtable<>();
		temp.put("get", get);
		temp.put("post", post);
		temp.put("patch", patch);
		temp.put("delete", delete);
		return temp;
	}

	public static Hashtable<String, Api> setAvailableCallsForGet(int availableCalls) {
		Api get = new Api("get", availableCalls);
		Api post = new Api("post");
		Api patch = new Api("patch");
		Api delete = new Api("delete");
		Hashtable<String, Api> temp = new Hashtable<>();
		temp.put("get", get);
		temp.put("post", post);
		temp.put("patch", patch);
		temp.put("delete", delete);
		return temp;
	}

	public static Hashtable<String, Api> setCustomValueForPost(LocalTime startTime, LocalTime endTime,
			int availableCalls) {
		Api get = new Api("get");
		Api post = new Api("post", availableCalls, startTime, endTime);
		Api patch = new Api("patch");
		Api delete = new Api("delete");
		Hashtable<String, Api> temp = new Hashtable<>();
		temp.put("get", get);
		temp.put("post", post);
		temp.put("patch", patch);
		temp.put("delete", delete);
		return temp;
	}
}
