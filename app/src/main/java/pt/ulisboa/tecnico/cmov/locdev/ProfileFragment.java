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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.getActivity().setTitle(R.string.title_activity_profile);
        return inflater.inflate(R.layout.profile_layout, container, false);
    }

    private void populateListView() {
        String[] locations = {"Monumental", "RNL", "Tenda SINFO", "Tecnico Alameda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                locations);
        MyListView list = (MyListView) getActivity().findViewById(R.id.keys_list_profile);
        list.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateListView();
    }
}
