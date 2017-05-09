package pt.ulisboa.tecnico.cmov.projcmu.request;


import java.util.List;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.ServerContext;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class GetInfoFromServerRequest extends Request {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public GetInfoFromServerRequest(User user) {
		this.setUser(user);
	}
	
	public GetInfoFromServerRequest(User user,Location loc) {
		this.setUser(user);
		user.setLocation(loc);
	}

	@Override
	public Response Run(ServerContext ctx){
		return this.processRequest(this,ctx);
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

}
