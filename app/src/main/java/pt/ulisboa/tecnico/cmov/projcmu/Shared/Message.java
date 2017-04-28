package pt.ulisboa.tecnico.cmov.projcmu.Shared;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	private static final long serialVersionUID = 4L;
	
	private Location location; //localizazao a qual a mensagem pertence
	private String message; // conteudo da mensagem
	private Restriction restriction; //lista de restricoes para a mensagem
	
	private Date CreationDate; 
	private Date DeletionDate;
	private User user;
	
	public Message(Location location,String message,Restriction restriction,User creationUser){
		setLocation(location);
		setMessage(message);
		setRestriction(restriction);
		this.CreationDate = new Date();
		this.DeletionDate = new Date();
		this.DeletionDate.setMinutes(this.DeletionDate.getMinutes()+10);
		this.user = creationUser;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Restriction getRestriction() {
		return restriction;
	}
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}
	public Date getCreationDate() {
		return CreationDate;
	}
	public void setCreationDate(Date creationDate) {
		CreationDate = creationDate;
	}
	public Date getDeletionDate() {
		return DeletionDate;
	}
	public void setDeletionDate(Date deletionDate) {
		DeletionDate = deletionDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object message){
		//System.out.println("equals object");
		if(message instanceof Message){
			return this.equals((Message) message);
		}
		return false;
	}
	
	public boolean equals(Message message){
		//System.out.println("equals user");
		//System.out.println(user.getUsername()+" "+this.getUsername());
		return (this.location.equals(message.getLocation()) &&
				this.message.equals(message.getMessage()) &&
				this.CreationDate.equals(message.getCreationDate()) &&
				this.user.equals(message.getUser()) &&
				this.restriction.equals(message.getRestriction()));
	}
	
	public void DumpInfo(){
		System.out.println("message "+ this.getMessage() +"|"+ this.user.getUsername());
	}
}
