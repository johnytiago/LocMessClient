package pt.ulisboa.tecnico.cmov.projcmu.Shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ServerContext {

	public boolean register(User user);
	
	public User userRegistered(User user);
	
	public boolean AddLocation(Location loc);

	public List<Location> getNearLocationsWithMessages(User user,List<String> BeaconIds);
	
	public List<Location> getNearLocations(User user,List<String> BeaconIds);
	
	public boolean newMessage(Message message);
	
	public boolean RemoveMessage(Message message,User creationUser);

	public boolean RemoveLocationRequest(Location location, User creationUser);

	public boolean SaveProfile(User user);

}
