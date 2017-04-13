package pt.ulisboa.tecnico.cmov.locdev;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class MessagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_messages);
        return inflater.inflate(R.layout.messages_layout, container, false);
    }

    private void populateListView() {
        // TODO: GO FETCH THIS FROM SERVER
        String[][] msgs = {
                { "LF Android Developer", "Fred", "25sec ago"},
                { "WTB CMU Andoid App", "Barney", "5min ago" },
                { "CMU Rocks!!", "George", "10min ago" },
                { "WTS CMU Project", "GOD", "1hour ago" } };

        String[] from = new String[] { "title", "author", "date"};
        int[] to = new int[] { R.id.message_item_title, R.id.message_item_author, R.id.message_item_date};

        List<HashMap<String, Object>> fillMaps = new ArrayList<>();

        for(String[] msg : msgs){
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", msg[0]); // This will be shown in R.id.message_item_title
            map.put("author", msg[1]); // And this in R.id.message_item_author
            map.put("date", msg[2]); // And this in R.id.message_item_date
            fillMaps.add(map);
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
}
