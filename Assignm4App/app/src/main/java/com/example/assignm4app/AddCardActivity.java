/*
 * This class operation enable to add a card
 * using NFC tag to add cardNumber, cardName is manually
 * added by the user.
 */
package com.example.assignm4app;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddCardActivity extends AppCompatActivity {

    private static RestfulCreateTask database;
    private String urlConnection = "http://192.168.1.62:8080/Assignm4Web/NFCCardServer/NFCCards/";

    private EditText cardName;
    private EditText cardNumber;
    private TextView cardName_view;

    private NfcAdapter nfcAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        /** Sets up the toolbar layout for menu options */
        Toolbar bar = findViewById(R.id.card_toolbar);
        setSupportActionBar(bar);

        cardName = (EditText) findViewById(R.id.card_name);
        cardNumber = (EditText) findViewById(R.id.card_number);
        cardName_view = (TextView) findViewById(R.id.cardName_view);

        Log.i(AddCardActivity.class.getName(), "onCreate called");
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    //sets up the items (add and delete button) options on the toolbar layout
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);

        return true;
    }

    private void handleTagConnection(Intent intent)
    {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals
                (intent.getAction()))
        {  // obtain the NDEF messages
            Parcelable[] rawMessages = intent.getParcelableArrayExtra
                    (NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null)
            {
                StringBuilder builder = new StringBuilder();
                builder.append(getString(R.string.message_count)
                        + rawMessages.length + "\n");
                for (int i = 0; i < rawMessages.length; i++)
                {
                    NdefMessage message = (NdefMessage) rawMessages[i];
                    NdefRecord[] records = message.getRecords();
                    builder.append(getString(R.string.message_item)
                            + i + ", ");
                    builder.append(getString(R.string.record_count)
                            + records.length + "\n");
                    for (NdefRecord record : records)
                    {
                        builder.append(record.toMimeType());
                        byte[] payload = record.getPayload();
                        for (byte item : payload)
                        {
                            builder.append(" " + item);
                        }
                        builder.append("\n");
                    }
                }
                cardNumber.setText(builder.toString());
                Log.i(AddCardActivity.class.getName(), builder.toString());
            }
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Ndef ndefTag = Ndef.get(tag);
            // optionally write some NdefMessage to NDEF tag
        }
        else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals
                (intent.getAction()))
        {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            String[] techList = tag.getTechList();
            String tagDescription = tag.toString();
            StringBuilder builder = new StringBuilder();
            builder.append(getString(R.string.tech_count)
                    + techList.length + " " + tagDescription + "\n");
            for (String techItem : techList)
            {
                builder.append(techItem + "\n");
            }
            cardNumber.setText(builder.toString());
            Log.i(AddCardActivity.class.getName(), builder.toString());
            // now use tag-specific class for communication
        }
        else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals
                (intent.getAction()))
        {
            cardNumber.setText(R.string.tag_unknown);
            Log.w(AddCardActivity.class.getName(),
                    getString(R.string.tag_unknown));
        }
    }

    //Button click handling
    public boolean onOptionsItemSelected(MenuItem item) {

        String newCardName = cardName.getText().toString();
        String newCardNumber = cardNumber.getText().toString();

        switch(item.getItemId()) {
            case R.id.actionAddCard:

                /** Validate if textfield is empty or not */
                if (newCardName.length() != 0 && newCardNumber.length() != 0) {

                    /** Add a new card */
                    database = new RestfulCreateTask(cardName, cardNumber, newCardName, newCardNumber);
                    database.execute(urlConnection+newCardName+"/"+newCardNumber+"/");
                    Toast.makeText(this, newCardName +" successfully added", Toast.LENGTH_SHORT).show();

                }
                /** card will only be added if BOTH name and number is given */
                else if(cardName.length() == 0 && cardNumber.length() == 0)
                {
                    Toast.makeText(this, "You must provide the card name and the NFC number", Toast.LENGTH_SHORT).show();
                }
                else if(cardName.length() != 0 && cardNumber == null)
                {
                    Toast.makeText(this, "Please assign a name to the tagged NFC car", Toast.LENGTH_SHORT).show();
                }
                else if(cardName == null && cardNumber.length() != 0)
                {
                    Toast.makeText(this, "Please provide the the NFC number, easily tag to do so", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.actionDeleteCard:
                Toast.makeText(this, "No card to delete, will act as a back button to the feed", Toast.LENGTH_SHORT).show();
                Intent feedIntent = new Intent(AddCardActivity.this, CardsFeedActivity.class);
                startActivity(feedIntent);
                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    protected void onResume()
    {
        super.onResume();
        Log.i(AddCardActivity.class.getName(), "onResume called");
        Intent intent = getIntent();
        handleTagConnection(intent);
    }

    protected void onPause()
    {
        super.onPause();
        Log.i(AddCardActivity.class.getName(), "onPause called");
    }

    protected void onStop()
    {
        super.onStop();
        Log.i(AddCardActivity.class.getName(), "onStop called");
    }

    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(AddCardActivity.class.getName(), "onDestroy called");
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        Log.i(AddCardActivity.class.getName(), "onNewIntent called");
        setIntent(intent);
    }
}
