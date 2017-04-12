package pt.ulisboa.tecnico.cmov.locdev;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_layout, container, false);
        return myView;
    }

    private void populateListView() {
        String[] locations = {"Arco do Cego", "Monumental", "Arco do Cego", "Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda", "Arco do Cego", "RNL", "Tenda SINFO", "Tecnico Alameda", "Arco do Cego", "Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                R.layout.key_list_item,
                locations);
        ListView list = (ListView) getActivity().findViewById(R.id.keys_list_profile);
        list.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateListView();
    }
}
