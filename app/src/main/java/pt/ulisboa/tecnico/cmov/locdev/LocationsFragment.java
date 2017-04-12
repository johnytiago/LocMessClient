package pt.ulisboa.tecnico.cmov.locdev;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by johnytiago on 10/04/2017.
 */

public class LocationsFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.locations_layout, container, false);
        return myView;
    }

    private void populateListView() {
        String[] locations = {"Arco do Cego", "Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                locations);
        ListView list = (ListView) getActivity().findViewById(R.id.location_list_view);
        list.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateListView();
    }
}
