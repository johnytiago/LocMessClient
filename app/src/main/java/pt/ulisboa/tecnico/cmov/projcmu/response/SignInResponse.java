package pt.ulisboa.tecnico.cmov.projcmu.response;

import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class SignInResponse extends Response {
	
	private boolean successfull=false;

	public SignInResponse(boolean successfull) {
		this.successfull=successfull;
	}
	
	public boolean getSuccessfull(){
		return this.successfull;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String Run(){
		return this.processResponse(this);
	}

}
