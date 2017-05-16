package pt.ulisboa.tecnico.cmov.projcmu.request;


import java.util.List;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.ServerContext;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class GetInfoFromServerRequest extends Request {

	private static final long serialVersionUID = 1L;

	private User user;
	private List<String> BeaconIds = new ArrayList<String>();

	public GetInfoFromServerRequest(User user) {
		this.setUser(user);
	}

	public GetInfoFromServerRequest(User user,Location loc,List<String> BeaconIds) {
		this.setUser(user);
		user.setLocation(loc);
		this.BeaconIds.addAll(BeaconIds);

	}

	@Override
	public Response Run(ServerContext ctx){
		return this.processRequest(this,ctx);
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

	public List<String> getBeaconIds() {
		return BeaconIds;
	}

	public void setBeaconIds(List<String> beaconIds) {
		BeaconIds = beaconIds;
	}

}
