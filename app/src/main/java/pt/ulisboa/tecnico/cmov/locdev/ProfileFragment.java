package pt.ulisboa.tecnico.cmov.locdev;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.ulisboa.tecnico.cmov.locdev.Application.ClientTask;
import pt.ulisboa.tecnico.cmov.locdev.Application.FragmentInterface;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.locdev.wifiP2p.WifiP2pActivity;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.request.GetInfoFromServerRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.request.SaveProfileRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.SignInRequest;
import pt.ulisboa.tecnico.cmov.projcmu.response.GetInfoFromServerResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;
import pt.ulisboa.tecnico.cmov.projcmu.response.SaveProfileResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.SignInResponse;


public class ProfileFragment extends Fragment implements FragmentInterface{

    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_profile);
        return inflater.inflate(R.layout.profile_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshFragment();
        //setClickListener();
    }

    @Override
    public void refreshFragment() {
        populateUser();
        populateListView();
    }

    private void populateListView(){
        LocdevApp Application = (LocdevApp) getActivity().getApplicationContext();
        new GetLocationsTask().execute(new GetInfoFromServerRequest(Application.getUser(),Application.getCurrentLocation(),Application.getNearBeacons()));
        new ProfileTask().execute(new SaveProfileRequest(Application.getUser()));
    }

    private void populateListView(Map<String,String> Keypairs) {
//        String[] locations = {"Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda"};
        String[] locations = {};
//        newLocations.add(new Location(0,1,"Monumental"));
//        List<Location> newLocations = Application.getLocations(Application.getCurrentLocation(),new ArrayList<Integer>());
        if (Keypairs.size()>0) {
            locations = new String[Keypairs.size()];
            int i=0;
            for(String Key : Keypairs.keySet()){
                locations[i] = "Key: " + Key + " Value: " + Keypairs.get(Key);
                i++;
            }
//            locations = newLocations.toArray(locations);
            Log.d(this.getClass().getName(),"Able to getLocations");
            Toast.makeText(getContext(), "Able to getLocations", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Unable to getLocations", Toast.LENGTH_LONG).show();
            Log.d(this.getClass().getName(),"Unable to getLocations");
        }
        MyListView list = (MyListView) getActivity().findViewById(R.id.keys_list_profile);
        adapter = new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                locations);
        list.setAdapter(adapter);
    }

    private void setClickListener() {
        ListView listView = (ListView) getActivity().findViewById(R.id.keys_list_profile);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemView,
                                    final int position, long id) {

                View view = (LayoutInflater.from(getContext())).inflate(R.layout.keys_list_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setView(view);

                final EditText new_key = (EditText) view.findViewById(R.id.keys_list_input_text);
                // set alert text to the value of the old key
                new_key.setText(((TextView) itemView).getText());
                final String old_key_text = new_key.getText().toString();

                alertBuilder.setCancelable(true)
                        .setMessage("Edit Key")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String new_key_text = new_key.getText().toString().trim();
                                if (!new_key_text.equals(old_key_text)){
                                    adapter.insert(new_key_text, position);
                                    adapter.remove(old_key_text);
                                    adapter.notifyDataSetChanged();
                                    // TODO: update the key on the server
                                }
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.edit_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View thisView) {
                View view = (LayoutInflater.from(getContext())).inflate(R.layout.keys_list_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setView(view);

                final EditText new_key = (EditText) view.findViewById(R.id.keys_list_input_text);

                alertBuilder.setCancelable(true)
                        .setMessage("Add Key")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String new_key_text = new_key.getText().toString();
                                // Do nothing if no key is set
                                if (!new_key_text.equals("")) {
                                    adapter.add(new_key_text);
                                    adapter.notifyDataSetChanged();
                                    // TODO: update the keys list on server
                                }
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    private void populateUser() {
        TextView TVname = (TextView) getActivity().findViewById(R.id.name_profile);
        TextView TVemail = (TextView) getActivity().findViewById(R.id.email_profile);
        TextView TVkeys = (TextView) getActivity().findViewById(R.id.keys_title_profile);
//        TextView TVname = (TextView) getActivity().findViewById(R.id.name_profile);
        User user = ((LocdevApp) getActivity().getApplicationContext()).getUser();
        TVname.setText(user.getUsername());
        String keys = "";
        for(String key : user.getProfile().getKeyPairs().keySet()){
            keys.concat(key+",");
        }
        TVkeys.setText(keys);
    }

    public class GetLocationsTask extends ClientTask {
        public User user;

        public GetLocationsTask() {
            super((LocdevApp) getActivity().getApplicationContext());
            //TODO Funcionalidade básica, melhorar na procura
            List<SimWifiP2pDevice> devices = ((WifiP2pActivity) getActivity()).getNearDevices();
            if(devices.size()>0){
                setDevice(devices.get(0));
            }
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
            if(result==null) return;
            GetInfoFromServerResponse processResponse = (GetInfoFromServerResponse) result;
            this.app.setLocations(processResponse.getLocations());
//            populateListView(processResponse.getLocations());
        }
    }



    public class ProfileTask extends ClientTask {
        public User user;

        public ProfileTask() {
            super((LocdevApp) getActivity().getApplicationContext());
        }

        @Override
        protected Response doInBackground(Request... requests) {
//            GetInfoFromServerRequest req = (GetInfoFromServerRequest) requests[0];
//            this.user = req.getUser();
            return super.doInBackground(requests);
        }

        @Override
        protected void onPostExecute(Response result) {
            Log.d(this.getClass().getName(),"Start Onpostexecute");
            if(result==null) return;
            SaveProfileResponse processResponse = (SaveProfileResponse) result;
//            this.app.setLocations(processResponse.getLocations());
            populateListView(app.getUser().getProfile().getKeyPairs());
        }
    }
}
