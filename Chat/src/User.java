import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable{
	private String username;
	private static Set<String> userNames = new HashSet<>();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void adduserNames() {
		User.userNames.add(this.username);
	}
	
	public void removeUserNames() {
		User.userNames.remove(username);
	}
	
	public Set<String> getuserNames(){
		return User.userNames;
	}
}
