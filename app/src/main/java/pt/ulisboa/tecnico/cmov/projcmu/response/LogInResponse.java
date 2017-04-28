package pt.ulisboa.tecnico.cmov.projcmu.response;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class LogInResponse extends Response {
	
	private boolean successfull=false;
	private User user;

	public LogInResponse(User user) {
		this.user = user;
		this.successfull=(user!=null);
	}
	
	public boolean getSuccessfull(){
		return this.successfull;
	}
	
	public User getUser() {
		return user;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String Run(){
		return this.processResponse(this);
	}

}
