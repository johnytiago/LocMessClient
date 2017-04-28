package pt.ulisboa.tecnico.cmov.projcmu.response;

import java.io.Serializable;
import java.util.List;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;

public class Response implements Serializable {
	
	public String Run(){
		return processResponse(this);
	}
	
	public String processResponse(Response hr){
		return "Response";
	}
	
	public String processResponse(LogInResponse hr){
		return "Log in: " + hr.getSuccessfull();
	}
	
	public String processResponse(SignInResponse hr){
		return "Sign in: " + hr.getSuccessfull();
	}
	
	public String processResponse(GetInfoFromServerResponse hr){
		for(Location l : hr.getLocations()){
			l.DumpInfo();
		}
		return "GetLocations: " + hr.getLocations().size();
	}
	
	public String processResponse(AddLocationResponse hr){
		return "AddLocation: " + hr.isSuccess();
	}
	
	public String processResponse(RemoveLocationResponse hr){
		return "RemoveLocation: " + hr.isSuccess();
	}
	
	public String processResponse(AddMessageResponse hr){
		return "AddMessage: " + hr.isSuccess();
	}
	
	public String processResponse(RemoveMessageResponse hr){
		return "RemoveMessage: " + hr.isSuccess();
	}
	
	public String processResponse(SaveProfileResponse hr){
		return "RemoveMessage: " + hr.isSuccess();
	}
}