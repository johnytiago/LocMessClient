package pt.ulisboa.tecnico.cmov.projcmu.Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Location implements Serializable{
	private static final long serialVersionUID = 3L;
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getRng() {
		return rng;
	}
	public void setRng(double rng) {
		this.rng = rng;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	@Override
	public int hashCode() {
		return (int) (getLat() + getLng());
		//return super.hashCode();
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	private double lat; //latitude da localizacao
	private double lng; //longitude da localizacao
	
	//quando e uma localizacao de utilizador nao e preciso preencher os proximos valores
	private double rng; //raio a volta da localizacai
	private String Name; //nome da localizacao
	private List<Message> messages = new ArrayList<Message>(); // lista de mensagens nesta localizacao
	private User creationUser; 
	
	public User getCreationUser() {
		return creationUser;
	}
	
	public Location(double lat,double lng,String Name, User creationUser) {
		this.creationUser = creationUser;
		this.lat=lat;
		this.lng=lng;
		this.Name=Name;
	}
	public Location(double lat,double lng,String Name) {
		this.lat=lat;
		this.lng=lng;
		this.Name=Name;
	}
	
	public Location(){}
	
	public boolean in(Location userLocation){
		//TODO: retrieve if in the location
		if(Math.abs(this.lat - userLocation.lat) > 1 || Math.abs(this.lng - userLocation.getLng()) > 1 ){
			return false;
		}
		if(distance(userLocation) > this.getRng()){
			return false;
		}
		return true;
	}
	
	private double distance(Location userLocation){
		double theta = this.getLng() - userLocation.getLng();
		double dist = Math.sin(deg2rad(this.getLat())) * Math.sin(deg2rad(userLocation.getLat())) + Math.cos(deg2rad(this.getLat())) * Math.cos(deg2rad(userLocation.getLat())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344; //em kilometros
		System.out.println("Distance: " + dist + "KM");
		return dist;
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	@Override
	public boolean equals(Object loc){
		if(loc instanceof Location) {
			return equals((Location) loc);
		}
		return false;
	}
	
	public boolean equals(Location loc){
		return (loc.getLat()==this.getLat() && loc.getLng()==this.getLng() && loc.getName().equals(this.getName()));
	}
	
	public Location clone(){
		Location loc = new Location(lat,lng,getName());
		loc.setMessages(new ArrayList<Message>());
		
		return loc;
	}
	
	public void DumpInfo(){
		System.out.println("Start Location");
		System.out.println(this.getName() +"|"+ this.getLat() + " "+this.getLng() + " "+this.rng);
		for(Message m : this.getMessages()){
			m.DumpInfo();
		}
		System.out.println("End Location");
	}
}
