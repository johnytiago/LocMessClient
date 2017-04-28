package pt.ulisboa.tecnico.cmov.projcmu.request;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmov.projcmu.core.Context;
import pt.ulisboa.tecnico.cmov.projcmu.response.*;

public class Request implements Serializable {
	
	public Response Run(Context ctx){
		return processRequest(this,ctx);
	}
	
	public Response processRequest(Request hr,Context ctx){
		//System.out.println("Request");
		return new Response();
	}
	
	public Response processRequest(LogInRequest hr,Context ctx){
		return new LogInResponse(ctx.userRegistered(hr.getUser()));
	}
	
	public Response processRequest(SignInRequest hr,Context ctx){
		return new SignInResponse(ctx.register(hr.getUser()));
	}
	
	public Response processRequest(GetInfoFromServerRequest hr,Context ctx){
		//TODO REsponse
		return new GetInfoFromServerResponse(ctx.getNearLocationsWithMessages(hr.getUser()));
	}
	
	public Response processRequest(AddLocationRequest hr,Context ctx){
		//TODO REsponse
		return new AddLocationResponse(ctx.AddLocation(hr.getLocation()));
	}
	
	public Response processRequest(RemoveLocationRequest hr,Context ctx){
		//TODO REsponse
		return new RemoveLocationResponse(ctx.RemoveLocationRequest(hr.getLocation(),hr.getCreationUser()));
	}
	
	public Response processRequest(AddMessageRequest hr,Context ctx){
		//TODO REsponse
		return new AddMessageResponse(ctx.newMessage(hr.getMensagem()));
	}
	
	public Response processRequest(RemoveMessageRequest hr,Context ctx){
		//TODO REsponse
		return new RemoveMessageResponse(ctx.RemoveMessage(hr.getMensagem(), hr.getUser()));
	}
	
	public Response processRequest(SaveProfileRequest hr,Context ctx){
		//TODO REsponse
		return new SaveProfileResponse(ctx.SaveProfile(hr.getUser()));
	}
	
	
}
