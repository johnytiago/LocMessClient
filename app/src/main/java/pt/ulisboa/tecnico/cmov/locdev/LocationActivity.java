package pt.ulisboa.tecnico.cmov.locdev;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;

/**
 * Created by johnytiago on 13/05/2017.
 */

public class LocationActivity extends AppCompatActivity {
    Location selectedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Location");
        setSupportActionBar(toolbar);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String location_name = intent.getStringExtra(LocationsFragment.LOCATION_TITLE);
        LocdevApp app = (LocdevApp) getApplicationContext();
        List<Location> locations = app.getLocations(null,null);
        for(Location loc : locations){
            if(loc.getName().equals(location_name)){
                selectedLocation=loc;
                break;
            }
        }
        //TODO: receive the rest of the arguments

        // Capture the layout's TextView and set the string as its text
        TextView titleView = (TextView) findViewById(R.id.location_name_location);
        TextView LatView = (TextView) findViewById(R.id.x_coordenate_location);
        TextView LonView = (TextView) findViewById(R.id.y_coordenate_location);
        titleView.setText(selectedLocation.getName());
        LatView.setText(""+selectedLocation.getLat());
        LonView.setText(""+selectedLocation.getLng());
        //TODO: if is of type GPS set GONE for the wifiid layout
        // if not of type GPS set GONE for coordenates layouts & populate list

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.remove_location_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(LocationActivity.this)
                        .setMessage("Do you really want to remove location: " + location_name + " ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(LocationActivity.this, "Location: "+location_name+" was removed.", Toast.LENGTH_SHORT).show();
//                                onBackPressed();
                                LocdevApp app = (LocdevApp) getApplicationContext();
                                app.LocationsToRemove.add(selectedLocation);
                                finish();
                                //TODO: Remove on the server
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

    private void populateListView(ArrayList<String> wifi_ids){
        final MyListView listView = (MyListView) findViewById(R.id.wifiIds_list_location);
        final ArrayAdapter<String> wifiIdsAdapter = new ArrayAdapter<>(
                LocationActivity.this,
                R.layout.wifiid_list_item,
                wifi_ids);
        listView.setAdapter(wifiIdsAdapter);
    }
}
