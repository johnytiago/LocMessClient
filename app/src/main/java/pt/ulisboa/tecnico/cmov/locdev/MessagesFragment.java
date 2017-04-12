package pt.ulisboa.tecnico.cmov.locdev;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_messages);
        return inflater.inflate(R.layout.messages_layout, container, false);
    }

    private void populateListView() {
        String[] locations = {"Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                locations);
        ListView list = (ListView) getActivity().findViewById(R.id.messages_list);
        list.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateListView();
    }
}
