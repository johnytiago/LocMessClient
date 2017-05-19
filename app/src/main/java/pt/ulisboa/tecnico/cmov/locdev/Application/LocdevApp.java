package pt.ulisboa.tecnico.cmov.locdev.Application;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.projcmu.Client;
import pt.ulisboa.tecnico.cmov.projcmu.ClientInterface;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.request.AddLocationRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.AddMessageRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.GetInfoFromServerRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.LogInRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.RemoveLocationRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.RemoveMessageRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.request.SaveProfileRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.SignInRequest;
import pt.ulisboa.tecnico.cmov.projcmu.response.AddLocationResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.AddMessageResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.GetInfoFromServerResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.LogInResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.RemoveLocationResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;
import pt.ulisboa.tecnico.cmov.projcmu.response.SaveProfileResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.SignInResponse;

/**
 * Created by Pedro Alcobia on 08/05/2017.
 */

public class LocdevApp extends Application implements ClientInterface{
    private User user;
    private List<Message> Messages = new ArrayList<Message>();
    private List<String> NearBeacons =  new ArrayList<String>();
    private List<Location> NearLocations =  new ArrayList<Location>();

    public List<Location> LocationsToAdd = new ArrayList<Location>();
    public List<Location> LocationsToRemove = new ArrayList<Location>();

    public List<Message> MessagesToSendToPeers = new ArrayList<Message>();

    public List<Message> MessagesToAdd = new ArrayList<Message>();
    public List<Message> MessagesToRemove = new ArrayList<Message>();

    public Response resp;

    @Override
    public boolean SignUp(User user) {
//        new ClientTask(this).execute(new SignInRequest(user.getUsername(),user.getPassword()));
//        //Test if everything went ok
////        Log.d(this.getClass().getName(),"Start sign up");
//        while(resp==null){};
//        Log.d(this.getClass().getName(),"Start sign up");
//        SignInResponse processResponse = (SignInResponse) resp;
//        if(processResponse.getSuccessfull()){
//            this.user=user;
//        }
//        resp = null;
//        return processResponse.getSuccessfull();
        if(this.user==null){
            this.user=user;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean LogIn(User user) {
//        new ClientTask(this).execute(new LogInRequest(user.getUsername(),user.getPassword()));
//        //Test if everything went ok
//        while(resp==null){}
//        Log.d(this.getClass().getName(),"Start get response");
//        LogInResponse processResponse = (LogInResponse) resp;
//        Log.d(this.getClass().getName(),"Start get response _ 1");
//        if(processResponse.getSuccessfull()){
//            Log.d(this.getClass().getName(),"Start get response _ 2");
//            this.user=processResponse.getUser();
//        }
//        Log.d(this.getClass().getName(),"Start get response _ 3");
//        resp = null;
//        Log.d(this.getClass().getName(),"Start get response _ 4");
        if(this.user==null){
            this.user=user;
            return true;
        }else{
            return false;
        }
//        return processResponse.getSuccessfull();
    }

    @Override
    public boolean LogOut() {
        user = null;
        Messages = new ArrayList<Message>();
        NearLocations =  new ArrayList<Location>();
        resp = null;
        return true;
    }

    @Override
    public List<Location> getLocations(Location loc, List<String> BeaconIds) {
//        new ClientTask(this).execute(new GetInfoFromServerRequest(this.user,loc));
//        //Test if everything went ok
//        while(resp==null){}
//        GetInfoFromServerResponse processResponse = (GetInfoFromServerResponse) resp;
//        NearLocations = new ArrayList<Location>();
//        NearLocations.addAll(processResponse.getLocations());
//        resp=null;
        return NearLocations;
    }

    @Override
    public boolean createLocations(Location loc) {
        new ClientTask(this).execute(new AddLocationRequest(loc));
        //Test if everything went ok
//        while(resp==null){}
//        AddLocationResponse processResponse = (AddLocationResponse) resp;
//        resp=null;
//        return processResponse.isSuccess();
        return true;
    }

    @Override
    public boolean removeLocations(Location loc) {
        new ClientTask(this).execute(new RemoveLocationRequest(loc,this.user));
//        //Test if everything went ok
//        while(resp==null){}
//        RemoveLocationResponse processResponse = (RemoveLocationResponse) resp;
//        resp=null;
//        return processResponse.isSuccess();
        return true;
    }

    @Override
    public boolean postMessage(Message m) {
        new ClientTask(this).execute(new AddMessageRequest(m));
        //Test if everything went ok
//        while(resp==null){}
//        AddMessageResponse processResponse = (AddMessageResponse) resp;
//        resp=null;
//        return processResponse.isSuccess();
        return true;
    }

    @Override
    public boolean unpostMessage(Message m) {
        new ClientTask(this).execute(new RemoveMessageRequest(m,this.user));
        //Test if everything went ok
//        while(resp==null){}
//        AddMessageResponse processResponse = (AddMessageResponse) resp;
//        resp=null;
//        return processResponse.isSuccess();
        return true;
    }

    @Override
    public List<Message> getAvailableMessages() {
        List<Message> messages = new ArrayList<Message>();
        if(user==null){
            System.err.println("session not initiated");
            return messages;
        }
        for(Location loc : NearLocations){
            messages.addAll(loc.getMessages());
        }
        Messages = new ArrayList<Message>();
        Messages.addAll(messages);
        return messages;
    }

    public void addMessage(Message m){
        NearLocations.add(m.getLocation());
        Messages.add(m);
    }

    @Override
    public boolean addKeyPair(String key, String value) {
        if(user==null){
            System.err.println("session not initiated");
            return false;
        }
        this.user.addKeyPair(key, value);
        new ClientTask(this).execute(new SaveProfileRequest(this.user));
        while(resp==null){}
        SaveProfileResponse processResponse = (SaveProfileResponse) resp;
        resp=null;
        return processResponse.isSuccess();
    }

    public void setNearBeacons(List<String> nearLocations){
        NearBeacons = new ArrayList<String>();
        NearBeacons.addAll(nearLocations);
    }

    public void setCurrentLocation(Location loc){
        this.user.setLocation(loc);
    }

    public List<String> getNearBeacons(){
        return this.NearBeacons;
    }

    public Location getCurrentLocation(){
        return this.user.getLocation();
    }
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void setLocations(List<Location> locations) {
        NearLocations = new ArrayList<Location>();
        NearLocations.addAll(locations);
        getAvailableMessages();
    }

    public void removeKeyPair(String key) {
        if(user==null){
            System.err.println("session not initiated");
//            return false;
        }
        this.user.removeKeyPair(key);
        new ClientTask(this).execute(new SaveProfileRequest(this.user));
        while(resp==null){}
        SaveProfileResponse processResponse = (SaveProfileResponse) resp;
        resp=null;
//        processResponse.isSuccess();
    }
}
