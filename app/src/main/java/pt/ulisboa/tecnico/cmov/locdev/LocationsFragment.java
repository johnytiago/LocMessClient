package pt.ulisboa.tecnico.cmov.locdev;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ulisboa.tecnico.cmov.locdev.Application.ClientTask;
import pt.ulisboa.tecnico.cmov.locdev.Application.FragmentInterface;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.request.GetInfoFromServerRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.response.GetInfoFromServerResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

/**
 * Created by johnytiago on 10/04/2017.
 */

public class LocationsFragment extends Fragment implements FragmentInterface {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_locations);
        return inflater.inflate(R.layout.locations_layout, container, false);
    }

    private void populateListView(){
        LocdevApp Application = (LocdevApp) getActivity().getApplicationContext();
        new GetLocationsTask().execute(new GetInfoFromServerRequest(Application.getUser(),Application.getCurrentLocation()));
    }

    private void populateListView(List<Location> locations ) {
//        // TODO: GO FETCH THIS FROM SERVER
//        String[][] msgs = {
//                {"Arco do Cego", "[40.7127837, -74.00594130000002, 20m]"},
//                {"Monumental", "[10.717, -64.031232, 25m]"},
//                {"Alameda", "[20.712234837, -64.00595232, 5m]"},
//                {"Avenida de Roma", "[2.7127837, 4.005622342, 50m]"}
//        };
//
//
        String[] from = new String[] { "title", "coordinates"};
        int[] to = new int[] { R.id.location_item_title, R.id.location_item_coordinates };
//
        List<HashMap<String, Object>> fillMaps = new ArrayList<>();
//
//        for(String[] msg : msgs){
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("title", msg[0]); // This will be shown in R.id.location_item_title
//            map.put("coordinates", msg[1]); // And this in R.id.location_item_coordinates
//            fillMaps.add(map);
//        }

        if(locations.size()>0){
            for(Location loc : locations){
                HashMap<String, Object> map = new HashMap<>();
                map.put("title", loc.getName()); // This will be shown in R.id.location_item_title
                map.put("coordinates", "Lat: "+loc.getLat()+" Lng: " + loc.getLng()); // And this in R.id.location_item_coordinates
                fillMaps.add(map);
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), fillMaps, R.layout.locations_list_item, from, to);

        ListView list = (ListView) getActivity().findViewById(R.id.locations_list);
        list.setAdapter(adapter);

//        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), fillMaps, R.layout.locations_list_item, from, to);
//
//        ListView list = (ListView) getActivity().findViewById(R.id.locations_list);
//        list.setAdapter(adapter);
    }

    private void setClickListener(){
        ListView listView = (ListView) getActivity().findViewById(R.id.locations_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getContext(),
                        ((TextView) view.findViewById(R.id.location_item_title)).getText(), Toast.LENGTH_SHORT).show();
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
            populateListView(processResponse.getLocations());
        }
    }
}
