package pt.ulisboa.tecnico.cmov.projcmu.request;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.ServerContext;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class SignInRequest extends Request {

	private String username = "";
	private String password = "";


	public SignInRequest(String username, String password) {
		this.username=username;
		this.password=password;
	}

	private static final long serialVersionUID = 1L;
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public User getUser(){
		return new User(username,password);
	}

	@Override
	public Response Run(ServerContext ctx){
		return this.processRequest(this,ctx);
	}

}
