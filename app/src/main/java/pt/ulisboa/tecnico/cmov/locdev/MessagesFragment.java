package pt.ulisboa.tecnico.cmov.locdev;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.ulisboa.tecnico.cmov.locdev.Application.ClientTask;
import pt.ulisboa.tecnico.cmov.locdev.Application.FragmentInterface;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.locdev.wifiP2p.WifiP2pActivity;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Restriction;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.request.AddMessageRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.GetInfoFromServerRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.RemoveMessageRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.response.AddMessageResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.GetInfoFromServerResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.RemoveMessageResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class MessagesFragment extends Fragment implements FragmentInterface{

    public static final String MESSAGE_TITLE = "pt.ulisboa.tecnico.cmov.locdev.title";
    public static final String MESSAGE_AUTHOR = "pt.ulisboa.tecnico.cmov.locdev.author";
    public static final String MESSAGE_DATE = "pt.ulisboa.tecnico.cmov.locdev.date";
    //TODO: Add the rest of arguments

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_messages);
        return inflater.inflate(R.layout.messages_layout, container, false);
    }

    private void populateListView(){
        LocdevApp Application = (LocdevApp) getActivity().getApplicationContext();
        for(Message m : Application.MessagesToAdd){
            sendMessage(m);
        }
        SendToPeersIfAvailable();
        Application.MessagesToAdd = new ArrayList<Message>();
        for(Message m : Application.MessagesToRemove){
            RemoveMessage(m);
        }
        Application.MessagesToRemove = new ArrayList<Message>();

        new GetLocationsTask().execute(new GetInfoFromServerRequest(Application.getUser(),Application.getCurrentLocation(),Application.getNearBeacons()));
    }

    private void populateListView(List<Message> messages) {
        String[] from = new String[] { "title", "author", "date"};
        int[] to = new int[] { R.id.message_item_title, R.id.message_item_author, R.id.message_item_date};

        List<HashMap<String, Object>> fillMaps = new ArrayList<>();
        if(messages.size()>0){
            for (Message msg : messages) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("title", msg.getMessage()); // This will be shown in R.id.message_item_title
                map.put("author", msg.getUser().getUsername()); // And this in R.id.message_item_author
                map.put("date", msg.getCreationDate().toString()); // And this in R.id.message_item_date
                fillMaps.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), fillMaps, R.layout.messages_list_item, from, to);

        ListView list = (ListView) getActivity().findViewById(R.id.messages_list);
        list.setAdapter(adapter);
    }

    private void setClickListener(){

        ListView listView = (ListView) getActivity().findViewById(R.id.messages_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view.findViewById(R.id.message_item_title)).getText().toString();
                String date = ((TextView) view.findViewById(R.id.message_item_date)).getText().toString();
                String author = ((TextView) view.findViewById(R.id.message_item_author)).getText().toString();
                // TODO: Pass the rest of arguments
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra(MESSAGE_TITLE, title);
                intent.putExtra(MESSAGE_AUTHOR, author);
                intent.putExtra(MESSAGE_DATE, date);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.messages_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshFragment();
    }

    @Override
    public void refreshFragment() {
        populateListView();
        setClickListener();
    }

    public class GetLocationsTask extends ClientTask {
        public User user;
        public GetLocationsTask() {
            super((LocdevApp) getActivity().getApplicationContext());
        }

        @Override
        protected Response doInBackground(Request... requests) {
            GetInfoFromServerRequest req = (GetInfoFromServerRequest) requests[0];
            this.user = req.getUser();
            return super.doInBackground(requests);
        }

        @Override
        protected void onPostExecute(Response result) {
            Log.d(this.getClass().getName(),"Start Onpostexecute");
            GetInfoFromServerResponse processResponse = (GetInfoFromServerResponse) result;
            this.app.setLocations(processResponse.getLocations());
            populateListView(this.app.getAvailableMessages());
        }
    }

    //Funciton to execute the message remove.
    public void RemoveMessage(Message m){
        LocdevApp app = (LocdevApp) getActivity().getApplicationContext();
        RemoveMessageRequest req = new RemoveMessageRequest(m,app.getUser());
        // send to server
        new PostMessageTask().execute(req);
    }

    private void SendToPeersIfAvailable() {
        LocdevApp app = (LocdevApp) getActivity().getApplicationContext();
        List<SimWifiP2pDevice> devices = ((MainActivity) getActivity()).getNearDevices();
        if(devices.size()<1) return;
        List<Message> messages = app.MessagesToSendToPeers;
        for(Message m : messages){
            AddMessageRequest req = new AddMessageRequest(m);
            for(SimWifiP2pDevice device : devices){
                Log.d(this.getClass().getName(),"messages being sent: " +device.getVirtIp() + " message: " + m.getMessage());
                new PostMessageTask(device).execute(req);
            }
        }
    }

    //Funciton to execute the message sending Decentralized.
    public void sendMessage(Message m){
        List<SimWifiP2pDevice> devices = ((WifiP2pActivity) getActivity()).getNearDevices();
        AddMessageRequest req = new AddMessageRequest(m);
//        if(devices.size()>0){
//            ((MainActivity) getActivity()).requestPeers();
//            ((MainActivity) getActivity()).requestGroupInfo();
//            for (SimWifiP2pDevice device : devices) {
//                //send to peers
//                new PostMessageTask(device).execute(req);
//            }
//        }else{
            LocdevApp app = (LocdevApp) getActivity().getApplicationContext();
            app.MessagesToSendToPeers.add(m);
//        }
        // send to server
        new PostMessageTask().execute(req);
    }

    /*Posting a message*/
    public class PostMessageTask extends ClientTask {
        public User user;

        public PostMessageTask() {
            super((LocdevApp) getActivity().getApplicationContext());
        }

        public PostMessageTask(SimWifiP2pDevice device){
            super((LocdevApp) getActivity().getApplicationContext());
            setDevice(device);
        }

        @Override
        protected Response doInBackground(Request... requests) {
//            AddMessageRequest req = (AddMessageRequest) requests[0];
//            this.user = req.getUser();
            return super.doInBackground(requests);
        }

        @Override
        protected void onPostExecute(Response result) {
            Log.d(this.getClass().getName(),"Start Onpostexecute");
            if(result instanceof AddMessageResponse ) {
                AddMessageResponse processResponse = (AddMessageResponse) result;
//            this.app.setLocations(processResponse.getLocations());
                if (processResponse.isSuccess()) {
                    Log.d(this.getClass().getName(), "Start Onpostexecute Success");
                } else {
                    Log.d(this.getClass().getName(), "Start Onpostexecute Failed");
                }
//            populateListView(this.app.getAvailableMessages());
            }else if(result instanceof RemoveMessageResponse){
                RemoveMessageResponse processResponse = (RemoveMessageResponse) result;
//            this.app.setLocations(processResponse.getLocations());
                if (processResponse.isSuccess()) {
                    Log.d(this.getClass().getName(), "Start Onpostexecute Success");
                } else {
                    Log.d(this.getClass().getName(), "Start Onpostexecute Failed");
                }
//            populateListView(this.app.getAvailableMessages());
            }
        }
    }
}
