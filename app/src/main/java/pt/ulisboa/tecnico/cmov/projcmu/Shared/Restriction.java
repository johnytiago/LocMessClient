package pt.ulisboa.tecnico.cmov.projcmu.Shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Restriction implements Serializable{

	private static final long serialVersionUID = 7L;
	private Map<String, String> restrictions = new HashMap<String, String>();
	boolean whitelist = false;
	
	public Restriction(boolean whitelist){
		this.whitelist = whitelist;
	}
	
	public Map<String, String> getRestrictions() {
		return restrictions;
	}

	public void AddRestrictions(String key,String value) {
		this.restrictions.put(key, value);
	}

	public boolean isWhitelist() {
		return whitelist;
	}

	public void setWhitelist(boolean whitelist) {
		this.whitelist = whitelist;
	}

	public boolean match(Map<String, String> keyPairs){
		for(String key : restrictions.keySet()){
			if(restrictions.get(key).equals(keyPairs.get(key)) != whitelist){
				return false;
			}
		}
		return true;
	}
	
	public boolean match(Restriction restriction){
		return match(restriction.getRestrictions());
	}
	
	@Override
	public boolean equals(Object rest){
		//System.out.println("equals object");
		if(rest instanceof Restriction){
			return this.equals((Restriction) rest);
		}
		return false;
	}
	
	public boolean equals(Restriction rest){
		Map<String, String> restrict = rest.restrictions;
		Set<String> key1 = restrictions.keySet();
		Set<String> key2 = restrict.keySet();
		
		
		if(key1.size()!=key2.size()) return false;
		for(String key : key1){
			if(!restrictions.get(key).equals(restrict.get(key))){
				return false;
			}
		}
		
		return true;
	}
}
