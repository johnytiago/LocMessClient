package pt.ulisboa.tecnico.cmov.locdev;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.security.KeyPair;
import java.util.Date;
import java.util.List;

import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Restriction;

/**
 * Created by johnytiago on 13/05/2017.
 */

public class NewMessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Message");
        setSupportActionBar(toolbar);

        LocdevApp app = (LocdevApp) getApplicationContext();
        List<Location> locations = app.getLocations(null,null);
        String[] autocompleteArray = new String[locations.size()];
        int i = 0;
        for(Location loc : locations){
            autocompleteArray[i] = loc.getName();
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, autocompleteArray);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.location_message);
        textView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.finish_edit_newMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: use users input and send to server or relay
                String LocationName = ((AutoCompleteTextView) findViewById(R.id.location_message)).getText().toString();
                String title = ((EditText) findViewById(R.id.edit_text_newMessage)).getText().toString();
                String keys = ((TextView) findViewById(R.id.edit_keys_list_newMessage)).getText().toString();
                Boolean is_centralized = ((Switch) findViewById(R.id.mode_switch_newMessage)).isChecked();

                LocdevApp app = (LocdevApp) getApplicationContext();

                List<Location> locations = app.getLocations(null,null);
                Location SelectedLocation = null;
                for(Location loc : locations){
                    if(loc.getName().equals(LocationName)){
                        SelectedLocation=loc;
                    }
                }
                if(SelectedLocation==null) return;

                boolean whitelist = true;
                Restriction rest = new Restriction(whitelist);
                String[] keypairs = keys.split(";");
                Log.d(this.getClass().getName(), "keypair Length " + keypairs.length);
                for(String keypair : keypairs){
                    if(keypair.contains("!=")){
                        Log.d(this.getClass().getName(),"Is blacklist");
                        keypair = keypair.replace("!=","=");
                        whitelist=false;
                        rest.setWhitelist(whitelist);
                    }
                    String[] devicedKeyPair = keypair.split("=");
                    Log.d(this.getClass().getName(), "keypair Length " + devicedKeyPair.length);
                    if(devicedKeyPair.length<2) continue;
                    rest.AddRestrictions(devicedKeyPair[0],devicedKeyPair[1]);
                }

                Message message = new Message(SelectedLocation,title,rest,app.getUser());
                app.MessagesToAdd.add(message);
                finish();
            }
        });
    }
}
