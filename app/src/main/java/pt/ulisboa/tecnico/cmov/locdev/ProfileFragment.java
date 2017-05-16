package pt.ulisboa.tecnico.cmov.locdev;

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

import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.ulisboa.tecnico.cmov.locdev.Application.ClientTask;
import pt.ulisboa.tecnico.cmov.locdev.Application.FragmentInterface;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.locdev.wifiP2p.WifiP2pActivity;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.request.GetInfoFromServerRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.request.SignInRequest;
import pt.ulisboa.tecnico.cmov.projcmu.response.GetInfoFromServerResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;
import pt.ulisboa.tecnico.cmov.projcmu.response.SignInResponse;


public class ProfileFragment extends Fragment implements FragmentInterface{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_profile);
        return inflater.inflate(R.layout.profile_layout, container, false);
    }

    private void populateListView(){
        LocdevApp Application = (LocdevApp) getActivity().getApplicationContext();
        new GetLocationsTask().execute(new GetInfoFromServerRequest(Application.getUser(),Application.getCurrentLocation(),Application.getNearBeacons()));
    }

    private void populateListView(List<Location> newLocations) {
//        String[] locations = {"Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda"};
        String[] locations = {};
//        newLocations.add(new Location(0,1,"Monumental"));
//        List<Location> newLocations = Application.getLocations(Application.getCurrentLocation(),new ArrayList<Integer>());
        if (newLocations.size()>0) {
            locations = new String[newLocations.size()];
            int i=0;
            for(Location loc : newLocations){
                locations[i] = loc.getName();
                i++;
            }
//            locations = newLocations.toArray(locations);
            Log.d(this.getClass().getName(),"Able to getLocations");
            Toast.makeText(getContext(), "Able to getLocations", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Unable to getLocations", Toast.LENGTH_LONG).show();
            Log.d(this.getClass().getName(),"Unable to getLocations");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                locations);
        MyListView list = (MyListView) getActivity().findViewById(R.id.keys_list_profile);
        list.setAdapter(adapter);
    }

    private void setClickListener(){
        ListView listView = (ListView) getActivity().findViewById(R.id.keys_list_profile);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshFragment();
        //setClickListener();
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

    @Override
    public void refreshFragment() {
        populateUser();
        populateListView();
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
            populateListView(processResponse.getLocations());
        }
    }
}
