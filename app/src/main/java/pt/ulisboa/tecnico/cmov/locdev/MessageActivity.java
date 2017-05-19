package pt.ulisboa.tecnico.cmov.locdev;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;

/**
 * Created by johnytiago on 13/05/2017.
 */

public class MessageActivity extends AppCompatActivity {
    Message selectMessage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Message");
        setSupportActionBar(toolbar);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String title = intent.getStringExtra(MessagesFragment.MESSAGE_TITLE);
        String author = intent.getStringExtra(MessagesFragment.MESSAGE_AUTHOR);
        String date = intent.getStringExtra(MessagesFragment.MESSAGE_DATE);
        // TODO: receive the rest of the needed arguments

        LocdevApp app = (LocdevApp) getApplicationContext();
        List<Message> messages = app.getAvailableMessages();

        for(Message m : messages){
            if(m.getMessage().equals(title)){
                selectMessage=m;
                break;
            }
        }

        // Capture the layout's TextView and set the string as its text
        TextView titleView = (TextView) findViewById(R.id.text_message);
        titleView.setText(title);
        TextView authorView = (TextView) findViewById(R.id.owner_message);
        authorView.setText(author);
        TextView locationView = (TextView) findViewById(R.id.location_message);
        locationView.setText(selectMessage.getLocation().getName());
        TextView fromDateView = (TextView) findViewById(R.id.from_date_text_message);
        fromDateView.setText(selectMessage.getCreationDate().toString());
        TextView toDateView = (TextView) findViewById(R.id.to_date_text_message);
        toDateView.setText(selectMessage.getDeletionDate().toString());

        TextView Keys = (TextView) findViewById(R.id.keys_list_message);
        String keyvalues="";
        String Separator = "!=";
        if(selectMessage.getRestriction().isWhitelist()) Separator = "==";
        for(String key : selectMessage.getRestriction().getRestrictions().keySet()){
            keyvalues+=key+Separator+selectMessage.getRestriction().getRestrictions().get(key);
            keyvalues+=";\n";
        }

        Keys.setText(keyvalues);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.unpost_message_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MessageActivity.this)
                        .setMessage("Do you really want to unpost this message?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(MessageActivity.this, "Message unposted.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                LocdevApp app = (LocdevApp) getApplicationContext();
                                app.MessagesToRemove.add(selectMessage);
                                finish();
                                // TODO: unPost message
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }
}
