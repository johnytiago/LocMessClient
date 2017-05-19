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

/**
 * Created by johnytiago on 13/05/2017.
 */

public class MessageActivity extends AppCompatActivity {
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

        // Capture the layout's TextView and set the string as its text
        TextView titleView = (TextView) findViewById(R.id.text_message);
        titleView.setText(title);
        TextView authorView = (TextView) findViewById(R.id.owner_message);
        authorView.setText(author);
        TextView fromDateView = (TextView) findViewById(R.id.from_date_text_message);
        fromDateView.setText(date);
        TextView toDateView = (TextView) findViewById(R.id.to_date_text_message);
        toDateView.setText(date);

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
                                // TODO: unPost message
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }
}
