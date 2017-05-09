package pt.ulisboa.tecnico.cmov.projcmu.request;


import java.util.List;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.ServerContext;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class RemoveMessageRequest extends Request {

	private static final long serialVersionUID = 1L;
	
	private Message mensagem;
	private User creationUser;

	public RemoveMessageRequest(Message mensagem,User creationUser) {
		this.mensagem = mensagem;
		this.creationUser=creationUser;
	}
	
	public Message getMensagem() {
		return mensagem;
	}
	
	public User getUser() {
		return creationUser;
	}
	
	@Override
	public Response Run(ServerContext ctx){
		return this.processRequest(this,ctx);
	}
}
