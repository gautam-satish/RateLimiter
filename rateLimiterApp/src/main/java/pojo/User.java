package pojo;

import java.util.Hashtable;
import java.util.Objects;

public class User {
	
	private String userId;
	private Hashtable<String, Api> apiData;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Hashtable<String, Api> getApiData() {
		return apiData;
	}
	public void setApiData(Hashtable<String, Api> apiData) {
		this.apiData = apiData;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(userId, other.userId);
	}
	
	//default
	public User(String userId) {
		super();
		this.userId = userId;		
		this.apiData = createAPIData() ;
	}
	
	//custom api properties for a user
	public User(String userId, Hashtable<String, Api> apiData) {
		super();
		this.userId = userId;		
		this.apiData = apiData ;
	}
	
	//method that creates APIData objects with default values
	private Hashtable<String, Api> createAPIData() {
		Api get = new Api("get");
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
	
	
}
