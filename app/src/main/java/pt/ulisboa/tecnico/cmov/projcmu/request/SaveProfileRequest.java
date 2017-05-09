package pt.ulisboa.tecnico.cmov.projcmu.request;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.ServerContext;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class SaveProfileRequest extends Request {

	private User user;


	public User getUser() {
		return user;
	}

	public SaveProfileRequest(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public Response Run(ServerContext ctx){
		return this.processRequest(this,ctx);
	}

}
