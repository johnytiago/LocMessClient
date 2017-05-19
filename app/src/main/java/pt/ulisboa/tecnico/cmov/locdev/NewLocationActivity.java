package pt.ulisboa.tecnico.cmov.locdev;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by johnytiago on 13/05/2017.
 */

public class NewLocationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_location_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Location");
        setSupportActionBar(toolbar);
        // Start hidden
        findViewById(R.id.wifiIds_layout_newLocation).setVisibility(View.GONE);

        final ArrayList<String> wifi_ids = new ArrayList<>(Arrays.asList(""));
        final MyListView listView = (MyListView) findViewById(R.id.wifiIds_list_newLocation);
        final ArrayAdapter<String> wifiIdsAdapter = new ArrayAdapter<>(
                NewLocationActivity.this,
                R.layout.wifiid_list_item,
                wifi_ids);
        listView.setAdapter(wifiIdsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemView,
                                    final int position, long id) {

                View view = (LayoutInflater.from(NewLocationActivity.this)).inflate(R.layout.keys_list_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NewLocationActivity.this);
                alertBuilder.setView(view);

                final EditText new_key = (EditText) view.findViewById(R.id.keys_list_input_text);
                // set alert text to the value of the old key
                new_key.setText(((TextView) itemView).getText());
                final String old_key_text = new_key.getText().toString();

                alertBuilder.setCancelable(true)
                        .setMessage( old_key_text.trim().equals("") ? "Add Location" : "Edit Location")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String new_key_text = new_key.getText().toString().trim();
                                if (!new_key_text.equals(old_key_text) && !new_key_text.equals("")){
                                    wifiIdsAdapter.insert(new_key_text, position);
                                    wifi_ids.add(position, new_key_text.toString());
                                    wifiIdsAdapter.remove(old_key_text);
                                    wifi_ids.remove(new_key_text.toString());
                                    //It was really an insertion, not a correction
                                    if(old_key_text.trim().equals("")) {
                                        wifiIdsAdapter.add(" ");
                                    }
                                    wifiIdsAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.finish_edit_newLocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: use users input and send to server or relay
                // wifi_ids array to send
                String name = ((TextView) findViewById(R.id.location_name_newLocation)).getText().toString();
            }
        });

        Switch mySwitch = (Switch) findViewById(R.id.type_switch_newLocation);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    findViewById(R.id.x_coordenate_layout_newLocation).setVisibility(View.VISIBLE);
                    findViewById(R.id.y_coordenate_layout_newLocation).setVisibility(View.VISIBLE);
                    findViewById(R.id.radius_layout_newLocation).setVisibility(View.VISIBLE);
                    findViewById(R.id.wifiIds_layout_newLocation).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.x_coordenate_layout_newLocation).setVisibility(View.GONE);
                    findViewById(R.id.y_coordenate_layout_newLocation).setVisibility(View.GONE);
                    findViewById(R.id.radius_layout_newLocation).setVisibility(View.GONE);
                    findViewById(R.id.wifiIds_layout_newLocation).setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
