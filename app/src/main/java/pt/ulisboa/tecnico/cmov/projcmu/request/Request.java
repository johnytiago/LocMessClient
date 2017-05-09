package pt.ulisboa.tecnico.cmov.projcmu.request;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.*;
import pt.ulisboa.tecnico.cmov.projcmu.response.*;

public class Request implements Serializable {
	
	public Response Run(ServerContext ctx){
		return processRequest(this,ctx);
	}
	
	public Response processRequest(Request hr,ServerContext ctx){
		//System.out.println("Request");
		return new Response();
	}
	
	public Response processRequest(LogInRequest hr,ServerContext ctx){
		return new LogInResponse(ctx.userRegistered(hr.getUser()));
	}
	
	public Response processRequest(SignInRequest hr,ServerContext ctx){
		return new SignInResponse(ctx.register(hr.getUser()));
	}
	
	public Response processRequest(GetInfoFromServerRequest hr,ServerContext ctx){
		//TODO REsponse
		return new GetInfoFromServerResponse(ctx.getNearLocationsWithMessages(hr.getUser()));
	}
	
	public Response processRequest(AddLocationRequest hr,ServerContext ctx){
		//TODO REsponse
		return new AddLocationResponse(ctx.AddLocation(hr.getLocation()));
	}
	
	public Response processRequest(RemoveLocationRequest hr,ServerContext ctx){
		//TODO REsponse
		return new RemoveLocationResponse(ctx.RemoveLocationRequest(hr.getLocation(),hr.getCreationUser()));
	}
	
	public Response processRequest(AddMessageRequest hr,ServerContext ctx){
		//TODO REsponse
		return new AddMessageResponse(ctx.newMessage(hr.getMensagem()));
	}
	
	public Response processRequest(RemoveMessageRequest hr,ServerContext ctx){
		//TODO REsponse
		return new RemoveMessageResponse(ctx.RemoveMessage(hr.getMensagem(), hr.getUser()));
	}
	
	public Response processRequest(SaveProfileRequest hr,ServerContext ctx){
		//TODO REsponse
		return new SaveProfileResponse(ctx.SaveProfile(hr.getUser()));
	}
	
	
}
