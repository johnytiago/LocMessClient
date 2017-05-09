package pt.ulisboa.tecnico.cmov.locdev;

import android.os.Bundle;
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

import pt.ulisboa.tecnico.cmov.locdev.Application.ClientTask;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Restriction;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;
import pt.ulisboa.tecnico.cmov.projcmu.request.GetInfoFromServerRequest;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.response.GetInfoFromServerResponse;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

public class MessagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_messages);
        return inflater.inflate(R.layout.messages_layout, container, false);
    }

    private void populateListView(){
        LocdevApp Application = (LocdevApp) getActivity().getApplicationContext();
        new GetLocationsTask().execute(new GetInfoFromServerRequest(Application.getUser(),Application.getCurrentLocation()));
    }

    private void populateListView(List<Message> messages) {
        messages.add(new Message(new Location(0,0,"algures"),"Mensagem super awesome",new Restriction(true),new User("User","Pass")));
        // TODO: GO FETCH THIS FROM SERVER
        String[][] msgs = {
                { "LF Android Developer", "Fred", "25sec ago"},
                { "WTB CMU Andoid App", "Barney", "5min ago" },
                { "CMU Rocks!!", "George", "10min ago" },
                { "WTS CMU Project", "GOD", "1hour ago" } };

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
        }else {
            for (String[] msg : msgs) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("title", msg[0]); // This will be shown in R.id.message_item_title
                map.put("author", msg[1]); // And this in R.id.message_item_author
                map.put("date", msg[2]); // And this in R.id.message_item_date
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
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getContext(),
                        ((TextView) view.findViewById(R.id.message_item_title)).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
}
