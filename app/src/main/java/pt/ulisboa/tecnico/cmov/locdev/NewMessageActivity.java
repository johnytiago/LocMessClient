package pt.ulisboa.tecnico.cmov.locdev;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.finish_edit_newMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: use users input and send to server or relay
                String title = ((TextView) view.findViewById(R.id.edit_text_newMessage)).getText().toString();
                String keys = ((TextView) view.findViewById(R.id.edit_keys_list_newMessage)).getText().toString();
                Boolean is_centralized = ((Switch) view.findViewById(R.id.mode_switch_newMessage)).isChecked();
            }
        });
    }
}
