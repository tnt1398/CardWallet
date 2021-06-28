/*
 * This class operation enable to delete a card
 */
package com.example.assignm4app;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class DeleteCardActivity extends AppCompatActivity {

    private static RestfulDeleteTask database;
    private String urlConnection = "http://192.168.1.62:8080/Assignm4Web/NFCCardServer/NFCCards/";

    private EditText cardName;
    private EditText cardNumber;
    private TextView cardName_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        /** Sets up the toolbar layout for menu options */
        Toolbar bar = findViewById(R.id.card_toolbar);
        setSupportActionBar(bar);

        cardName = (EditText) findViewById(R.id.card_name);
        cardNumber = (EditText) findViewById(R.id.card_number);
        cardName_view = (TextView) findViewById(R.id.cardName_view);

    }

    @Override
    //sets up the items (add and delete button) options on the toolbar layout
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);

        return true;
    }

    @Override
    //Button click handling
    public boolean onOptionsItemSelected(MenuItem item) {

        String theCardName = cardName.getText().toString();
        String theCardNumber = cardNumber.getText().toString();

        switch(item.getItemId()) {
            case R.id.actionAddCard:
                Toast.makeText(this, "No card to add, will act as a back button to the feed", Toast.LENGTH_SHORT).show();
                Intent feedIntent = new Intent(DeleteCardActivity.this, CardsFeedActivity.class);
                startActivity(feedIntent);
                break;

            case R.id.actionDeleteCard:
                database = new RestfulDeleteTask(cardName, cardNumber, theCardName, theCardNumber);
                database.execute(urlConnection+theCardName+"/"+theCardNumber+"/");
                Toast.makeText(this, theCardName + " deleted", Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
