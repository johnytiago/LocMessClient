package pt.ulisboa.tecnico.cmov.projcmu.response;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;

import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class AddMessageResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	private boolean success = false;

	public boolean isSuccess() {
		return success;
	}

	public AddMessageResponse(boolean success) {
		this.success=success;
	}
	
	@Override
	public String Run(){
		return this.processResponse(this);
	}

}
