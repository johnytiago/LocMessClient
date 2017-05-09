package pt.ulisboa.tecnico.cmov.projcmu.request;


import java.util.List;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.ServerContext;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class RemoveLocationRequest extends Request {

	private static final long serialVersionUID = 1L;
	
	private Location location;
	private User creationUser;
	
	public User getCreationUser() {
		return creationUser;
	}

	public RemoveLocationRequest(Location location,User user) {
		this.location = location;
		this.creationUser = user;
	}
	
	public Location getLocation() {
		return location;
	}
	
	@Override
	public Response Run(ServerContext ctx){
		return this.processRequest(this,ctx);
	}
}
