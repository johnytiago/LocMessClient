package pt.ulisboa.tecnico.cmov.projcmu.request;


import java.util.List;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.core.Context;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class AddMessageRequest extends Request {

	private static final long serialVersionUID = 1L;
	
	private Message mensagem;

	public AddMessageRequest(Message mensagem) {
		this.mensagem = mensagem;
	}
	
	public Message getMensagem() {
		return mensagem;
	}
	
	@Override
	public Response Run(Context ctx){
		return this.processRequest(this,ctx);
	}
}
