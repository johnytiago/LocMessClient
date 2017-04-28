package pt.ulisboa.tecnico.cmov.projcmu.Shared;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 6L;
	private String username;
	private String password;
	
	private Profile profile = new Profile();
	private Location location;
	
	public User(String Username,String Password){
		this.username=Username;
		this.password=Password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void addKeyPair(String key, String value){
		this.profile.addKeyPair(key, value);
	}
	
	public void removeKeyPair(String key){
		this.profile.removeKeyPair(key);
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public boolean equals(Object user){
		//System.out.println("equals object");
		if(user instanceof User){
			return this.equals((User) user);
		}
		return false;
	}
	
	public boolean equals(User user){
		//System.out.println("equals user");
		//System.out.println(user.getUsername()+" "+this.getUsername());
		return user.getUsername().equals(this.getUsername());
	}
	
	public boolean isValid(User user){
		return (equals(user) && getPassword().equals(user.getPassword()));
	}
}
