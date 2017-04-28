package pt.ulisboa.tecnico.cmov.projcmu.Shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Profile implements Serializable{
	private static final long serialVersionUID = 5L;

	private Map<String, String> keyPairs = new HashMap<String, String>();
	
	public Map<String, String> getKeyPairs() {
		return keyPairs;
	}
	
	public void addKeyPair(String key, String value){
		this.keyPairs.put(key,value);
	}
	
	public void removeKeyPair(String key){
		this.keyPairs.remove(key);
	}
}
