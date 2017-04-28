package pt.ulisboa.tecnico.cmov.projcmu.request;


import java.util.List;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.core.Context;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class AddLocationRequest extends Request {

	private static final long serialVersionUID = 1L;
	
	private Location location;
	
	public AddLocationRequest(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	@Override
	public Response Run(Context ctx){
		return this.processRequest(this,ctx);
	}
}
