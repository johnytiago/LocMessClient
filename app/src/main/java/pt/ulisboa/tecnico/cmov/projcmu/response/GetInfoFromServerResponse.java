package pt.ulisboa.tecnico.cmov.projcmu.response;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;

import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class GetInfoFromServerResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	private List<Location> Locations= new ArrayList<Location>();

	public GetInfoFromServerResponse(List<Location> locations) {
		this.Locations=locations;
	}
	
	public List<Location> getLocations(){
		return this.Locations;
	}
	
	@Override
	public String Run(){
		return this.processResponse(this);
	}

}
